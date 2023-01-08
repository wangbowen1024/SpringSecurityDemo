package com.securitydemo.service.impl;

import com.securitydemo.entity.SysUser;
import com.securitydemo.security.MyUser;
import com.securitydemo.service.LoginService;
import com.securitydemo.utils.JwtUtils;
import com.securitydemo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Result login(SysUser sysUser) {

        // 将传入的用户名密码封装成 Authentication 对象
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                sysUser.getUserName(), sysUser.getPassword()
        );
        // 该方法会调用自定义 MyUserDetailsServerImpl 中的 loadUserByUsername 方法，把数据库查到的用户名密码和传入的进行对比
        // 以及将查出来的权限信息再封装到 Authentication 对象
        final Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证失败
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }
        //如果认证通过，利用用户ID生成一个 jwt
        final MyUser principal = (MyUser) authenticate.getPrincipal();
        final String userId = principal.getSysUser().getId().toString();
        // 将 token 返回给前端
        final HashMap<String, String> rs = new HashMap<>();
        rs.put("Authorization", JwtUtils.createJWT(userId));
        return new Result(200, "登入成功", rs);
    }
}
