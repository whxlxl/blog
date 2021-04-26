package com.lxl.myblog.dao;

import com.lxl.myblog.po.Tag;
import com.lxl.myblog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTypeByName(String name);

    @Query("select t from t_tag t")
    List<Tag> findTop(Pageable pageable);
}
