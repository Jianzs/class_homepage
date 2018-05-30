package top.zhengsj.klass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhengsj.klass.dao.UserDao;
import top.zhengsj.klass.enums.UserRoleEnum;
import top.zhengsj.klass.pojo.dto.OptionDto;
import top.zhengsj.klass.pojo.entity.UserEntity;
import top.zhengsj.klass.service.UserService;
import top.zhengsj.klass.util.BCryptUtil;
import top.zhengsj.klass.util.JWTUtil;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public OptionDto<Integer, String> frontLogin(UserEntity user) {
        UserEntity realUser = userDao.getByNumber(user.getNumber());
        if (realUser == null || !BCryptUtil.check(user.getPassword(), realUser.getPassword())) {
            return new OptionDto<>(1, "Number Or Password is Wrong.");
        } else  {
            String token = JWTUtil.createToken(realUser.getId(), UserRoleEnum.STUDENT.getValue());
            return new OptionDto<>( 0, token);
        }
    }

    @Override
    public void frontUpdateInfo(Integer userId, UserEntity user) {
        UserEntity realUser = userDao.getById(userId);

        realUser.setPhone(user.getPhone());
        realUser.setFatherPhone(user.getFatherPhone());
        realUser.setMatherPhone(user.getMatherPhone());
        realUser.setHome(user.getHome());
        realUser.setPoliticalStatus(user.getPoliticalStatus());

        userDao.save(realUser);
    }

    @Override
    public UserEntity getFrontUserInfo(Integer userId) {
        return userDao.getById(userId);
    }
}
