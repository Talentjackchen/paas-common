package com.wondersgroup.cloud.paas.common.enums;

/**
 * @author chenlong
 */
public enum ParamTypeEnum {

    STRING("String"),
    MAP("Map"),
    ENTITY("Entity");

    private String value;

    ParamTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
