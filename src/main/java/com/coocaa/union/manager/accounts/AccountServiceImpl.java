package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.BaseServiceImpl;
import com.coocaa.union.manager.exception.BaseJSONException;
import com.coocaa.union.manager.exception.ErrorCodes;
import com.coocaa.union.manager.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Integer> implements AccountService {

    @Autowired
    AccountRepository repository;

    @Override
    protected CrudRepository<Account, Integer> getCrudRepostory() {
        return repository;
    }
    @Transactional
    @Override
    public Account findByNickName(String nickName) {
        Account account = repository.findAccountByNickName(nickName).orElseThrow(()-> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        Set<Role> roles = account.getRoles();
        logger.info("获取角色的个数为：" + roles);
        return account;
    }

    @Transactional
    @Override
    public void saveAccountRole(Integer accountId, Integer[] roleIds) {
        Account account = repository.findById(accountId).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        account.getRoles().clear();
        for (Integer roleId : roleIds) {
            account.getRoles().add(new Role(roleId));
        }
        repository.save(account);
    }
}
