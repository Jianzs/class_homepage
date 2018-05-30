package top.zhengsj.klass.service;

import top.zhengsj.klass.pojo.dto.OptionDto;
import top.zhengsj.klass.pojo.entity.UserEntity;

public interface UserService {
    OptionDto<Integer, String> frontLogin(UserEntity user);

    void frontUpdateInfo(Integer userId, UserEntity user);

    UserEntity getFrontUserInfo(Integer userId);
}
