package com.onestop.common.security.service;

import com.onestop.common.security.model.entity.SystemUser;
import org.minbox.framework.security.delegate.SecurityStoreDelegate;

/**
 * 自定义用户验证
 *
 * @author Clark
 * @version 2020-12-29
 */
public interface UserService extends SecurityStoreDelegate {
    /**
     * 短信验证码逻辑
     * @param phone
     * @param code
     * @return
     */
    SystemUser findByPhone(String phone, String code);
    /**
     * 邮箱验证码逻辑
     * @param mail
     * @param code
     * @return
     */
    SystemUser findByMail(String mail, String code);
}
