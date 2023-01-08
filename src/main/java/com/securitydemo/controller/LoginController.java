package com.securitydemo.controller;

import com.securitydemo.entity.SysUser;
import com.securitydemo.service.LoginService;
import com.securitydemo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登陆
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/user/login")
    public Result login(@RequestBody SysUser sysUser) {
        return loginService.login(sysUser);
    }
}
