package com.coocaa.union.manager.accounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author xieguangcai
 * @date 2020/2/17
 */
@Entity
@Table(name = "union_data_items", schema = "union_role", catalog = "")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler","fieldHandler", "dataGroup"})
public class DataItems {
    private int itemId;
    private String value;
    private String text;
    private Integer sortId;
    private Date createTime;
    private Date modifyTime;

    public DataItems() {
    }

    public DataItems(int dataItemId) {
        this.itemId = dataItemId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int id) {
        this.itemId = id;
    }

    @Basic
    @Column(name = "value", nullable = false, length = 50)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "text", nullable = false, length = 50)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "sort_id", nullable = true)
    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modify_time", nullable = true)
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataItems dataItems = (DataItems) o;
        return itemId == dataItems.itemId &&
                Objects.equals(value, dataItems.value) &&
                Objects.equals(text, dataItems.text) &&
                Objects.equals(sortId, dataItems.sortId) &&
                Objects.equals(createTime, dataItems.createTime) &&
                Objects.equals(modifyTime, dataItems.modifyTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, value, text, sortId, createTime, modifyTime);
    }

    private DataGroup dataGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    public DataGroup getDataGroup() {
        return dataGroup;
    }

    public void setDataGroup(DataGroup dataGroup) {
        this.dataGroup = dataGroup;
    }
}
