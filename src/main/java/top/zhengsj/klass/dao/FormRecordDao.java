package top.zhengsj.klass.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.zhengsj.klass.pojo.entity.FormRecordEntity;

@Repository
public interface FormRecordDao extends JpaRepository<FormRecordEntity, Integer> {

    Boolean existsByUserIdAndFormId(Integer userId, Integer formId);
}
