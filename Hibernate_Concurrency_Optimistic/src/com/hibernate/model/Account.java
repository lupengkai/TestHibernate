package com.hibernate.model;

import javax.persistence.*;

/**
 * Created by tage on 3/26/16.
 */
@Entity
public class Account {
    private int id;
    private int balance;//BigDecimal
    private int version;

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
