package com.lxl.myblog.service;

import com.lxl.myblog.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
