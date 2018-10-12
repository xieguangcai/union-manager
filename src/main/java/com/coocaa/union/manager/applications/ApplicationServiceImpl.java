package com.coocaa.union.manager.applications;

import com.coocaa.union.manager.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl extends BaseServiceImpl<Application, Integer> implements ApplicationService {

    @Autowired
    ApplicationRepository repository;

    @Override
    protected CrudRepository<Application, Integer> getCrudRepostory() {
        return repository;
    }

}
