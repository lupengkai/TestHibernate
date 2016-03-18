package com.hibernate.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tage on 3/16/16.
 */


@Entity
@Table(name="_Teacher")
// @SequenceGenerator(name = "teacherSEQ",sequenceName = "DB_teacherSEQ" )
public class Teacher {

    //@Id //也可以 根据get方法最好，保护封装， getTotalPrice 可以在表中
    private int id;
    private String name;
  /*  private TeacherPK pk;*/
    private int age;
    private Title title;
    private String yourWifeName;
    private Date birthDate;

    @Temporal(TemporalType.DATE)
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Transient
    public String getYourWifeName() {
        return yourWifeName;
    }

    public void setYourWifeName(String yourWifeName) {
        this.yourWifeName = yourWifeName;
    }

    @Enumerated(EnumType.STRING)//EnumType.ORDINAL
    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @Column(name = "_name")
   // @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic //默认可以不写
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)  //默认auto支持所有     最好identity支持 mysql
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacherSEQ")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

/*    @EmbeddedId
    public TeacherPK getPk() {
        return pk;
    }

    public void setPk(TeacherPK pk) {
        this.pk = pk;
    }*/
}
