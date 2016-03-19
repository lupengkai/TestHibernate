package com.hibernate.model;

import javax.persistence.*;

/**
 * Created by tage on 3/19/16.
 */
@Entity
public class Wife {
    private int id;
    private String name;
    private Husband husband;

    @OneToOne(mappedBy = "wife")
    public Husband getHusband() {
        return husband;
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
