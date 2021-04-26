package com.lxl.myblog.util;

import cn.hutool.crypto.SecureUtil;

public class Md5Utils {
    public static String code(String str){
        String s = SecureUtil.md5(str);//
        return s;
    }

    public static void main(String[] args) {
        System.out.println(code("123456"));
    }
}
