package com.securitydemo.security;

import com.securitydemo.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 有一个默认实现，类名叫 org.springframework.security.core.userdetails.User 可以直接用，但是这里自定义一个，方便后续扩展功能
 */
public class MyUser implements UserDetails {

    private SysUser sysUser;

    private List<GrantedAuthority> permissions;

    public MyUser() {
    }

    public MyUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public MyUser(SysUser sysUser, List<GrantedAuthority> permissions) {
        this.sysUser = sysUser;
        this.permissions = permissions;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    /**
     * 获取权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    /**
     * 获取密码
     * @return
     */
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    /**
     * 获取用户名
     * @return
     */
    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    /**
     * 下面几个不写true的话可能会有问题
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
