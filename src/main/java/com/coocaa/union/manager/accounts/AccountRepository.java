package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.NoCudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

public interface AccountRepository extends NoCudRepository<Account, Integer> {

    Page<Account> getAccountsByNickNameStartingWith(@Param("nickName") String nickName, Pageable pageable);

    Page<Account> findAccountsByUserNameStartingWithAndNickNameStartingWith(@Param("userName")String userName, @Param("nickName")String nickName, Pageable pageable);

}
