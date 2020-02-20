package com.max.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/list")
    public String listItems(Model model){
        model.addAttribute("items", itemRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newItem(Model model){
        model.addAttribute("item", new Item());
        return "form";
    }

    @PostMapping("/add")
    public String processItem(@ModelAttribute Item item, @RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            item.setPicture(uploadResult.get("url").toString());
            itemRepository.save(item);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/list";
    }
}
