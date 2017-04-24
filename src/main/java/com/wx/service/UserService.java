package com.wx.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.dao.UserMapper;
import com.wx.model.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public List<User> findByParams(Map<String, Object> params){
		return userMapper.selectByParams(params);
	}
}
