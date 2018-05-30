package top.zhengsj.klass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.zhengsj.klass.dao.NoticeDao;
import top.zhengsj.klass.dao.projection.NoticeBriefProjection;
import top.zhengsj.klass.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {
    private NoticeDao noticeDao;

    @Autowired
    public NoticeServiceImpl(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @Override
    public Page<NoticeBriefProjection> getFrontNoticeList(Integer type, Pageable pageable) {
        return noticeDao.findAllByType(type, pageable);
    }
}
