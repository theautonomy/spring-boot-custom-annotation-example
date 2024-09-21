package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyCustomBeanImportSelector.class)
public class MyCustomBeanAutoConfiguration {}
