package com.hibernate.model;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
    public void testQBC() {//query by criteria
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        //Criteria c = session.createCriteria(Topic.class).add(Restrictions.gt("id",2));//greater than id > 2
        //Criteria c = session.createCriteria(Topic.class).add(Restrictions.lt("id",8));//littler than
        // Criteria c = session.createCriteria(Topic.class).add(Restrictions.like("title", "t_"));
        Criteria c = session.createCriteria(Topic.class).createCriteria("category").add(Restrictions.between("id", 3, 5));
        // Criteria c = session.createCriteria(Topic.class).add(Restrictions.between("id",3,5));
//DetachedCriteria 自由创建
        for (Object o : c.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getId() + "-" + t.getTitle());
        }


        session.getTransaction().commit();
    }


    @Test
    public void testQBE() {//query by example
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Topic tExample = new Topic();
        tExample.setTitle("_T");
        Example e = Example.create(tExample)
                .ignoreCase().enableLike();
        Criteria c = session.createCriteria(Topic.class).add(Restrictions.gt("id", 2)).add(Restrictions.lt("id", 8)).add(e);


        for (Object o : c.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getId() + "-" + t.getTitle());
        }


        session.getTransaction().commit();
    }

    //session.clear()


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
