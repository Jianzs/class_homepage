package top.zhengsj.klass.service;

import org.json.JSONObject;
import top.zhengsj.klass.dao.projection.FormDetailProjection;
import top.zhengsj.klass.dao.projection.FormListProjection;
import top.zhengsj.klass.exception.PostFormException;

import java.io.IOException;
import java.util.List;

public interface FormService {
    FormDetailProjection getFrontFrom(Integer formId);

    void createBackForm(JSONObject formId, Integer userId) throws IOException;

    void postFront(Integer formId, String content, Integer userId) throws PostFormException;

    List<FormListProjection> getFrontFormList(Integer userId);
}
