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
        StudentPK pk = new StudentPK();
        pk.setName("s1");
        pk.setId(1);
        Student s = new Student();
        s.setAge(1);
        s.setPk(pk);



        Session session = sf.openSession();
        session.beginTransaction();
        session.save(s);
        session.getTransaction().commit();
        session.close();

    }

    @Test
    public void testTeacherSave() {
        TeacherPK pk = new TeacherPK();
        pk.setId(1);
        pk.setName("233");
        Teacher t = new Teacher();
        t.setPk(pk);
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
