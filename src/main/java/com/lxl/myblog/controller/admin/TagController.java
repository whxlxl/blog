package com.lxl.myblog.controller.admin;

import com.lxl.myblog.po.Tag;
import com.lxl.myblog.po.Type;
import com.lxl.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    TagService tagService;


    @GetMapping("/tags")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("tags",tagService.listTage(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editType(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getType(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        if (tagService.getTypeByName(tag.getName())!=null){
            result.rejectValue("name","nameError","不能重复添加分类");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveType(tag);
        if (t == null){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String post(@Valid Tag tag, BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        if (tagService.getTypeByName(tag.getName())!=null){
            result.rejectValue("name","nameError","名称一样无需修改");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.updateType(id,tag);
        if (t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

}
