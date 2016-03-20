package com.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Created by tage on 3/19/16.
 */

public class ORMappingTest {
    private static SessionFactory sf = null;

    @BeforeClass
    public static void beforeClass() {
        sf = new Configuration().configure().buildSessionFactory();
    }

    @Test
    public void test() {
        Session session = sf.getCurrentSession();


        session.beginTransaction();
        //session.save();
        session.getTransaction().commit();

    } 


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
