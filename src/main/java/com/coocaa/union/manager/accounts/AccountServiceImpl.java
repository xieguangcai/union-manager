package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Integer> implements AccountService {

    @Autowired
    AccountRepository repository;

    @Override
    protected CrudRepository<Account, Integer> getCrudRepostory() {
        return repository;
    }

}
