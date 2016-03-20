package com.hibernate.model;

import javax.persistence.*;

/**
 * Created by tage on 3/20/16.
 */
@Entity
@Table(name = "t_user")
public class User {
    private int id;
    private String name;
    private Group group;

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

    @ManyToOne
//@JoinColumn
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
