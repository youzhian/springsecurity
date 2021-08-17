package com.lyv.security.common.config;

import com.lyv.security.common.filter.VerifyCodeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * security配置类
 * @author youzhian
 */
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Resource
    VerifyCodeFilter verifyCodeFilter;

    /**
     * 配置用户、角色、密码等
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        /**
         * 用户的密码都是加密之后的字符串(明文是 123)，从 Spring5 开始，强制要求密码要加密，如果非不想加密，可以使用一个过期的 PasswordEncoder
         * 的实例 NoOpPasswordEncoder，但是不建议这么做，毕竟不安全
         */
        //增加用户,可增加多个用户
        auth.inMemoryAuthentication()
                .withUser("zhangsan").roles("admin").password("123")
                .and()
                .withUser("lisi").roles("user").password("123")
                .and()
                .withUser("you").roles("admin").password("123");
    }

    /**
     * 权限配置
     * @param http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
        //开启登录配置
        http.authorizeRequests()
            //表示hello接口需要admin角色才能访问
            .antMatchers("/hello").hasRole("admin")
            //表示其他接口只需登录即可访问
            .anyRequest().authenticated()
            .and()
            .formLogin()
            //定义登录页面，未登录时，访问一个需要登录之后才能访问的接口，会自动跳转到该页面
            .loginPage("/login")
            //登录处理接口
            .loginProcessingUrl("/doLogin")
            //定义登录时，登录名的key，默认是username
            .usernameParameter("username")
            //定义登录时，密码的key，默认是password
            .passwordParameter("password")
            //登录成功的处理器
            .successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                                    Authentication authentication) throws IOException, ServletException {

                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("success");
                    out.flush();
                }
            })
                //登录失败处理
            .failureHandler(new AuthenticationFailureHandler() {
                @Override
                public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
                        , AuthenticationException e) throws IOException, ServletException {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("fail");
                    out.flush();
                }
            })
            .permitAll()//和登录相关的接口直接通过
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(new LogoutSuccessHandler() {
                @Override
                public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                            Authentication authentication) throws IOException, ServletException {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("logout success");
                    out.flush();
                }
            })
            .permitAll()
            .and()
            .httpBasic()
            .and()
            .csrf().disable();
    }

    /**
     * 忽略拦截配置
     * @param web
     */
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/login");
    }

    /**
     * Spring Security 中提供的 BCryptPasswordEncoder 密码编码工具，可以非常方便的实现密码的加密加盐，相同明文加密出来的结果总是不同，这样就不需要用户去额外保存盐的字段了
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
