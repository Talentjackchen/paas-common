package com.wondersgroup.cloud.paas.common.pojo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AccessControlAllowOriginBean {

    private String allowOrigin;

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public boolean isAll() {
        return "*".equals(allowOrigin);
    }

    public Set<String> getDomains() {
        String[] domains = allowOrigin.trim().split(",");
        return new HashSet<>(Arrays.asList(domains));
    }
}
