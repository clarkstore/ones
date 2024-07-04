package com.onestop.common.security.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 系统用户信息，验证用
 *
 * @author Clark
 */
@Builder
@Data
public class SystemUser implements UserDetails {
    @Tolerate
    public SystemUser() {
    }

    /**
     * 用户编号
     */
    private String id;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 用户状态
     * 1：正常，0：已冻结，-1：已删除
     */
    private Integer deleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * UserDetails提供的方法，用户是否未过期
     * 可根据自己用户数据表内的字段进行扩展，这里为了演示配置为true
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * UserDetails提供的方法，用户是否未锁定
     * 可根据自己用户数据表内的字段进行扩展，这里为了演示配置为true
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * UserDetails提供的方法，凭证是否未过期
     * 可根据自己用户数据表内的字段进行扩展，这里为了演示配置为true
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * UserDetails提供的方法，是否启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return this.deleted == 1;
    }
}