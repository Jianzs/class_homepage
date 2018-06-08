package top.zhengsj.klass.service.impl;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.zhengsj.klass.dao.FormDao;
import top.zhengsj.klass.dao.projection.FormDetailProjection;
import top.zhengsj.klass.dao.projection.FormListProjection;
import top.zhengsj.klass.exception.PostFormException;
import top.zhengsj.klass.pojo.entity.FormEntity;
import top.zhengsj.klass.service.FormRecordService;
import top.zhengsj.klass.service.FormService;
import top.zhengsj.klass.util.FetchUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FormServiceImpl implements FormService {
    private String PREFIX_URL = "http://yingkebao.top/web/formview/";
    private FormDao formDao;
    private FormRecordService formRecordService;

    private Logger logger = LoggerFactory.getLogger(FormServiceImpl.class);

    @Autowired
    public FormServiceImpl(FormDao formDao, FormRecordService formRecordService) {
        this.formDao = formDao;
        this.formRecordService = formRecordService;
    }

    @Override
    public FormDetailProjection getFrontFrom(Integer formId) {
        return formDao.getDetailById(formId);
    }

    @Override
    public void createBackForm(JSONObject jsonObject, Integer userId) throws IOException {
        String formId = jsonObject.getString("formId");
        Date endTime = new Date(jsonObject.getLong("endTime"));

        Document document = FetchUtil.getHtml(PREFIX_URL + formId);
        Elements form = document.getElementsByTag("form");
        Elements title = document.getElementsByTag("title");
        String text = title.first().text();

        FormEntity formEntity = new FormEntity();
        formEntity.setAdminId(userId);
        formEntity.setTitle(text);
        formEntity.setContent(form.html());
        formEntity.setEndTime(endTime);
        formEntity.setFormId(formId);
        formDao.save(formEntity);
    }

    @Override
    public void postFront(Integer formId, String content, Integer userId) throws PostFormException {
        JSONObject jsonObject = new JSONObject(content);
        String formCode = jsonObject.getString("FRMID");

        formRecordService.addRecord(userId, formId);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> requestEntity = new HttpEntity<>(content, headers);
        try {
            restTemplate.postForObject(PREFIX_URL + formCode, requestEntity, String.class);
        } catch (Exception e) {
            throw new PostFormException("POST FORM FAILED.");
        }
    }

    @Override
    public List<FormListProjection> getFrontFormList(Integer userId) {
        return formDao.findForms(userId);
    }
}
