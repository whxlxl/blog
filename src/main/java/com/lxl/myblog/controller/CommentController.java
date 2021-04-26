package com.lxl.myblog.controller;

import com.lxl.myblog.po.Comment;
import com.lxl.myblog.po.User;
import com.lxl.myblog.service.BlogService;
import com.lxl.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        User user = (User)session.getAttribute("user");
        Long id = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(id));
        comment.setAvatar("https://picsum.photos/seed/picsum/100/100?image=1805");
        comment.setAdminComment(false);
        if (user!=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        }
        commentService.saveComment(comment);
        return "redirect:/comments/+"+id;
    }
}
