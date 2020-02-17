package com.coocaa.union.manager.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证用户主体模型
 */
public class SysUserAuthentication implements UserDetails {

    private static final long serialVersionUID = 2678080792987564753L;

    /**
     * ID号
     */
    private String uuid;
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账户生效
     */
    private boolean accountNonExpired;
    /**
     * 账户锁定
     */
    private boolean accountNonLocked;
    /**
     * 凭证生效
     */
    private boolean credentialsNonExpired;
    /**
     * 激活状态
     */
    private boolean enabled;
    /**
     * 权限列表
     */
    private Collection<GrantedAuthority> authorities;
    private String email;

    private Map<String, List<String>> dataItems = new HashMap<>();
    /**
     * ID号
     * @return uuid 
     */
    public String getUuid() {
        return uuid;
    }
    
    /**
     * ID号
     * @param uuid ID号
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    /**
     * 用户名
     * @return username 
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * 用户名
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * 密码
     * @return password 
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 账户生效
     * @return accountNonExpired 
     */
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    /**
     * 账户生效
     * @param accountNonExpired 账户生效
     */
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    /**
     * 账户锁定
     * @return accountNonLocked 
     */
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    /**
     * 账户锁定
     * @param accountNonLocked 账户锁定
     */
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    /**
     * 凭证生效
     * @return credentialsNonExpired 
     */
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    /**
     * 凭证生效
     * @param credentialsNonExpired 凭证生效
     */
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    /**
     * 激活状态
     * @return enabled 
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * 激活状态
     * @param enabled 激活状态
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * 权限列表
     * @return authorities 
     */
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    /**
     * 权限列表
     * @param authorities 权限列表
     */
    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, List<String>> getDataItems() {
        return dataItems;
    }

    public void setDataItems(Map<String, List<String>> dataItems) {
        this.dataItems = dataItems;
    }
}