package com.lxl.myblog.service;

import com.lxl.myblog.po.Blog;
import com.lxl.myblog.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
    void deleteByBlogId(Integer id);
    void deleteComment(Blog blog);
}
