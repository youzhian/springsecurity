package com.lyv.security.modules.login.controller;

import com.lyv.security.modules.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录Controller
 * @author youzhian
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

    @GetMapping("/login")
    public String login(){

        return "login/login";
    }

    @PostMapping("/doLogin")
    public String login(String username,String poassword){

        return "index";
    }
    @GetMapping("/logout")
    public String logout(){
        return "login/login";
    }
}
