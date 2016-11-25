package com.william.dream.bean;

/**
 * Created by william on 16-11-25.
 */
public class User {

    public final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return "Name: $name";
    }

}
