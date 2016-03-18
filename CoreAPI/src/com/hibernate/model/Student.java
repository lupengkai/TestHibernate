package com.hibernate.model;

/**
 * Created by tage on 3/16/16.
 */
public class Student {//native identity uuid(String)
    //private StudentPK pk;
private int id;
    private String name;
    private int age;
/*
    public StudentPK getPk() {
        return pk;
    }

    public void setPk(StudentPK pk) {
        this.pk = pk;
    }*/

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
