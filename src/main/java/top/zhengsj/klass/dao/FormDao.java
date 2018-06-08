package top.zhengsj.klass.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.zhengsj.klass.dao.projection.FormDetailProjection;
import top.zhengsj.klass.dao.projection.FormListProjection;
import top.zhengsj.klass.pojo.entity.FormEntity;

import java.util.List;

@Repository
public interface FormDao extends JpaRepository<FormEntity, Integer> {
    String findForms = "SELECT A.id AS formId, A.title, A.end_time AS endTime, IFNULL(B.form_id, 0) AS status\n" +
            "FROM (SELECT id, title, end_time\n" +
            "\tFROM form) AS A\n" +
            "\tLEFT JOIN \n" +
            "\t(SELECT form_id\n" +
            "\tFROM form_record\n" +
            "\tWHERE user_id = :userId) AS B\n" +
            "\t ON (A.id = B.form_id)\n" +
            "GROUP BY A.id, A.title, A.end_time\n";

    FormEntity getById(Integer formId);
    FormDetailProjection getDetailById(Integer formId);

    @Query(value = findForms, nativeQuery = true)
    List<FormListProjection> findForms(@Param("userId") Integer userId);
}
