


import com.hibernate.model.Account;
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

    public void testOperation1() {
        testSave();

        Session session = sf.getCurrentSession();


        session.beginTransaction();
        Account a = (Account) session.load(Account.class, 1);
        int balance = a.getBalance();
        balance = balance - 10;
        a.setBalance(balance);


        session.getTransaction().commit();


    }

    @org.junit.Test

    public void testPessmisticLock() {


        Session session = sf.getCurrentSession();


        session.beginTransaction();
        Account a = (Account) session.load(Account.class, 1, LockMode.UPGRADE);
        int balance = a.getBalance();
        balance = balance - 10;
        a.setBalance(balance);


        session.getTransaction().commit();


    }


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
