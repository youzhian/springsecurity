package com.lyv.security.modules.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主页controller
 * @author youzhian
 */
@Controller
public class MainController {

    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public ModelAndView index(){

        ModelAndView mv = new ModelAndView();

        mv.setViewName("index");

        return mv;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }
}
