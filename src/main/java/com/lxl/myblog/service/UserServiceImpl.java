package com.lxl.myblog.service;

import com.lxl.myblog.dao.UserRepository;
import com.lxl.myblog.po.User;
import com.lxl.myblog.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, Md5Utils.code(password));
        return user;
    }
}
