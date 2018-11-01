package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.NoCudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends NoCudRepository<Account, Integer> {

    //Page<Account> getAccountsByNickNameStartingWith(@Param("nickName") String nickName, Pageable pageable);

    //Page<Account> findAccountsByUserNameStartingWithAndNickNameStartingWith(@Param("userName")String userName, @Param("nickName")String nickName, Pageable pageable);

    Optional<Account> findAccountByNickName(String nickName);
}
