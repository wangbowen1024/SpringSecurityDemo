package com.securitydemo.mapper;

import com.securitydemo.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysUserMapperTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void testList() {
        final List<SysUser> sysUsers = sysUserMapper.selectList(null);
        for (SysUser sysUser : sysUsers) {
            System.out.println(sysUser);
        }
    }
}
