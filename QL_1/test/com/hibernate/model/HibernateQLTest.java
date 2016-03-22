package com.hibernate.model;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
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
    public void testHQL_01() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Category");
        List<Category> categories = (List<Category>) q.list();
        for (Category c : categories) {
            System.out.println(c.getName());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_02() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Category c where c.name > 'category5'");
        List<Category> categories = (List<Category>) q.list();
        for (Category c : categories) {
            System.out.println(c.getName());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_03() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Category c order by c.name desc");//降序
        List<Category> categories = (List<Category>) q.list();
        for (Category c : categories) {
            System.out.println(c.getName());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_04() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select distinct c from Category c order by c.name desc");//降序
        List<Category> categories = (List<Category>) q.list();
        for (Category c : categories) {
            System.out.println(c.getName());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_05() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Category  c where c.id > :min and c.id < :max")//:占位符
                .setInteger("min", 2)
                .setInteger("max", 3);
/*
        q.setParameter("min", 2);
        q.setParameter("max", 8);
*/

  /*      q.setInteger("min", 2);
        q.setInteger("max", 8);*/


        List<Category> categories = (List<Category>) q.list();
        for (Category c : categories) {
            System.out.println(c.getName());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_06() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Category  c where c.id > ? and c.id < ?")
                .setInteger(0, 2)
                .setInteger(1, 8);

        List<Category> categories = (List<Category>) q.list();
        for (Category c : categories) {
            System.out.println(c.getName());
        }

        session.getTransaction().commit();
    }


    @Test
    public void testHQL_07() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Category c order by c.name desc");//降序
        q.setMaxResults(4);//一次4条
        q.setFirstResult(2);
        List<Category> categories = (List<Category>) q.list();
        for (Category c : categories) {
            System.out.println(c.getName());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_08() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select c.id, c.name from Category c order by c.name");
        List<Object[]> categories = (List<Object[]>) q.list();
        for (Object[] o : categories) {
            //System.out.println((Integer) o[0] + "-" +(String)o[1]);
            System.out.println(o[0] + "-" + o[1]);//自动打包
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_09() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Topic t where t.category.id = 1");
        List<Topic> topics = (List<Topic>) q.list();
        for (Topic t : topics) {
            System.out.println(t.getTitle());//eager topic 和 category 都取
            System.out.println(t.getCategory().getName());//lazy 只取topic 因为这句话取category
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_11() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Msg m where m.topic.category.id = 1");

        for (Object o : q.list()) {
            Msg m = (Msg) o;
            System.out.println(m.getCont());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_12() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select new com.hibernate.model.MsgInfo(m.id, m.topic.title, m.topic.category.name) from Msg m");
        for (Object o : q.list()) {
            MsgInfo m = (MsgInfo) o;
            System.out.println(m.getId());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_13() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select t.title, c.name from Topic t join t.category c");//用该成员变量做表连接条件

        for (Object o : q.list()) {
            Object[] m = (Object[]) o;
            System.out.println(m[0] + "-" + m[1]);//自动打包
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_14() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Msg m where m = :MsgToSearch");
        Msg m = new Msg();
        m.setId(1);
        q.setParameter("MsgToSearch", m);
        Msg mResult = (Msg) q.uniqueResult();//知道对象唯一
        System.out.println(mResult.getCont());

        session.getTransaction().commit();
    }


    @Test
    public void testHQL_15() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select  count(*) from Msg m");

        long count = (Long) q.uniqueResult();//知道结果唯一
        System.out.println(count);

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_16() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select max(m.id), min(id), avg(m.id), sum(m.id) from Msg m");

        for (Object o : q.list()) {
            Object[] m = (Object[]) o;
            System.out.println(m[0] + "-" + m[1] + "-" + m[2] + "-" + m[3]);
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_17() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Msg m where m.id between 3 and 5");

        for (Object o : q.list()) {
            Msg m = (Msg) o;
            System.out.println(m.getCont());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_18() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Msg m where m.id in (3,4,5) ");

        for (Object o : q.list()) {
            Msg m = (Msg) o;
            System.out.println(m.getCont());
        }

        session.getTransaction().commit();
    }


    @Test
    public void testHQL_19() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Msg m where m.cont is not null ");

        for (Object o : q.list()) {
            Msg m = (Msg) o;
            System.out.println(m.getCont());
        }

        session.getTransaction().commit();
    }


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
