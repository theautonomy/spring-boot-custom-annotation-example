package com.example.demo;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean<T> implements FactoryBean<T> {

    private Class<T> beanClass;

    public MyFactoryBean(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public T getObject() throws Exception {
        // Create the instance of the bean class
        return beanClass.getDeclaredConstructor().newInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return this.beanClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}