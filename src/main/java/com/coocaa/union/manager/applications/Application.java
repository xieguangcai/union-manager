package com.coocaa.union.manager.applications;

import com.coocaa.union.manager.accounts.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "union_applications")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler","fieldHandler"})
public class Application {
    public Application() {
    }

    public Application(Integer appId) {
        this.appId = appId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Integer appId;
    @Column(name = "app_key")
    private String appKey;
    @Column(name = "app_secret")
    private String appSecret;
    @Column(name = "name")
    private String name;
    @Column(name = "memo")
    private String memo ;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
    private Integer status;
    private Integer type ;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Application that = (Application) o;
        return  Objects.equals(appId, that.appId) &&
                Objects.equals(appKey, that.appKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, appKey);
    }
}
