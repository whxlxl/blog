package com.lxl.myblog.controller;

import com.lxl.myblog.NotFoundException;
import com.lxl.myblog.service.BlogService;
import com.lxl.myblog.service.CommentService;
import com.lxl.myblog.service.TagService;
import com.lxl.myblog.service.TypeService;
import com.lxl.myblog.vo.BlogQuery;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.ASC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(10));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query,
                         Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/tags")
    public String tags(){
        return "tags";
    }
    @GetMapping("/types")
    public String types(){
        return "types";
    }

//    @GetMapping("/about")
//    public String about(){
//        return "about";
//    }

//    @GetMapping("/archives")
//    public String archives(){
//        return "archives";
//    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvertBlog(id));
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        return "blog";
    }


    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newbloglist";
    }
}
