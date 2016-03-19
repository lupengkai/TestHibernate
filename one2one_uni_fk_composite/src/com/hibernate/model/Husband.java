package com.hibernate.model;

import javax.persistence.*;

/**
 * Created by tage on 3/19/16.
 */
@Entity
public class Husband {
    private int id;
    private String name;
    private Wife wife;

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

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "wifeId", referencedColumnName = "id"), @JoinColumn(name = "wifeName", referencedColumnName = "name")
    }) //改名
    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }
}
