package top.zhengsj.klass.enums;

public enum NoticeTypeEnum {
    CLASS(0),
    SHUDIAN(1),
    CPP(2);

    private Integer value;

    NoticeTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
