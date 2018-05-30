package top.zhengsj.klass.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.zhengsj.klass.pojo.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {

    UserEntity getByNumber(Long number);

    UserEntity getById(Integer userId);
}
