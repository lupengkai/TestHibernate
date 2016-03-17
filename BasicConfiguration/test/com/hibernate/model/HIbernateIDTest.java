package com.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

/**
 * Created by tage on 3/17/16.
 */
public class HIbernateIDTest {
    private static SessionFactory sf = null;
    @BeforeClass
    public static void beforeClass() {
        sf = new Configuration().configure().buildSessionFactory();
    }

    @Test
    public void testStudentSave() {
        Student s = new Student();
        s.setName("s1");
        s.setAge(1);


        Session session = sf.openSession();
        session.beginTransaction();
        session.save(s);
        session.getTransaction().commit();
        session.close();

    }

    @Test
    public void testTeacherSave() {
        Teacher t = new Teacher();
        t.setName("t1");
        t.setTitle(Title.A);
        t.setAge(1);
        t.setBirthDate(new Date());

        Session session = sf.openSession();
        session.save(t);
        session.close();

    }

    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
