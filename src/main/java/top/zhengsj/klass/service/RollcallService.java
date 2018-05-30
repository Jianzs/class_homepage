package top.zhengsj.klass.service;

import top.zhengsj.klass.dao.projection.RollcallBriefProjection;

import java.util.List;

public interface RollcallService {
    List<RollcallBriefProjection> getFrontRecord(Integer userId);
}
