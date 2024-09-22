package com.example.demo.registra;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class Config {

    @Bean
    @Order(1)
    CommandLineRunner commandLineRunner2(ApplicationContext applicationContext) {
        return args -> {
            registerBean("customService2", CustomService.class, applicationContext);

            // Retrieve the bean and call a method to verify it was registered
            CustomService customService =
                    (CustomService) applicationContext.getBean("customService2");

            System.out.println("from config customService2 -->");
            System.out.println(customService.hashCode());
            customService.doSomething();

            CustomService advancedCustomService =
                    (CustomService) applicationContext.getBean("advancedCustomService");

            System.out.println("from config advanceCustomService-->");
            System.out.println(advancedCustomService.hashCode());
            customService.doSomething();
        };
    }

    private void registerBean(
            String beanName, Class<?> beanClass, ApplicationContext applicationContext) {

        DefaultListableBeanFactory beanFactory =
                (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(beanClass);

        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }
}
