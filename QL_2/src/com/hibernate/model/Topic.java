package com.hibernate.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tage on 3/22/16.
 */
@NamedQueries(
        {
                @NamedQuery(name = "topic.selectCertainTopic", query = "from Topic t where t.id = :id")
        }
)

@NamedNativeQueries(
        {
                @NamedNativeQuery(name = "topic.select2_5Topic", query = "SELECT * FROM Topic limit 2,5")
        }
)
@Entity
public class Topic {
    private int id;
    private String title;
    private Category category;
    private Date createDate;

    private List<Msg> msgs = new ArrayList<>();


    @OneToMany(mappedBy = "topic")
    public List<Msg> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Msg> msgs) {
        this.msgs = msgs;
    }

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
