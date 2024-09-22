package com.example.demo.beans;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeanController {

    private final BeanInfoService beanInfoService;

    @Autowired
    public BeanController(BeanInfoService beanInfoService) {
        this.beanInfoService = beanInfoService;
    }

    @GetMapping("/beans")
    public String beans(Model model) {
        List<BeanInfo> beans = beanInfoService.getAllBeans();
        model.addAttribute("beans", beans);
        return "beans";
    }
}