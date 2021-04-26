package com.lxl.myblog.dao;

import com.lxl.myblog.po.Blog;
import com.lxl.myblog.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

//    @Query("delete from t_comment where ")
    void deleteByBlog(Blog bog);

    void removeByBlogId(Long id);
}
