package com.coocaa.union.manager.models;

public class Roles {
    /**
     * 查看权限，客服查看信息
     */
    public static final String ROLE_VIEW = "ROLE_VIEW";
    /**
     * 编辑权限，新增和修改权限
     */
    public static final String ROLE_EDIT = "ROLE_EDIT";
    /**
     * 审计权限
     */
    public static final String ROLE_AUDIT = "ROLE_AUDIT";
    /**
     * 管理员权限
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    /**
     * 导出权限
     */
    public static final String ROLE_EXPORT = "ROLE_EXPORT";

    /**
     * 从 域账号创建的新账号。
     */
    public static final String ROLE_NEW_LDAP_USER = "ROLE_NEW_LDAP_USER";

}
