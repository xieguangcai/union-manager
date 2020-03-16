package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.roles.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "union_accounts")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler","fieldHandler","salt", "roles", "dataItems"})
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    @Column(name = "nick_name", nullable = false, length = 100)
    private String nickName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "pwd", length = 50)
    private String pwd;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "account_status", nullable = false)
    private Integer accountStatus;

    private String salt;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    private String email;
    private String department;
    private String memo;
    private String avatar;
    @Column(name = "last_login_time")
    private Date lastLoginTime;
    @Column(name = "create_time", updatable = false)
    private Date createTime;
    @Column(name = "modify_time", updatable = false)
    private Date modifyTime;
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "union_account_role",
            joinColumns = {@JoinColumn(name = "accountId", referencedColumnName = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(targetEntity = DataItems.class, fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "union_account_data_item",
            joinColumns = {@JoinColumn(name = "accountId", referencedColumnName = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "itemId", referencedColumnName = "item_id")}
    )
    private Set<DataItems> dataItems = new HashSet<>();

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<DataItems> getDataItems() {
        return dataItems;
    }

    public void setDataItems(Set<DataItems> dataItems) {
        this.dataItems = dataItems;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Account that = (Account) o;
        return  Objects.equals(accountId, that.accountId) &&
                Objects.equals(nickName, that.nickName) &&
                Objects.equals(department, that.department) &&
                Objects.equals(email, that.email) &&
                Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, nickName, department, email, userName);
    }
}
