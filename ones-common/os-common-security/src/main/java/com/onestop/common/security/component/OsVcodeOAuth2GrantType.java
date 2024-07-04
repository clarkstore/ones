package com.onestop.common.security.component;

import cn.hutool.core.util.StrUtil;
import com.onestop.common.security.service.UserService;
import org.minbox.framework.oauth.exception.OAuth2TokenException;
import org.minbox.framework.oauth.grant.OAuth2TokenGranter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * 验证码OAuth2的认证方式实现
 *
 * @author Clark
 * @version 2021-01-11
 * @see OAuth2TokenGranter
 */
@Component
public class OsVcodeOAuth2GrantType implements OAuth2TokenGranter {
    /**
     * 手机号邮箱验证码方式的授权方式
     */
    private static final String GRANT_TYPE_VCODE = "vcode";
    /**
     * 授权参数：手机号
     */
    private static final String PARAM_PHONE = "phone";
    /**
     * 授权参数：邮箱
     */
    private static final String PARAM_MAIL = "mail";
    /**
     * 授权参数：验证码
     */
    private static final String PARAM_CODE = "code";

    /**
     * 自定义系统用户认证服务
     */
    @Autowired
    private UserService userService;

    @Override
    public String grantType() {
        return GRANT_TYPE_VCODE;
    }

    /**
     * 根据自定义的授权参数进行查询用户信息
     *
     * @param parameters
     * @return
     * @throws OAuth2TokenException
     */
    @Override
    public UserDetails loadByParameter(Map<String, String> parameters) throws OAuth2TokenException {
        UserDetails userDetails = null;
        String phone = parameters.get(PARAM_PHONE);
        String mail = parameters.get(PARAM_MAIL);
        String code = parameters.get(PARAM_CODE);

        // 后端验证验证码时使用
        // 区分手机与邮箱验证码
        if (StrUtil.isNotBlank(phone) && StrUtil.isNotBlank(code)) {
            userDetails = userService.findByPhone(phone, code);
        } else if (StrUtil.isNotBlank(mail) && StrUtil.isNotBlank(code)) {
            userDetails = userService.findByMail(mail, code);
        }
        if (ObjectUtils.isEmpty(userDetails)) {
            throw new OAuth2TokenException("用户：" + phone + "，不存在.");
        }
        return userDetails;
    }
}