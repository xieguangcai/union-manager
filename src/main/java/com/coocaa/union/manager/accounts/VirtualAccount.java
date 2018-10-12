package com.coocaa.union.manager.accounts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "vuser", types = Account.class)
public interface VirtualAccount {

    @Value("#{target.userName} #{target.accountId}")
    String getFullName();

}
