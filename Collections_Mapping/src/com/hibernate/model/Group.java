package com.hibernate.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tage on 3/20/16.
 */
@Entity
@Table(name = "t_group")
public class Group {
    private int id;
    private String name;
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = {CascadeType.ALL})
    @OrderBy("name ASC ") //默认按主键
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

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
