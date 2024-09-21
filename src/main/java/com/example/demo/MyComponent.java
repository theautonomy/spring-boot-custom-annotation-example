package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class MyComponent {
    
    private final MyService service;

    public MyComponent(MyService service) {
        this.service = service;
    }

    public void doStuff() {
        service.serve();
    }
    
}
