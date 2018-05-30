package top.zhengsj.klass.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.zhengsj.klass.dao.projection.NoticeBriefProjection;
import top.zhengsj.klass.pojo.entity.NoticeEntity;

@Repository
public interface NoticeDao extends JpaRepository<NoticeEntity, Integer> {
    Page<NoticeBriefProjection> findAllByType(Integer type, Pageable pageable);
}
