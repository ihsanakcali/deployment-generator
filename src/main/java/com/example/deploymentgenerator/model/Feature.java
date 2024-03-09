package com.example.deploymentgenerator.model;

public class Feature {
    private String name;
    private String id;

    public Feature(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}