package com.zjx.security.app;

import com.zjx.security.core.properties.OAuth2ClientProperties;
import com.zjx.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author: zjx
 * @date: 2019/2/28 9:13
 */
@Configuration
@EnableAuthorizationServer
public class ZjxAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    /**
     * 关于endpoint的相关配置
     * @author: zjx
     * @date 14:03 2019/3/22
     * @param endpoints
     * @return void
     **/
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }


    /**
     * 关于客户端配置
     * @author: zjx
     * @date 14:03 2019/3/22
     * @param clients
     * @return void
     **/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();

        if (ArrayUtils.isNotEmpty(securityProperties.getoAuth2().getClients())) {
            for (OAuth2ClientProperties config : securityProperties.getoAuth2().getClients()) {
                //与application.yml中的client-id相同,如果此处配置,则yml中不生效
                builder.withClient(config.getClientId())
                        //与application.yml中的client-secret相同,如果此处配置,则yml中不生效
                        .secret(config.getClientSecret())
                        //令牌有效时间
                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                        //支持的认证模式("refresh_token","password","implicit","authorization_code","client_credentials")
                        .authorizedGrantTypes(config.getAuthorizedGrantTypes())
                        //可使用权限,如果此处配置,则认证时的请求只可在这个范围内的,超出范围则报错,如果没带scope,则用该处配置的scope
                        .scopes(config.getScopes());
            }
        }
    }
}
