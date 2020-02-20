package com.max.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

        if(file.isEmpty() && item.getPicture().equals("")){
            item.setPicture(null);
            itemRepository.save(item);
            return "redirect:/list";
        }

        else if(file.isEmpty() && item.getPicture() != null){
            itemRepository.save(item);
            return "redirect:/list";
        }


//        if(!file.isEmpty())
//        {item.setPicture(item.getPicture());
//            itemRepository.save(item);
//            return "redirect:/list";
//        }
//
//        else{
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                item.setPicture(uploadResult.get("url").toString());
                itemRepository.save(item);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/add";
            }
            itemRepository.save(item);
//        }
        return "redirect:/list";
    }


//////////////////////////////////////////////////////////////////////////////////
//
//    @PostMapping("/process")
//    public String processForm(@Valid Item item, BindingResult result){
//        if (result.hasErrors()){
//            return "form";
//        }
//        itemRepository.save(item);
//        return "redirect:/list";
//    }


    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("item", itemRepository.findById(id).get());
        return "list";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("item", itemRepository.findById(id).get());
        return "form";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id, Model model) {
        itemRepository.deleteById(id);
        return "redirect:/";
    }





}
