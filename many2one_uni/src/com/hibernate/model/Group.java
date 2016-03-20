package com.hibernate.model;

import javax.persistence.*;

/**
 * Created by tage on 3/20/16.
 */
@Entity
@Table(name = "t_group")
public class Group {
    private int id;
    private String name;

    @Id
    @Column(name = "group_id")
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
