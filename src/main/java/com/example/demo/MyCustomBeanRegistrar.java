package com.example.demo;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class MyCustomBeanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // Create scanner and set filter for @MyCustomBean annotation
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyCustomBean.class));

        // Scan the base package
        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents("com.example");

        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
                MyCustomBean myCustomBean = beanClass.getAnnotation(MyCustomBean.class);
                String beanName =
                        myCustomBean.value().isEmpty()
                                ? beanClass.getSimpleName()
                                : myCustomBean.value();

                // Register bean
                registry.registerBeanDefinition(beanName, beanDefinition);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
