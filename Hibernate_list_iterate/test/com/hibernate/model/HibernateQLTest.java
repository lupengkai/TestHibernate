package com.hibernate.model;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Created by tage on 3/19/16.
 */

public class HibernateQLTest {
    private static SessionFactory sf = null;

    @BeforeClass
    public static void beforeClass() {
        sf = new Configuration().configure().buildSessionFactory();
    }

    @Test
    public void testSave() {


        Session session = sf.getCurrentSession();


        session.beginTransaction();


        for (int i = 0; i < 10; i++) {
            Category c = new Category();
            c.setName("category" + i);
            session.save(c);
        }

        for (int i = 0; i < 10; i++) {
            Category c = new Category();
            c.setId(1);
            Topic t = new Topic();
            t.setCategory(c);
            t.setTitle("t" + i);
            t.setCreateDate(new Date());
            session.save(t);
        }


        for (int i = 0; i < 10; i++) {
            Topic t = new Topic();
            t.setId(1);
            Msg m = new Msg();
            m.setCont("m" + i);
            m.setTopic(t);
            session.save(m);
        }


        session.getTransaction().commit();

    }


    @Test
    public void testQueryList() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        List<Category> categories = (List<Category>) session.createQuery("from Category").list();
        List<Category> categories2 = (List<Category>) session.createQuery("from Category").list();//执行两遍发两次，不会使用session里的缓存，每次重新加载

        for (Object o : categories) {
            Category category = (Category) o;
            System.out.println(category.getName());
        }


        session.getTransaction().commit();
    }


    @Test
    public void testQueryIterate() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Iterator<Category> categories = (Iterator<Category>) session.createQuery("from Category").iterate();//先把所有主键取出来，用到的时候一个一个取
        Iterator<Category> categories2 = (Iterator<Category>) session.createQuery("from Category").iterate();//执行两遍时。利用了缓存，只执行一次

        while (categories.hasNext()) {
            Category category = categories.next();
            System.out.println(category.getName());
        }


        while (categories2.hasNext()) {
            Category category = categories2.next();
            System.out.println(category.getName());
        }


        session.getTransaction().commit();
    }


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
