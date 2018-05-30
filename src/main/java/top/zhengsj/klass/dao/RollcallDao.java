package top.zhengsj.klass.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.zhengsj.klass.dao.projection.RollcallBriefProjection;
import top.zhengsj.klass.pojo.entity.RollcallEntity;

import java.util.List;

@Repository
public interface RollcallDao extends JpaRepository<RollcallEntity, Integer> {
    List<RollcallBriefProjection> findAllByUserId(Integer userId);
}
