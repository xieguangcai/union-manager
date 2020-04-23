package com.coocaa.union.manager.config;

import com.coocaa.union.manager.components.CCLettuceRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class TokenStoreConfig {
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 检查心跳保持redis连接
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void timer() {
        redisTemplate.opsForValue().get("heartbeat");
    }

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Primary
    @Bean
    @ConditionalOnProperty(prefix = "security.oauth2", name = "store-type", havingValue = "redis")
    public TokenStore redisTokenStore(){
        return new CCLettuceRedisTokenStore(redisConnectionFactory);
    }
}
