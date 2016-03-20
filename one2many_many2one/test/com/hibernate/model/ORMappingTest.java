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
    public void testSaveUser() {
        User u = new User();
        u.setName("u1");
        Group g = new Group();
        g.setName("g1");
        u.setGroup(g);

        Session session = sf.getCurrentSession();


        session.beginTransaction();
     /*   session.save(g);
        session.save(u);*/

        /*session.save(u);*/// fail   object references an unsaved transient instance - save the transient instance before flushing: com.hibernate.model.Group

        session.save(u);//cascade success 从多的一方保存容易

        session.getTransaction().commit();

    }

    @Test//只存了group一条记录
    public void testSaveGroup1() {
        User u = new User();
        u.setName("u1");
        Group g = new Group();
        g.setName("g1");
        u.setGroup(g);

        Session session = sf.getCurrentSession();


        session.beginTransaction();
        session.save(g);
        session.getTransaction().commit();
    }

    @Test
    public void testSaveGroup2() {
        User u1 = new User();
        u1.setName("u1");
        User u2 = new User();
        u2.setName("u2");
        Group g = new Group();
        g.setName("g1");
        g.getUsers().add(u1);
        g.getUsers().add(u2);
        u1.setGroup(g);
        u2.setGroup(g);


        Session session = sf.getCurrentSession();


        session.beginTransaction();
        // session.save(g);未设cascade只存了group的一条记录

        //手动存u1，u2，再存group 或者设一下相互关系
        session.save(g);
        session.getTransaction().commit();
    }


    @Test
    public void testGetUser() {
        testSaveGroup2();
        Session session = sf.getCurrentSession();


        session.beginTransaction();

        User u = (User) session.get(User.class, 1);//取了user和group数据，即使没有cascade，many2one默认取one那方,fetch设成lazy不取group
        //System.out.println(u.getGroup().getName());//lazy,用到时再取group

        session.getTransaction().commit();

        //System.out.println(u.getGroup().getName());lazy报错，没session取不到


    }

    @Test
    public void testGetGroup() {
        testSaveGroup2();
        Session session = sf.getCurrentSession();


        session.beginTransaction();
        Group g = (Group) session.get(Group.class, 1);//只取了roup数据，即使设了cascade（所以与cascade无关，不涉及读取， fetch管读取），many2one默认取many顺带取one那方，反之不然
        session.getTransaction().commit();


        //不用session去取 ，eager早已取出来
        for (User u : g.getUsers()) {
            System.out.println(u.getName());
        }


    }


    @Test
    public void testLoadUser() {
        testSaveGroup2();
        Session session = sf.getCurrentSession();


        session.beginTransaction();

        User u = (User) session.load(User.class, 1);//load 不取
        System.out.println(u.getGroup().getName());//lazy,用到时再取group,取2次

        session.getTransaction().commit();

        //System.out.println(u.getGroup().getName());lazy报错，没session取不到


    }

    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
