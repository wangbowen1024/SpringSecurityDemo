package com.securitydemo.service;

import com.securitydemo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
* @author wangbowen
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-01-08 15:56:12
*/
public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    List<GrantedAuthority> getUserAuthority(Long userId);
}
