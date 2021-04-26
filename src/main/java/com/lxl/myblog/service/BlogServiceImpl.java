package com.lxl.myblog.service;

import com.lxl.myblog.NotFoundException;
import com.lxl.myblog.dao.BlogRepository;
import com.lxl.myblog.po.Blog;
import com.lxl.myblog.po.Type;
import com.lxl.myblog.util.MarkdownUtils;
import com.lxl.myblog.util.MyBeanUtils;
import com.lxl.myblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    public Blog getAndConvertBlog(Long id) {
        Blog one = blogRepository.getOne(id);
        if (one == null) throw new NotFoundException("博客没找到");
        Blog blog = new Blog();
        BeanUtils.copyProperties(one,blog);

        String content = one.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensitons(content));
        blogRepository.updateViews(id);
        return blog;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long id, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Object, Object> tags = root.join("tags");

                return criteriaBuilder.equal(tags.get("id"),id);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort updateTime = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest of = PageRequest.of(0, size, updateTime);
        return blogRepository.findTop(of);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> list = blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year:list){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() != null){
            blog.setUpdateTime(new Date());
        }else {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogRepository.getOne(id);
        if (one == null) throw new NotFoundException("该博客不存在");

        blog.setUpdateTime(new Date());
        BeanUtils.copyProperties(blog,one, MyBeanUtils.getNullPropertyNames(blog));
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
