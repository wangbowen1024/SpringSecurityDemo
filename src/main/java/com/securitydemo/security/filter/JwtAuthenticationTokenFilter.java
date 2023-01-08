package com.securitydemo.security.filter;

import com.securitydemo.entity.SysUser;
import com.securitydemo.mapper.SysUserMapper;
import com.securitydemo.security.MyUser;
import com.securitydemo.service.SysUserService;
import com.securitydemo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * token认证时候的过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token
        final String token = request.getHeader("Authorization");
        // 如果没有 token 直接放行（有可能没有登陆的情况下）
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            // 这里如果不 return，后续过滤器链执行完还会执行剩余代码，后续如果获取到了 token，那么执行后续代码就会有问题
            return;
        }
        // 解析 token
        String userId;
        try {
            final Claims claims = JwtUtils.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token 非法");
        }
        // 获取用户信息（这里就直接去数据库获取了，后续可以改成 redis 等方案）
        final SysUser sysUser = sysUserMapper.selectById(userId);
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("用户不存在");
        }
        // 构造 Authentication 对象（存入 UserDetails 用户信息、权限信息），存入 SecurityContextHolder
        final MyUser myUser = new MyUser(sysUser, sysUserService.getUserAuthority(sysUser.getId()));
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                myUser, null, myUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
