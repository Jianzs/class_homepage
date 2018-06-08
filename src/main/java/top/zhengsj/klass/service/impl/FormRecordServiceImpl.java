package top.zhengsj.klass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhengsj.klass.dao.FormRecordDao;
import top.zhengsj.klass.pojo.entity.FormRecordEntity;
import top.zhengsj.klass.service.FormRecordService;

@Service
public class FormRecordServiceImpl implements FormRecordService {
    private FormRecordDao formRecordDao;

    @Autowired
    public FormRecordServiceImpl(FormRecordDao formRecordDao) {
        this.formRecordDao = formRecordDao;
    }

    @Override
    public void addRecord(Integer userId, Integer formId) {
        boolean exists = formRecordDao.existsByUserIdAndFormId(userId, formId);
        if (exists) return ;
        FormRecordEntity formRecord = new FormRecordEntity();
        formRecord.setFormId(formId);
        formRecord.setUserId(userId);
        formRecordDao.save(formRecord);
    }
}
