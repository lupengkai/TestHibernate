package com.hibernate.model;


import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;


/**
 * Created by tage on 3/19/16.
 */

public class Test {
    private static SessionFactory sf = null;

    @BeforeClass
    public static void beforeClass() {
        sf = new Configuration().configure().buildSessionFactory();
    }

    @org.junit.Test
    public void testSave() {


        Session session = sf.getCurrentSession();


        session.beginTransaction();
        Account a = new Account();
        a.setBalance(100);
        session.save(a);


        session.getTransaction().commit();

    }

    @org.junit.Test

    public void testOptimisticLock() {
        testSave();

        Session session = sf.openSession();
        Session session2 = sf.openSession();


        session.beginTransaction();
        Account a1 = (Account) session.load(Account.class, 1);


        session2.beginTransaction();
        Account a2 = (Account) session2.load(Account.class, 1);

        a1.setBalance(900);
        a2.setBalance(1000);

        session.getTransaction().commit();
        System.out.println(a1.getVersion());

        session2.getTransaction().commit();
        System.out.println(a2.getVersion());


        session.close();
        session2.close();
    }


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
