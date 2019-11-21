package com.wondersgroup.cloud.paas.common.constant;

/**
 * @author chenlong
 */
public class UserInfoValidationConstant {

    public static int GET_CURRENT_USER_INFO_FAIL_CODE = 1000;
    public static String GET_CURRENT_USER_INFO_FAIL_MSG = "无法获取用户登录信息";


    public static int OBJECT_PROPERTIES_FAIL_CODE = 1001;
    public static String OBJECT_PROPERTIES_FAIL_MSG = "对象没有映射属性值";


    public static int USER_ACCESS_FAIL_CODE = 1002;
    public static String USER_ACCESS_FAIL_MSG = "用户无权限访问";


    public static int ACCOUNT_VALID_FAIL_CODE = 1003;
    public static String ACCOUNT_VALID_FAIL_MSG = " 验证账号不通过";

    public static int PROJECT_VALID_FAIL_CODE = 1004;
    public static String PROJECT_VALID_FAIL_MSG = " 验证项目不通过";

    public static int OPERATE_OUT_CONTROL_CODE = 1005;
    public static String OPERATE_OUT_CONTROL_MSG = "操作越权";

}
