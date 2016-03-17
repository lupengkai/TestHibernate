package com.hibernate.model;

import java.io.Serializable;

/**
 * Created by tage on 3/17/16.
 */
public class StudentPK implements Serializable {
    private int id;
    private String name;

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


    @Override
    public boolean equals(Object o) {
        if (o instanceof StudentPK) {
            StudentPK pk = (StudentPK)o;
            if (this.id == pk.getId() && this.name.equals(pk.getName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int hashCode() {
        return this.name.hashCode();
    }



}
