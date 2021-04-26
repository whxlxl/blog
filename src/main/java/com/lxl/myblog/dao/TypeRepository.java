package com.lxl.myblog.dao;

import com.lxl.myblog.po.Type;
import com.lxl.myblog.po.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findTypeByName(String name);

    @Query("select t from t_type t")
    List<Type> findTop(Pageable pageable);
}
