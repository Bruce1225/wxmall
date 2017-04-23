package com.wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jeff on 2017/4/23.
 */
@Controller
@RequestMapping("/v1")
public class HelloController {

    @RequestMapping("/hello")
    public String helloPage(){
        return "hello";
    }

}
