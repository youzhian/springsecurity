package com.lyv.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author youzhian
 */
@SpringBootApplication
@MapperScan("com.lyv.security.modules.*.mapper")
public class SecurityApplication {

    public static void main(String [] args){
        SpringApplication.run(SecurityApplication.class,args);
    }
}
