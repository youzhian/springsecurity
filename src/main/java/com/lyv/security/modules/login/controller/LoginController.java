package com.lyv.security.modules.login.controller;

import com.lyv.security.modules.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录Controller
 * @author youzhian
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @PostMapping("/login")
    public String login(String name,String poassword){

        return "index";
    }
}
