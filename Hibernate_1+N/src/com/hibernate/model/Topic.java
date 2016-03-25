package com.hibernate.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tage on 3/22/16.
 */
@Entity
public class Topic {
    private int id;
    private String title;
    private Category category;
    private Date createDate;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne

    @JoinColumn(name = "category_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
