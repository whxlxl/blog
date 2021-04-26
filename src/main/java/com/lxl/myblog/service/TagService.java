package com.lxl.myblog.service;

import com.lxl.myblog.po.Tag;
import com.lxl.myblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveType(Tag tag);
    Tag getType(Long id);
    Page<Tag> listTage(Pageable pageable);
    List<Tag> listTage();
    List<Tag> listTage(String ids);
    List<Tag> listTagTop(Integer size);
    Tag updateType(Long id,Tag tag);
    void deleteType(Long id);
    Tag getTypeByName(String name);
}
