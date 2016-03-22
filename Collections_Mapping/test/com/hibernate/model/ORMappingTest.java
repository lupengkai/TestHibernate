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
        g.getUsers().put(1, u1);
        g.getUsers().put(2, u2);
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
        for (Map.Entry<Integer, User> entry : g.getUsers().entrySet()) {
            System.out.println(entry.getValue().getName());
        //System.out.print(g.getUsers().contains(new User()));
        session.getTransaction().commit();

//多方实际中自己存
        //不用session去取 ，eager早已取出来

        }


    }


    @Test
    public void testLoadUser() {
        testSaveGroup2();
        Session session = sf.getCurrentSession();


        session.beginTransaction();

        User u = (User) session.load(User.class, 1);//load 不取
        System.out.println(u.getGroup().getName());//lazy,先单取user，用到时再关联主要取group包括所含user属性,取2次 eager 先关联取user各种属性包括group的,再单取user(因为eager，取group，再取user,比如group里有两个user)

        session.getTransaction().commit();

        //System.out.println(u.getGroup().getName());lazy报错，没session取不到


    }

    @Test
    public void testUpdateUser() {
        testSaveGroup2();
        Session session = sf.getCurrentSession();


        session.beginTransaction();

        User u = (User) session.load(User.class, 1);
        u.setName("user");
        // u.getGroup().setName("ggg");//改了就会更新，与设定无关

        session.getTransaction().commit();


    }

    @Test
    public void testUpdateUser2() {
        testSaveGroup2();
        Session session = sf.getCurrentSession();


        session.beginTransaction();

        User u = (User) session.get(User.class, 1);

        session.getTransaction().commit();
        u.setName("user");
        u.getGroup().setName("hshs");


        Session session2 = sf.getCurrentSession();


        session2.beginTransaction();

        session2.update(u);

        session2.getTransaction().commit();

    }


    @Test
    public void testDeleteUser() {
        testSaveGroup2();

        Session session = sf.getCurrentSession();


        session.beginTransaction();

    /*    User u = (User) session.load(User.class, 1);

        u.setGroup(null);
        session.delete(u);//先load一下（有id就能删t状态）把u1， u2， group都删了， 级联着,所以要把对应关系设null*/


        session.createQuery("delete from User u where u.id = 1").executeUpdate();


        session.getTransaction().commit();


    }

    @Test
    public void testDeleteGroup() {
        testSaveGroup2();

        Session session = sf.getCurrentSession();


        session.beginTransaction();

        Group g = (Group) session.load(Group.class, 1);
        g.setUsers(null);//fail 外键约束

        session.delete(g);//先load一下（有id就能删t状态）把u1， u2， group都删了， 级联着,所以要把对应关系设null


        // session.createQuery("delete from User u where u.id = 1").executeUpdate();


        session.getTransaction().commit();


    }


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
