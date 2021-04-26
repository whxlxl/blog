package com.lxl.myblog.controller.admin;

import com.lxl.myblog.po.User;
import com.lxl.myblog.service.TagService;
import com.lxl.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes, Model model){
        User user = userService.checkUser(username, password);
        if (user != null){
            session.setAttribute("user",user);
            model.addAttribute("user",user);
            return "admin/index";
        }
        redirectAttributes.addFlashAttribute("message","用户名或者密码错误");
        //这里是重定向不能使用model
        return "redirect:/admin";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
