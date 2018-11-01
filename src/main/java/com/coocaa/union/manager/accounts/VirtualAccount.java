package com.coocaa.union.manager.accounts;

import org.springframework.beans.factory.annotation.Value;

public interface VirtualAccount {

    @Value("#{target.userName} #{target.accountId}")
    String getFullName();

}
