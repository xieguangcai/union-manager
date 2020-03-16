package com.coocaa.union.manager.auth;

import com.coocaa.union.manager.accounts.Account;
import com.coocaa.union.manager.accounts.AccountService;
import com.coocaa.union.manager.accounts.DataItems;
import com.coocaa.union.manager.auth.model.SysUserAuthentication;
import com.coocaa.union.manager.roles.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

/**
 * 自定义用户认证Service
 * @author wunaozai
 * @date 2018-06-19
 */
public class BaseUserDetailServiceImpl implements UserDetailsService {


    public BaseUserDetailServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }
    AccountService accountService;

    private static final Logger log = LoggerFactory.getLogger(BaseUserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        System.out.println(username);
        SysUserAuthentication user = null;

        Account account = accountService.findByNickName(username);

//        if("admin".equals(username)) {
//            //这里可以通过auth 获取 user 值
//            //然后根据当前登录方式type 然后创建一个sysuserauthentication 重新设置 username 和 password
//            //比如使用手机验证码登录的， username就是手机号 password就是6位的验证码{noop}000000
//            List<GrantedAuthority> list = AuthorityUtils.createAuthorityList("admin_role"); //所谓的角色，只是增加ROLE_前缀
//            user = new SysUserAuthentication();
//            user.setUsername(username);
//            user.setPassword("{noop}123456");
//            user.setAuthorities(list);
//            user.setAccountNonExpired(true);
//            user.setAccountNonLocked(true);
//            user.setCredentialsNonExpired(true);
//            user.setEnabled(true);
//
//            //user = new User(username, "{noop}123456", list);
//        }

        user = new SysUserAuthentication();
        user.setUsername(username);
        user.setPassword("{bcrypt}" + account.getPwd());
        //用户角色

        Set<String> roleKey = new HashSet<>();
        for (Role role:account.getRoles()){
            roleKey.add(role.getRoleKey());
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
        user.setEnabled(account.getAccountStatus() == 1);

        Map<String, List<String>> userDataGroups = new HashMap<>();
        for(DataItems item: account.getDataItems()) {
            List<String> itemKeys = userDataGroups.get(item.getDataGroup().getKey());
            if ( null == itemKeys) {
                itemKeys = new ArrayList<>();
                userDataGroups.put(item.getDataGroup().getKey(), itemKeys);
            }
            itemKeys.add(item.getValue());
        }
        user.setDataItems(userDataGroups);
        return user;//返回UserDetails的实现user不为空，则验证通过
    }
}