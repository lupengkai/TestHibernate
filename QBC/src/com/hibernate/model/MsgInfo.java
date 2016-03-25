package com.hibernate.model;

/**
 * Created by tage on 3/22/16.
 */
public class MsgInfo {//vo / dto 装临时的值 数据彼此之间没有强联系,没有逻辑意义。
    private int id;
    private String topicName;
    private String categoryName;


    public MsgInfo(int id, String topicName, String categoryName) {
        super();
        this.id = id;
        this.topicName = topicName;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
