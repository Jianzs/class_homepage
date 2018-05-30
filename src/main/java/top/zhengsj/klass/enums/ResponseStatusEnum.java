package top.zhengsj.klass.enums;

public enum ResponseStatusEnum {
    SUCCEED(0),
    FAILED(1);

    private Integer value;

    ResponseStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
