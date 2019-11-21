package com.wondersgroup.cloud.paas.common.pojo;

import java.util.List;

/**
 * @author chenlong
 */
public class UserVo {
    private String userId;

    private String username;

    private String name;

    private String mobile;

    private String email;

    List<ProjectVO> projects;

    List<RoleVO> roles;

    List<PermissionVO> permissions;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProjectVO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectVO> projects) {
        this.projects = projects;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public List<PermissionVO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionVO> permissions) {
        this.permissions = permissions;
    }
}
