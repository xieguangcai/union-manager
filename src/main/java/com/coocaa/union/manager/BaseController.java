package com.coocaa.union.manager;

import com.coocaa.union.manager.auth.model.SysUserAuthentication;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;
import java.util.Map;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    public String encryptPwd(String pwd){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pwd);
    }

    public boolean validPwd(String pwd, String encryptpwd){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(pwd, encryptpwd);
    }

    public String getPrincipalName(Principal user){
        if(user instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) user;
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)auth2Authentication.getUserAuthentication();
            SysUserAuthentication authentication = (SysUserAuthentication)token.getPrincipal();
            return authentication.getName();
        }
        return "";
    }

    /**
     * 获取授权用户的clientId
     * @param user
     * @return
     */
    public Integer getAppId(Principal user){
        if(user instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) user;
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)auth2Authentication.getUserAuthentication();
            SysUserAuthentication authentication = (SysUserAuthentication)token.getPrincipal();
            return authentication.getAppId();
        }
        return null;
    }
}
