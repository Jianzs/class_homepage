package top.zhengsj.klass.dao.projection;

import java.util.Date;

public interface FormListProjection {
    Integer getFormId();
    String getTitle();
    Date getEndTime();
    Integer getStatus();
}
