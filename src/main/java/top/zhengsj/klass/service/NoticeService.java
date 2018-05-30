package top.zhengsj.klass.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import top.zhengsj.klass.dao.projection.NoticeBriefProjection;

public interface NoticeService {
    Page<NoticeBriefProjection> getFrontNoticeList(Integer type, Pageable pageable);
}
