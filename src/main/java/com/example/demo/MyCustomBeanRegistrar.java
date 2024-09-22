package com.example.demo;

import java.util.Set;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class MyCustomBeanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyCustomBean.class));

        Set<org.springframework.beans.factory.config.BeanDefinition> beanDefinitions =
                scanner.findCandidateComponents("com.example");

        for (org.springframework.beans.factory.config.BeanDefinition beanDefinition :
                beanDefinitions) {
            try {
                Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
                MyCustomBean myCustomBean = beanClass.getAnnotation(MyCustomBean.class);
                String beanName =
                        myCustomBean.value().isEmpty()
                                ? beanClass.getSimpleName()
                                : myCustomBean.value();

                RootBeanDefinition factoryBeanDefinition =
                        new RootBeanDefinition(MyFactoryBean.class);
                factoryBeanDefinition
                        .getConstructorArgumentValues()
                        .addGenericArgumentValue(beanClass);

                registry.registerBeanDefinition(beanName, factoryBeanDefinition);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
