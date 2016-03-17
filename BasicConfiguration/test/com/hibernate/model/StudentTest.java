package com.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tage on 3/17/16.
 */
public class StudentTest {
    private static SessionFactory sf = null;
    @BeforeClass
    public static void beforeClass() {
        sf = new Configuration().configure().buildSessionFactory();
    }

    @Test
    public void testStudentSave() {
        Student s = new Student();

        s.setAge(1);


        Session session = sf.openSession();
        session.beginTransaction();
        session.save(s);
        session.getTransaction().commit();
        session.close();

    }

    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}