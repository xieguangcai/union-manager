package com.coocaa.union.manager.accounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author xieguangcai
 * @date 2020/2/17
 */
@Entity
@Table(name = "union_data_group", schema = "union_role", catalog = "")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler","fieldHandler", "items"})
public class DataGroup {
    private int id;
    private String key;
    private String text;
    private Date createTime;
    private Date modifyTime;
    private Integer sortId;

    private Set<DataItems> items;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "key", nullable = false, length = 50)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    @Basic
    @Column(name = "sort_id", nullable = true)
    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataGroup dataGroup = (DataGroup) o;
        return id == dataGroup.id &&
                Objects.equals(key, dataGroup.key) &&
                Objects.equals(text, dataGroup.text) &&
                Objects.equals(createTime, dataGroup.createTime) &&
                Objects.equals(modifyTime, dataGroup.modifyTime) &&
                Objects.equals(sortId, dataGroup.sortId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, text, createTime, modifyTime, sortId);
    }

    @OneToMany(targetEntity = DataItems.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id")
    public Set<DataItems> getItems() {
        return items;
    }

    public void setItems(Set<DataItems> items) {
        this.items = items;
    }
}
