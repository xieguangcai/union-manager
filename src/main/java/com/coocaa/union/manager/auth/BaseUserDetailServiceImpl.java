package com.coocaa.union.manager.auth;

import com.coocaa.magazine.utils.LdapUtil;
import com.coocaa.union.manager.accounts.Account;
import com.coocaa.union.manager.accounts.AccountService;
import com.coocaa.union.manager.accounts.DataItems;
import com.coocaa.union.manager.auth.model.SysUserAuthentication;
import com.coocaa.union.manager.models.Roles;
import com.coocaa.union.manager.roles.Role;
import com.coocaa.union.manager.utils.HttpContextUtils;
import com.novell.ldap.LDAPEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

/**
 * 自定义用户认证Service
 * @author wunaozai
 * @date 2018-06-19
 */
public class BaseUserDetailServiceImpl implements UserDetailsService {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    AccountService accountService;
    LdapUtil ldapUtil;

    public BaseUserDetailServiceImpl(AccountService accountService, LdapUtil ldapUtil) {
        this.accountService = accountService;
        this.ldapUtil = ldapUtil;
    }
    private static final Logger log = LoggerFactory.getLogger(BaseUserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        SysUserAuthentication user = null;
        String password = HttpContextUtils.getHttpServletRequest().getParameter("password");
        Account account = null;
        try {
            account = accountService.findByNickName(username);
        }catch (Exception ex) {
            log.info("找不到账号信息:" + username);
        }

        if(account == null){
            LDAPEntry entry = getLDAPUserInfo(username, password);
            //创建本地账号
            account = accountService.createNew(entry);
        } else if (account.getType() == 2) {
            //域账号验证密码
            LDAPEntry entry = getLDAPUserInfo(username, password);
        }

        user = new SysUserAuthentication();
        user.setUsername(username);
        //用户角色
        Set<String> roleKey = new HashSet<>();
        if(account.getAccountStatus() == 1) {
            for (Role role : account.getRoles()) {
                roleKey.add(role.getRoleKey());
            }
        } else {
            if( account.getType() == 2 && account.getAccountStatus() == 3) {
                //域账号未审核通过的
                roleKey.add(Roles.ROLE_NEW_LDAP_USER);
            }
        }
        if(account.getType() == 2) {
            //如果是ldap账号，修改密码规则。
            String encryptPwd = encoder.encode(password);
            user.setPassword("{bcrypt}" + encryptPwd);
        }else {
            user.setPassword("{bcrypt}" + account.getPwd());
        }
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roleKey.toArray(new String[]{}));
        user.setAuthorities(authorities);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        //扩展其他信息
        user.setEmail(account.getEmail());
        user.setAvatar("http://img.sky.fs.skysrt.com/passport/N4enSPuwWkHtBOz8zIDQ.jpg");
        user.setName(account.getUserName());
        user.setEnabled(account.getAccountStatus() == 1 || (account.getType() == 2 && account.getAccountStatus() != 2));

        if(account.getAccountStatus() == 1) {
            Map<String, List<String>> userDataGroups = new HashMap<>();
            for (DataItems item : account.getDataItems()) {
                List<String> itemKeys = userDataGroups.get(item.getDataGroup().getKey());
                if (null == itemKeys) {
                    itemKeys = new ArrayList<>();
                    userDataGroups.put(item.getDataGroup().getKey(), itemKeys);
                }
                itemKeys.add(item.getValue());
            }
            user.setDataItems(userDataGroups);
        }
        return user;//返回UserDetails的实现user不为空，则验证通过
    }


    private LDAPEntry getLDAPUserInfo(String username, String password) throws UsernameNotFoundException {
        LDAPEntry userEntity = this.ldapUtil.getUserInfo(username, password);
        //从域账号获取
        if(userEntity == null){
            throw new UsernameNotFoundException(username);
        }
        log.info("获取到的域账号信息为：" + userEntity);
        return userEntity;
    }
}