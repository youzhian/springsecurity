package com.lyv.security.common.config;

import com.lyv.security.modules.common.service.impl.CustomUserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author youzhian
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                //如果允许匿名url，填在下面
                .anyRequest().permitAll()
                //.anyRequest().authenticated()
                .and()
                //设置登录页
                .formLogin()
                //.loginPage("/login")
                //设置登录成功页
                .defaultSuccessUrl("/index").permitAll()
                // 自定义登录用户名和密码参数，默认为username和password
                // .usernameParameter("username")
                // .passwordParameter("password")
                .and()
                .logout().permitAll();

        //关闭CSRF跨域
        http.csrf().disable();
    }
    @Override
    public void configure(WebSecurity web) throws Exception{
        // 设置拦截忽略文件，可以对静态文件放行
        web.ignoring().antMatchers("/css/**","/js/**");
    }
}
