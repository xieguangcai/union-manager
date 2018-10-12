package com.coocaa.union.manager.accounts;

import java.util.Date;

public class SearchAccountModel {
    private String nickName;
    private String userName;
    private Date[] createTime;
    private Integer[] accountStatus;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date[] getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date[] createTime) {
        this.createTime = createTime;
    }

    public Integer[] getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer[] accountStatus) {
        this.accountStatus = accountStatus;
    }
}
