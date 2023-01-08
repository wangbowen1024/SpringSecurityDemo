package com.securitydemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.securitydemo.entity.SysMenu;
import com.securitydemo.entity.SysRole;
import com.securitydemo.entity.SysRoleMenu;
import com.securitydemo.entity.SysUser;
import com.securitydemo.mapper.SysMenuMapper;
import com.securitydemo.mapper.SysRoleMapper;
import com.securitydemo.service.SysUserService;
import com.securitydemo.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wangbowen
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-01-08 15:56:12
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, username);
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 获取用户权限集合
     * @param userId
     * @return
     */
    @Override
    public List<GrantedAuthority> getUserAuthority(Long userId) {

        final ArrayList<String> userAuthorities = new ArrayList<>();

        // 根据用户ID获取角色列表
        final List<SysRole> roleList = sysRoleMapper.selectRolesByUserId(userId);
        for (SysRole sysRole : roleList) {
            userAuthorities.add("ROLE_" + sysRole.getRoleKey());
        }
        // 遍历所有角色，获取权限信息（不重复）
        final HashSet<String> menus = new HashSet<>();
        for (SysRole sysRole : roleList) {
            final List<String> perms = sysMenuMapper.selectPermsByRoleId(sysRole.getId());
            menus.addAll(perms);
        }
        // 参数格式要求：ROLE_角色1,ROLE_角色2,权限1,权限2,权限3
        userAuthorities.addAll(menus);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", userAuthorities));
    }
}




