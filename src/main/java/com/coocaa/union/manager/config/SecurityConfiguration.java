package com.coocaa.union.manager.config;

import com.coocaa.union.manager.auth.BaseUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法级的权限认证
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    BaseUserDetailService userDetailService;

    //通过自定义userDetailsService 来实现查询数据库，手机，二维码等多种验证方式
    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        //采用一个自定义的实现UserDetailsService接口的类
        return userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/", "/index.html", "/oauth/**").permitAll() //允许访问
//            .anyRequest().authenticated() //其他地址的访问需要验证权限
//            .and()
//            .formLogin()
//            .loginPage("/login.html") //登录页
//            .failureUrl("/login-error.html").permitAll()
//            .and()
//            .logout()
//            .logoutSuccessUrl("/index.html");
        http.authorizeRequests().anyRequest().fullyAuthenticated();
        http.formLogin().loginPage("/login").failureUrl("/login?code=").permitAll();
        http.logout().permitAll();
        http.authorizeRequests().antMatchers("/oauth/authorize").permitAll();
    }
    
    /**
     * 用户验证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
    
    /**
     * Spring Boot 2 配置，这里要bean 注入
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static void main(String[] args) {
        String pwd = "xgc2008";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String enpwd = encoder.encode(pwd);
        System.out.println(enpwd);
    }
}