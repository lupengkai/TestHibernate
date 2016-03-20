package com.hibernate.model;

import javax.persistence.*;

/**
 * Created by tage on 3/19/16.
 */

public class Wife {

    private String wifeName;
    private int wifeAge;

    @Column(name = "wifename")//改名字避免冲突
    public String getWifeName() {
        return wifeName;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public int getWifeAge() {
        return wifeAge;
    }

    public void setWifeAge(int wifeAge) {
        this.wifeAge = wifeAge;
    }
}
