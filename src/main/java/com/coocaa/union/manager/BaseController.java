package com.coocaa.union.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    public String encryptPwd(String pwd){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pwd);
    }
}
