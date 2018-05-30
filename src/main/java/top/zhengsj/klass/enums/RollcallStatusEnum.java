package top.zhengsj.klass.enums;

public enum RollcallStatusEnum {
    NORMAL(0),
    ABSENTEEISM(1),
    LEAVE(3);

    private Integer value;

    RollcallStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
