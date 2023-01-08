package com.securitydemo.service;

import com.securitydemo.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysUserServiceTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    void getUserAuthority() {
        System.out.println(sysUserService.getUserAuthority(1L));
    }
}