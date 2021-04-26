package com.lxl.myblog;

import com.lxl.myblog.dao.CommentRepository;
import com.lxl.myblog.service.BlogService;
import com.lxl.myblog.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyblogApplicationTests {
    @Autowired
    CommentService commentService;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentRepository repository;
    @Test
    void contextLoads() {
        repository.removeByBlogId(Long.valueOf(9));
    }

}
