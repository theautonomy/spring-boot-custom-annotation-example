package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class MyApplication /* implements CommandLineRunner */ {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Bean
    @Order(3)
    CommandLineRunner commandLineRunner(MyComponent component) {
        return args -> component.doStuff();
    }

    @Bean
    @Order(2)
    CommandLineRunner commandLineRunnerForPrintingBeans(ApplicationContext applicationContext) {
        return args -> {
            DefaultListableBeanFactory beanFactory =
                    (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

            String[] beanNames = applicationContext.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                System.out.println(
                        beanName
                                + " : "
                                + applicationContext.getBean(beanName).getClass().getName()
                                + " | Scope: "
                                + beanDefinition.getScope());
            }
        };
    }
}
