package com.coocaa.union.manager.roles;

import com.coocaa.union.manager.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements RoleService {

    @Autowired
    RoleRepository repository;

    @Override
    protected CrudRepository<Role, Integer> getCrudRepostory() {
        return repository;
    }

}
