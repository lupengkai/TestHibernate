package com.hibernate.model;

import javax.persistence.*;

/**
 * Created by tage on 3/16/16.
 */


@Entity
@Table(name="_Teacher")
public class Teacher {
    private int id;
    private String name;
    private int age;
    private String title;
    private String yourWifeName;

    @Transient
    public String getYourWifeName() {
        return yourWifeName;
    }

    public void setYourWifeName(String yourWifeName) {
        this.yourWifeName = yourWifeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic //默认
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
