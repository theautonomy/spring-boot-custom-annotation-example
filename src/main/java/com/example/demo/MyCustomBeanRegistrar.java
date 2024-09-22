package com.example.demo;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class MyCustomBeanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyCustomBean.class));

        Set<org.springframework.beans.factory.config.BeanDefinition> beanDefinitions = scanner.findCandidateComponents("com.example");

        for (org.springframework.beans.factory.config.BeanDefinition beanDefinition : beanDefinitions) {
            try {
                Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
                MyCustomBean myCustomBean = beanClass.getAnnotation(MyCustomBean.class);
                String beanName = myCustomBean.value().isEmpty() ? beanClass.getSimpleName() : myCustomBean.value();
                
                // Use AbstractBeanDefinition and set a supplier to use BeanFactory
                org.springframework.beans.factory.support.AbstractBeanDefinition abd = 
                    (org.springframework.beans.factory.support.AbstractBeanDefinition) beanDefinition;
                abd.setInstanceSupplier(() -> {
                    try {
                        return beanClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create an instance of class " + beanClass.getName(), e);
                    }
                });
                registry.registerBeanDefinition(beanName, abd);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}