package com.securitydemo.config;

import com.securitydemo.security.filter.JwtAuthenticationTokenFilter;
import com.securitydemo.security.handler.MyAccessDeniedHandler;
import com.securitydemo.security.handler.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// 开启权限校验
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * URL白名单
     */
    private static final String[] WHITE_URL = {
            "/user/login"
    };

    /**
     * 创建 BCryptPasswordEncoder 加解密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理，放入容器用于自定义认证
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * JWT 认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 认证失败处理器
     */
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    /**
     * 鉴权失败处理器
     */
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 基本配置
        http
                // 关闭 csrf
                .csrf().disable()
                // 允许跨域
                .cors()
                // 不通过 session 获取 securityContext
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 开启认证
                .and()
                .authorizeRequests()
                // 指定白名单URL，允许匿名访问
                .antMatchers(WHITE_URL).anonymous()
                // 其余请求都需要进行认证
                .anyRequest().authenticated();

        // 配置过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置异常处理器
        http
                .exceptionHandling()
                // 认证失败处理器
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                // 授权失败处理器
                .accessDeniedHandler(myAccessDeniedHandler);
    }
}
