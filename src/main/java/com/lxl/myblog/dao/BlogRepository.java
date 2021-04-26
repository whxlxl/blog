package com.lxl.myblog.dao;

import com.lxl.myblog.po.Blog;
import com.lxl.myblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {


    @Query("select b from t_blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from  t_blog b where b.title like ?1 or b.content like ?1 or b.description like ?1")
    Page<Blog> findByQuery(String query, Pageable p);

    @Transactional
    @Modifying
    @Query("update t_blog b set b.views = b.views+1 where b.id =?1")
    void updateViews(Long id);


    @Query("select function('date_format',b.updateTime,'%Y') as year from t_blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from t_blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
