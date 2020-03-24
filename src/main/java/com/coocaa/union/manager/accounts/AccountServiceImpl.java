package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.BaseServiceImpl;
import com.coocaa.union.manager.exception.BaseJSONException;
import com.coocaa.union.manager.exception.ErrorCodes;
import com.coocaa.union.manager.roles.Role;
import com.novell.ldap.LDAPEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 账号相关service
 * @author xieguangcai
 * @date 2020-02-18
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Integer> implements AccountService {

    @Autowired
    AccountRepository repository;

    @Override
    protected CrudRepository<Account, Integer> getCrudRepostory() {
        return repository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Account findByNickName(String nickName) {
        Account account = repository.findAccountByNickName(nickName).orElseThrow(()-> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        Set<Role> roles = account.getRoles();
        logger.info("获取角色的个数为：" + roles);
        return account;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAccountRole(Integer accountId, Integer[] roleIds, Integer[] userDataItems) {
        Account account = repository.findById(accountId).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        account.getRoles().clear();
        if(null != roleIds) {
            for (Integer roleId : roleIds) {
                account.getRoles().add(new Role(roleId));
            }
        }
        account.getDataItems().clear();

        if( null != userDataItems) {
            for (Integer dataItemId : userDataItems) {
                account.getDataItems().add(new DataItems(dataItemId));
            }
        }
        repository.save(account);
    }

    @Override
    public Account createNew(LDAPEntry entry) {
        return null;
    }
}
