# springsecurity
#####spring security用例

## 一、认证：
    
     1、security可通过表单来认证
     2、可以通过 HttpBasic 来认证
 
## 二、关于用户名密码

######若未配置过用户名密码，则在启动时会随机生成，并输出在控制台日志中，如：security password：xxxxxx
    
######配置用户名密码的方式有三种：
        
#####1、在配置文件（application.yaml）中配置,如：
    spring.security.user.name=javaboy
    spring.security.user.password=123

#####2、通过java代码配置在内存中
######在 Java 代码中配置用户名密码，首先需要我们创建一个 Spring Security 的配置类，集成自 WebSecurityConfigurerAdapter 类，如下：
    @Configuration
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            //下面这两行配置表示在内存中配置了两个用户
            auth.inMemoryAuthentication()
                    .withUser("javaboy").roles("admin").password("$2a$10$OR3VSksVAmCzc.7WeaRPR.t0wyCsIj24k0Bne8iKWV1o.V9wsP8Xe")
                    .and()
                    .withUser("lisi").roles("user").password("$2a$10$p1H8iWa8I4.CA.7Z8bwLjes91ZpY.rYREGHQEInNtAp4NzL6PLKxi");
        }
        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
            
######这里我们在 configure 方法中配置了两个用户，用户的密码都是加密之后的字符串(明文是 123)，从 Spring5 开始，强制要求密码要加密，如果非不想加密，可以使用一个过期的 PasswordEncoder 的实例 NoOpPasswordEncoder，但是不建议这么做，毕竟不安全。<br>Spring Security 中提供了 BCryptPasswordEncoder 密码编码工具，可以非常方便的实现密码的加密加盐，相同明文加密出来的结果总是不同，这样就不需要用户去额外保存盐的字段了，这一点比 Shiro 要方便很多。            

#####3、通过java代码从数据库中读取