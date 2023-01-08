package com.securitydemo.mapper;

import com.securitydemo.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author wangbowen
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2023-01-08 22:56:45
* @Entity com.securitydemo.entity.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectRolesByUserId(Long userId);
}




