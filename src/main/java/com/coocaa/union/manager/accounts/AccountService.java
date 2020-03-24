package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.BaseService;
import com.novell.ldap.LDAPEntry;

public interface AccountService extends BaseService<Account, Integer>{
    Account findByNickName(String nickName);

    void saveAccountRole(Integer accountId,Integer[] roleIds, Integer[] userDataItems );

    Account createNew(LDAPEntry entry);
}
