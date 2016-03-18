package com.hibernate.model;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by tage on 3/17/16.
 */

//@Embeddable
public class TeacherPK implements Serializable{//序列化到硬盘中或传到其他机器上
    private int id;
    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeacherPK teacherPK = (TeacherPK) o;

        if (id != teacherPK.id) return false;
        return name != null ? name.equals(teacherPK.name) : teacherPK.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
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
