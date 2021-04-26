package com.lxl.myblog.controller;

import com.lxl.myblog.po.Tag;
import com.lxl.myblog.po.Type;
import com.lxl.myblog.service.BlogService;
import com.lxl.myblog.service.TagService;
import com.lxl.myblog.service.TypeService;
import com.lxl.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model,@PathVariable Long id){
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1 && tags.size() > 0){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTypeId",id);
        return "tags";
    }

}
