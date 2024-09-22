package com.example.demo.beans;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BeanInfoService {

    private final ApplicationContext applicationContext;

    public BeanInfoService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<BeanInfo> getAllBeans() {
        DefaultListableBeanFactory beanFactory =
                (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

        String[] beanNames = beanFactory.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        List<BeanInfo> beanInfos = new ArrayList<>();

        for (String beanName : beanNames) {
            Class<?> beanType = beanFactory.getType(beanName);
            if (beanType != null) {
                String[] dependencies = getDependencies(beanFactory, beanName);
                beanInfos.add(new BeanInfo(beanName, beanType.getName(), dependencies));
            }
        }

        return beanInfos;
    }

    private String[] getDependencies(DefaultListableBeanFactory beanFactory, String beanName) {

        Set<String> dependencies = new HashSet<String>();

        if (beanFactory.getBeanDefinition(beanName).getDependsOn() != null) {
            dependencies =
                    Stream.of(beanFactory.getBeanDefinition(beanName).getDependsOn())
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());
        }

        Class<?> beanType = beanFactory.getType(beanName);
        if (beanType != null) {
            dependencies.addAll(getFieldDependencies(beanType));
            // dependencies.addAll(getConstructorDependencies(beanType));
            // dependencies.addAll(getMethodDependencies(beanType));
        }

        return dependencies.toArray(new String[0]);
    }

    private List<String> getFieldDependencies(Class<?> beanType) {
        return Stream.of(beanType.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(field -> field.getType().getName())
                .collect(Collectors.toList());
    }

    private List<String> getConstructorDependencies(Class<?> beanType) {
        return Stream.of(beanType.getDeclaredConstructors())
                .flatMap(constructor -> Stream.of(constructor.getParameterTypes()))
                .map(Class::getName)
                .collect(Collectors.toList());
    }

    private List<String> getMethodDependencies(Class<?> beanType) {
        return Stream.of(beanType.getDeclaredMethods())
                .filter(
                        method ->
                                method.getName().startsWith("set")
                                        && method.getParameterCount() == 1)
                .map(method -> method.getParameterTypes()[0].getName())
                .collect(Collectors.toList());
    }
}
