package com.coocaa.union.manager.config;

import com.coocaa.magazine.utils.LdapUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xieguangcai
 * @date 2020/3/24
 */

@Configuration
public class LdapConfig {
    @Bean
    public LdapUtil getLdapUtil() {
        return new LdapUtil();
    }
}
