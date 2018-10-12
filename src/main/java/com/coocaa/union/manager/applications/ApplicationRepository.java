package com.coocaa.union.manager.applications;

import com.coocaa.union.manager.NoCudRepository;
import com.coocaa.union.manager.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ApplicationRepository extends NoCudRepository<Application, Integer> {

}
