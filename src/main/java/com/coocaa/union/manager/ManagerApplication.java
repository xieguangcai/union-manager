package com.coocaa.union.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.security.Principal;

//@EnableAutoConfiguration
@EnableAuthorizationServer
@SpringBootApplication
//@ComponentScan(basePackages="com.coocaa.union")
@Controller
@SessionAttributes("authorizationRequest")
public class ManagerApplication extends WebMvcConfigurationSupport {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
//    }
}
