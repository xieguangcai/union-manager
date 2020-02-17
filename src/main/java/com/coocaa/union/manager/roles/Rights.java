package com.coocaa.union.manager.roles;

import com.coocaa.union.manager.accounts.DataItems;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xieguangcai
 * @date 2020/2/17
 */
public class Rights {
    private Set<Role> roles = new HashSet<>();
    private Set<DataItems> dataItems = new HashSet<>();

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
}
