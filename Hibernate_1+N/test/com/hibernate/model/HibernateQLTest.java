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

            Topic t = new Topic();
            t.setCategory(c);
            t.setTitle("t" + i);
            t.setCreateDate(new Date());
            session.save(c);
            session.save(t);
        }


        session.getTransaction().commit();

    }

    @Test
    public void testQuery1() {//只想取一个，结果取了其他表的很多内容， 1 + N
        //1. fetchtype = lazy
        //2.
        //3.
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        //List<Topic> topics = (List<Topic>)session.createCriteria(Topic.class).list();//默认用表连接取
        List<Topic> topics = (List<Topic>) session.createQuery("from Topic t").list();

        for (Topic t : topics) {
            System.out.println(t.getId() + "_" + t.getTitle());
        }

        session.getTransaction().commit();

    }

    //BatchSize //@BatchSize
    @Test
    public void testQuery3() {//只想取一个，结果取了其他表的很多内容， 1 + N
        //1. fetchtype = lazy
        //2.natchsize
        //3.
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        List<Topic> topics = (List<Topic>) session.createQuery("from Topic t").list();

        for (Topic t : topics) {
            System.out.println(t.getId() + "_" + t.getTitle());
        }

        session.getTransaction().commit();

    }


    //join fetch
    @Test
    public void testQuery4() {//只想取一个，结果取了其他表的很多内容， 1 + N
        //1. fetchtype = lazy
        //2.natchsize
        //3.join fetch
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        List<Topic> topics = (List<Topic>) session.createQuery("from Topic t left join fetch t.category").list();

        for (Topic t : topics) {
            System.out.println(t.getId() + "_" + t.getTitle());
        }

        session.getTransaction().commit();

    }


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
