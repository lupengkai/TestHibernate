package com.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;


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
    public void testSave() {

        Student s = new Student();
        s.setName("zhangsan");
        Course c = new Course();
        c.setName("java");
        Score score = new Score();
        score.setCourse(c);
        score.setStudent(s);

        Session session = sf.getCurrentSession();


        session.beginTransaction();
        //注意顺序
        session.save(s);
        session.save(c);
        session.save(score);

        session.getTransaction().commit();

    }


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
