package com.wondersgroup.cloud.paas.common.enums;

/**
 * @author chenlong
 */
public enum UserInfoValidationEnum {
    ACCOUNT("Account"),
    PROJECT("Project");

    private String value;

    UserInfoValidationEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
