package com.wondersgroup.cloud.paas.common.constant;

/**
 * @author chenlong
 */
public class CommonConstant {
    /**
     * 成功
     */
    public static int SUCCESS = 200;
    public static String RESULT_SUCCESS = "success";

    /**
     * 失败
     */
    public static int ERROR = 400;
    public static String RESULT_ERROR = "error";


    public static int AUTHENTICATION_CODE_FAIL = 1000;
    public static String AUTHENTICATION_MSG_FAIL = "认证失败";

    public static int MD5_CODE_FAIL = 1001;
    public static String MD5_MSG_FAIL = "MD5加密失败";

    public static int REQUEST_EXPIRE_CODE_FAIL = 1002;
    public static String REQUEST_EXPIRE_MSG_FAIL = "认证签名已过期";

    public static String BEGIN_DATE_FORMAT_ERROR = "开始时间转化错误";
    public static String END_DATE_FORMAT_ERROR = "结束时间转化错误";

    public static String DATA_NOT_EXISTS = "数据不存在";
    public static String NO_AUTHORITY = "没有操作权限";
    public static String DELETE_SUCCESS = "删除成功";
    public static String DELETE_ERROR = "删除失败";
    public static String START_SUCCESS = "启用成功";
    public static String START_ERROR = "启用失败";
    public static String STOP_SUCCESS = "停用成功";
    public static String STOP_ERROR = "停用失败";
    public static String ADD_SUCCESS = "添加成功";
    public static String ADD_ERROR = "添加失败";
    public static String STOP_ERROR_KEEP_ONE = "操作失败,请至少保证一个有效密钥";

    public static int ILLEGAL_ACCESS_CODE = 1004;
    public static String ILLEGAL_ACCESS_MSG = "非法访问";

    public static int REQUEST_HEADER_ERROR_CODE = 1005;
    public static String REQUEST_HEADER_ERROR_MSG = "请求头错误，请参考API文档";

    /**
     * 默认失效时间一小时
     */
    public static long EXPIRE_TIME_DEFAULT = 3600;

    /**
     * 请求5分钟失效
     */
    public static long REQUEST_EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 分页默认第一页
     */
    public static int PAGENUM = 1;

    /**
     * 分页默认每页显示10条
     */
    public static int PAGESIZE = 10;

    /**
     * 分页默认排序规则
     */
    public static String ORDERBYCLAUSE = "create_time desc";

    public static String SORD= "desc";
    public static String SIXD= "name";


    //下划线拼接符
    public static String UNDER_LINE_SYMBOL = "_";

    /**
     * 密钥错误
     */
    public static int ERROR_ACCESSKEY_CODE = 2003;
    public static String ERROR_ACCESSKEY_MSG = "获取密钥失败";

    /**
     * 参数校验失败
     */
    public static int DATA_CHECK_FAILURE_CODE = 2004;
    public static String DATA_CHECK_FAILURE_MSG = "缺少必要参数";

    /**
     * http协议头
     */
    public static String HTTP_PROTOCOL = "http://";

    /**
     * https协议头
     */
    public static String HTTPS_PROTOCOL = "https://";

    /**
     * 反斜杠
     */
    public static String BACKSLASH = "/";

    /**
     * json格式
     */
    public static String CONTENT_TYPE_JSON = "application/json";

    /**
     * POST提交
     */
    public static String REQUEST_TYPE_POST = "POST";

    /**
     * PUT提交
     */
    public static String REQUEST_TYPE_PUT = "PUT";
    /**
     * DELETE提交
     */
    public static String REQUEST_TYPE_DELETE = "DELETE";

    public static String UNDERLINE = "_";

    public static int EXCEPTION_CODE_PRIVATE_KEY_DECRYPT = 901;
    public static String EXCEPTION_MSG_PRIVATE_KEY_DECRYPT = "私钥解密失败";

    public static int EXCEPTION_CODE_DATA_KEY_DECRYPT = 902;
    public static String EXCEPTION_MSG_DATA_KEY_DECRYPT = "数据解密失败";

    public static String DEFAULT_ENCRYPT_KEY = "1234567890123456";
    public static String DEFAULT_ENCRYPT_IV_KEY = "6543********4321";
    public static String PRIMARY_KEY_NOT_EXIST = "缺少必要参数";

    public static int ERROR_MSG_LENGTH = 4000;
}
