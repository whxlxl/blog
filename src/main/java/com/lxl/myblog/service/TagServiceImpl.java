package com.lxl.myblog.service;

import com.lxl.myblog.NotFoundException;
import com.lxl.myblog.dao.TagRepository;
import com.lxl.myblog.po.Tag;
import com.lxl.myblog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag saveType(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getType(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Page<Tag> listTage(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTage() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTage(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort by = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0,size,by);
        return tagRepository.findTop(pageable);
    }

    public List<Long>  convertToList(String ids){
        List<Long> longs = new ArrayList<>();
        String[] split = ids.split(",");
        if (split.length > 0){
            for (String i:split){
                boolean matches = i.matches("[0-9]+");
                if (matches){
                    if (!tagRepository.existsById(new Long(i))){
                        //不存在
                        Tag t = new Tag();
                        t.setName(i);
                        t = tagRepository.save(t);
                        i = String.valueOf(t.getId());
                    }
                }else {
                    Tag t = new Tag();
                    t.setName(i);
                    t = tagRepository.save(t);
                    i = String.valueOf(t.getId());
                }
                longs.add(new Long(i));
            }
        }
        return longs;
    }

    @Override
    public Tag updateType(Long id, Tag tag) {
        Tag tag1 = tagRepository.getOne(id);
        if (tag == null){
            throw new NotFoundException("id不存在");
        }
        BeanUtils.copyProperties(tag,tag1);
        return tagRepository.save(tag1);
    }

    @Override
    public void deleteType(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getTypeByName(String name) {
        return tagRepository.findTypeByName(name);
    }
}
