package com.wx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wx.service.UserService;


/**
 * Created by Bruce on 2017/4/24.
 */
@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	public void list(@RequestParam Map<String,Object> param, Model model){
		System.out.println("来啦，欢迎！");
		Integer count = userService == null ? 0 : userService.findByParams(param).size();
		
		model.addAttribute("count", count);
		model.addAttribute("lists", userService.findByParams(param));
		model.addAttribute("param", param);
	}

}
