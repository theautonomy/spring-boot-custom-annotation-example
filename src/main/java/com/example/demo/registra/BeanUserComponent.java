package com.example.demo.registra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class BeanUserComponent {

    private final CustomService customService;

    @Autowired
    public BeanUserComponent(CustomService customService) {
        this.customService = customService;
    }

    @PostConstruct
    public void useCustomService() {
        System.out.println("from bean user component -->");
        System.out.println(customService.hashCode());
        customService.doSomething();
    }
}
