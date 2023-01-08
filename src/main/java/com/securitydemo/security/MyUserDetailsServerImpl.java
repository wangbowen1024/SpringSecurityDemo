package com.securitydemo.security;

import com.securitydemo.entity.SysUser;
import com.securitydemo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 这里自定义一个实现类来替换默认的(登陆时候用)
 */
@Service
public class MyUserDetailsServerImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        final SysUser sysUser = sysUserService.getByUsername(username);
        // 如果没有查询到用户抛出异常
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 查询用户权限信息
        List<GrantedAuthority> userAuthority = sysUserService.getUserAuthority(sysUser.getId());
        // 查询结果封装成自定义UserDetails
        return new MyUser(sysUser, userAuthority);
    }
}
