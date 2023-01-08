package com.securitydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.securitydemo.entity.SysRole;
import com.securitydemo.service.SysRoleService;
import com.securitydemo.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author wangbowen
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2023-01-08 22:56:45
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

}




