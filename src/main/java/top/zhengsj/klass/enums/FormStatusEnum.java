package top.zhengsj.klass.enums;

public enum FormStatusEnum {
    UNFINISHED(0),
    FINISHED(1);

    private Integer value;

    FormStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
