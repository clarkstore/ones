package com.onestop.common.security.config;

import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义RedisToken存储
 * <p>@Primary 覆盖ApiBootAuthorizationServerRedisAutoConfiguration中的RedisTokenStore</p>
 *
 * @author Clark
 * @version 2020-12-23
 * @see org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootAuthorizationServerRedisAutoConfiguration
 */
@Component
@Primary
public class OsRedisTokenStore extends RedisTokenStore {
    public OsRedisTokenStore(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    /**
     * 校验token的流程：
     * 1，在CheckTokenEndpoint.checkToken()校验token时，会先通过resourceServerTokenServices.readAccessToken(value)
     * 2，而resourceServerTokenServices的默认实现就是DefaultTokenServices
     * 3，resourceServerTokenServices.readAccessToken是通过tokenStore.readAccessToken(accessToken)实现的
     * 4，所以在此时刷新token
     * 在这里刷新的优点是：减少了网络交互，无需再从客户端发送/oauth/token?grant_type=refresh_token请求来刷新token
     * 同时，这种自动续签的方式不会重新生成token，如果想重新生成token可以参考DefaultTokenServices.createAccessToken()
     *
     * @param tokenValue
     * @return
     */
    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) super.readAccessToken(tokenValue);
        if (oAuth2AccessToken != null) {
            //1，如果accessToken存在且将要过期(600秒后过期)，就重置accessToken的过期时间。
            if (oAuth2AccessToken.getExpiresIn() < 600) {
                //重置时间：延长7200秒，可以自定义在配置文件中或者通过clientid获取原定的过期时间
                oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + (7200 * 1000L)));
                OAuth2Authentication oAuth2Authentication = super.readAuthentication(tokenValue);
                //2，将重置的过期时间存入session
                super.storeAccessToken(oAuth2AccessToken, oAuth2Authentication);
            }
        }
        return oAuth2AccessToken;
    }
}
