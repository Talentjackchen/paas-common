package com.wondersgroup.cloud.paas.common.theadlocal;

/**
 * @param <T>
 * @author chenlong
 */
public class AuthorizationThreadLocal<T> {
    private final static ThreadLocal threadLocal = new ThreadLocal();

    public static ThreadLocal getCurrent() {
        return threadLocal;
    }
}
