package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration

// Both ways work
// @Import(MyCustomBeanImportSelector.class)

@Import(MyCustomBeanRegistrar.class)
public class MyCustomBeanAutoConfiguration {}
