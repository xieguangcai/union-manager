package com.coocaa.union.manager.auth;

import com.coocaa.union.manager.applications.Application;
import com.coocaa.union.manager.applications.ApplicationService;
import com.coocaa.union.manager.exception.BaseJSONException;
import com.coocaa.union.manager.exception.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 自定义客户端认证
 * @author wunaozai
 * @date 2018-06-20
 */
@Service
public class BaseClientDetailServiceImpl implements ClientDetailsService {

    private static final Logger log = LoggerFactory.getLogger(BaseClientDetailServiceImpl.class);

    @Autowired
    ApplicationService applicationService;

    @Autowired
    BaseClientDetails clientDetails;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails client = null;
        Application application = applicationService.findByAppKey(clientId);
        if(null == application) {
            throw new BaseJSONException(ErrorCodes.NO_CLIENTID);
        }
        if(application.getStatus() != 1) {
            throw new BaseJSONException(ErrorCodes.CLIENT_ID_INVALID, application.getAppKey());
        }
        client = new BaseClientDetails();
        client.setClientId(application.getAppKey());
        client.setClientSecret("{noop}" + application.getAppSecret());
        client.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "password", "refresh_token"));
        client.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ALL"));
        client.setRegisteredRedirectUri(Collections.emptySet());
        return client;
//        BaseClientDetails client = null;
        //这里可以改为查询数据库
//        if("client".equals(clientId)) {
//            log.info(clientId);
//            client = new BaseClientDetails();
//            client.setClientId(clientId);
//            client.setClientSecret("{noop}123456");
//            //client.setResourceIds(Arrays.asList("order"));
//            client.setAuthorizedGrantTypes(Arrays.asList("authorization_code",
//                    "client_credentials","refresh_token", "password", "implicit"));
//            //不同的client可以通过 一个scope 对应 权限集
//            client.setScope(Arrays.asList("all", "select"));
//            client.setAuthorities(AuthorityUtils.createAuthorityList("admin_role"));
//            client.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); //1天
//            client.setRefreshTokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(1)); //1天
//            Set<String> uris = new HashSet<>();
//            uris.add("http://localhost:8080/login");
//            client.setRegisteredRedirectUri(uris);
//        }
//        if(client == null) {
//            throw new NoSuchClientException("No client width requested id: " + clientId);
//        }
//        return client;
//        return clientDetails;
    }

    @Configuration
    @ConditionalOnMissingBean({BaseClientDetails.class})
    protected static class BaseClientDetailsConfiguration {
        private final OAuth2ClientProperties client;

        protected BaseClientDetailsConfiguration(OAuth2ClientProperties client) {
            this.client = client;
        }

        @Bean
        @ConfigurationProperties(
                prefix = "security.oauth2.client"
        )
        public BaseClientDetails oauth2ClientDetails() {
            BaseClientDetails details = new BaseClientDetails();
            if (this.client.getClientId() == null) {
                this.client.setClientId(UUID.randomUUID().toString());
            }

            details.setClientId(this.client.getClientId());
            details.setClientSecret(this.client.getClientSecret());
            details.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "password", "client_credentials", "implicit", "refresh_token"));
            details.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
            details.setRegisteredRedirectUri(Collections.emptySet());
            return details;
        }
    }
}