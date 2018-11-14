package com.coocaa.union.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

/**
 * open-session-in-view 的拦截器
 */
@Configuration
public class MyFilter {

    @Bean
    public OpenEntityManagerInViewFilter inViewFilter(){
        return new OpenEntityManagerInViewFilter();
    }
    
}