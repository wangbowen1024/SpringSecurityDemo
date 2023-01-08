package com.securitydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.securitydemo.entity.SysMenu;
import com.securitydemo.service.SysMenuService;
import com.securitydemo.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author wangbowen
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2023-01-08 22:56:45
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

}




