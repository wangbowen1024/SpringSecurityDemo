package com.securitydemo.service;

import com.securitydemo.entity.SysUser;
import com.securitydemo.utils.Result;

public interface LoginService {

    Result login(SysUser sysUser);
}
