package com.wondersgroup.cloud.paas.common.exception;

import com.wondersgroup.cloud.paas.common.pojo.ResultMap;

/**
 * @author chenlong
 */
public class BusinessException extends Exception{
    private ResultMap resultMap;

    public BusinessException(int code, String message) {
        super(message);
        setResultMap(code, message);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        setResultMap(code, message);
    }

    private void setResultMap(int code,String message){
        resultMap = new ResultMap(code,message);
    }

    public ResultMap getResultMap() {
        return resultMap;
    }
}
