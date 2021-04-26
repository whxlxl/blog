package com.lxl.myblog.controller;

import com.lxl.myblog.service.TagService;
import com.lxl.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {

    @Autowired
    TagService tagService;
    @Autowired
    TypeService typeService;

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("tags",tagService.listTage());
        model.addAttribute("types",typeService.listType());
        return "about";
    }
}
