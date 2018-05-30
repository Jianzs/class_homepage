package top.zhengsj.klass.enums;

public enum UserRoleEnum {
    STUDENT(0),
    ADMINISTRATION(1);

    private Integer value;

    UserRoleEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
