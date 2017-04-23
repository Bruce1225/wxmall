package com.wx.dao;

import java.util.List;
import java.util.Map;

import com.wx.model.User;
import com.wx.model.UserExample;

public interface UserMapper {
    public int countByExample(UserExample example);

    public List<User> selectByExample(UserExample example);

    public int countByParams(Map<String,Object> params);

    public List<User> selectByParams(Map<String,Object> params);
}