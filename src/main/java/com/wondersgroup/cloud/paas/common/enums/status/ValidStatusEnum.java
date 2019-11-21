package com.wondersgroup.cloud.paas.common.enums.status;

/**
 * @author chenlong
 */
public enum ValidStatusEnum {

    /**
     * 无效
     */
    INVALID("0"),
    /**
     * 有效
     */
    VALID("1");

    private String value;

    ValidStatusEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}