package com.hibernate.model;

import javax.persistence.*;

/**
 * Created by tage on 3/19/16.
 */
@Entity
@IdClass(WifePK.class)
public class Wife {
    private int id;
    private String name;
    private int age;

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

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
