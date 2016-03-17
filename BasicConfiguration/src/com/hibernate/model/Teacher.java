package com.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by tage on 3/16/16.
 */


@Entity
public class Teacher {
    private int id;
    private String name;
    private int age;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
