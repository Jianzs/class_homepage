package top.zhengsj.klass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhengsj.klass.dao.RollcallDao;
import top.zhengsj.klass.dao.projection.RollcallBriefProjection;
import top.zhengsj.klass.service.RollcallService;

import java.util.List;

@Service
public class RollcallServiceImpl implements RollcallService {
    private RollcallDao rollcallDao;

    @Autowired
    public RollcallServiceImpl(RollcallDao rollcallDao) {
        this.rollcallDao = rollcallDao;
    }

    @Override
    public List<RollcallBriefProjection> getFrontRecord(Integer userId) {
        return rollcallDao.findAllByUserId(userId);
    }
}
