package com.coocaa.union.manager.accounts;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author xieguangcai
 * @date 2020/2/17
 */
@Entity
@Table(name = "union_account_data_item", schema = "union_role", catalog = "")
public class AccountDataItem {
    private Integer accountId;
    private Integer itemId;

    @Basic
    @Column(name = "account_id", nullable = true)
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "item_id", nullable = true)
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDataItem that = (AccountDataItem) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, itemId);
    }
}
