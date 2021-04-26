package com.lxl.myblog.service;

import com.lxl.myblog.po.Blog;
import com.lxl.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Blog getAndConvertBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(Long id,Pageable pageable);
    Page<Blog> listBlog(String query, Pageable pageable);
    List<Blog> listRecommendBlogTop(Integer size);
    Map<String,List<Blog>> archiveBlog();
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    void deleteBlog(Long id);
    Long countBlog();
}
