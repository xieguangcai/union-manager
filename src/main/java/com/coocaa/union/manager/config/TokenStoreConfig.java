package com.coocaa.union.manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

//@Configuration
public class TokenStoreConfig {
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    @ConditionalOnProperty(prefix = "security.oauth2", name = "storeType", havingValue = "redis")
//    public TokenStore redisTokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
}
