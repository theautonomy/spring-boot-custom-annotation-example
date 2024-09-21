package com.example.demo;

@MyCustomBean("myCustomService")
public class MyService {
    public void serve() {
        System.out.println("Service method called");
    }
}
