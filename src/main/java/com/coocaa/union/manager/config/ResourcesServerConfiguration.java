package com.coocaa.union.manager.config;

import com.coocaa.union.manager.components.CCLettuceRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@EnableResourceServer
@Configuration
public class ResourcesServerConfiguration  extends ResourceServerConfigurerAdapter {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources)throws Exception{
        resources.tokenStore(new CCLettuceRedisTokenStore(redisConnectionFactory));

    }
    @Override

    public void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('select')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('select')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('select')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('select')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('select')")
                .and()

                .headers().addHeaderWriter((request, response) -> {
            response.addHeader("Access-Control-Allow-Origin", "*");
            if (request.getMethod().equals("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            }
        });
    }
}
