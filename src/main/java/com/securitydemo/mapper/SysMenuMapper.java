package com.securitydemo.mapper;

import com.securitydemo.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.securitydemo.entity.SysRole;

import java.util.List;

/**
* @author wangbowen
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2023-01-08 22:56:45
* @Entity com.securitydemo.entity.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> selectPermsByRoleId(Long roleId);
}




