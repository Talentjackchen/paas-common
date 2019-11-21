package com.wondersgroup.cloud.paas.common.annotation;


import com.wondersgroup.cloud.paas.common.enums.ParamTypeEnum;
import com.wondersgroup.cloud.paas.common.enums.UserInfoValidationEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfoValidation {
    ParamTypeEnum paramType();

    String[] paramNames();

    UserInfoValidationEnum[] userInfos();
}
