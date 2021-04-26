package com.lxl.myblog.controller;

import com.lxl.myblog.dao.TypeRepository;
import com.lxl.myblog.po.Type;
import com.lxl.myblog.service.BlogService;
import com.lxl.myblog.service.TypeService;
import com.lxl.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model,@PathVariable Long id){
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1 && types.size() > 0){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery ));
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
