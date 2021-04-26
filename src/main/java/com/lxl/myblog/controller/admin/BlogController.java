package com.lxl.myblog.controller.admin;

import com.lxl.myblog.po.Blog;
import com.lxl.myblog.po.Tag;
import com.lxl.myblog.po.Type;
import com.lxl.myblog.po.User;
import com.lxl.myblog.service.BlogService;
import com.lxl.myblog.service.CommentService;
import com.lxl.myblog.service.TagService;
import com.lxl.myblog.service.TypeService;
import com.lxl.myblog.vo.BlogQuery;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.nio.cs.US_ASCII;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {
    public static final String INPUT = "admin/blogs-input";
    public static final String LIST = "admin/blogs";
    public static final String REDIRECT_LIST = "redirect:/admin/blogs";
    @Autowired
    BlogService blogService;

    @Autowired
    TagService tagService;

    @Autowired
    TypeService typeService;

    @Autowired
    CommentService commentService;

    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("tags",tagService.listTage());
        model.addAttribute("blogs",blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("blogs",blogService.listBlog(pageable,blog));
        return "admin/blogs::blogList";
    }


    @GetMapping("/blogs/input")
    public String input(Model model){
        sendTypeAndAg(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    public void sendTypeAndAg(Model model){
        model.addAttribute("tags",tagService.listTage());
        model.addAttribute("types",typeService.listType());
    }

    @GetMapping("/blogs/{id}/input")
    public String edit(@PathVariable Long id, Model model){
        sendTypeAndAg((model));
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes redirectAttributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTage(blog.getTagIds()));
        Blog b = null;
        if (blog.getId() == null)
            b = blogService.saveBlog(blog);
        else b = blogService.updateBlog(blog.getId(),blog);

        if (b == null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
//        commentService.deleteComment(blogService.getBlog(id));
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}
