package com.example.demo.beans;

public class BeanInfo {
    private String name;
    private String type;
    private String[] dependencies;

    // Constructors, getters, and setters

    public BeanInfo(String name, String type, String[] dependencies) {
        this.name = name;
        this.type = type;
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getDependencies() {
        return dependencies;
    }

    public void setDependencies(String[] dependencies) {
        this.dependencies = dependencies;
    }
}