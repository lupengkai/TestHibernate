package com.hibernate.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by tage on 3/22/16.
 */

@Entity
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)//如果放在二级缓存之后不能修改
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = simpl...)//一般用这个
public class Category {
    private int id;
    private String name;

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
