package com.hibernate.model;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
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


    @Test
    public void testHQL_20() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Topic t where t.msgs is empty ");

        for (Object o : q.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getId());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_21() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Topic t where t.title like '%5'");//0个或多个

        for (Object o : q.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getTitle());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_22() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Topic t where t.title like '_5'");//1个

        for (Object o : q.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getTitle());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_23() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select lower(t.title), upper(t.title), trim(t.title), concat(t.title, '***'),length(t.title) from Topic t");//EJBQL

        for (Object o : q.list()) {
            Object[] arr = (Object[]) o;
            System.out.println(arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " " + arr[4]);
        }

        session.getTransaction().commit();
    }


    @Test
    public void testHQL_24() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select abs(t.id),sqrt(t.id), mod(t.id, 2) from Topic t");//EJBQL

        for (Object o : q.list()) {
            Object[] arr = (Object[]) o;
            System.out.println(arr[0] + " " + arr[1] + " " + arr[2]);
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_25() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select current_date, current_date, current_timestamp, t.id from Topic t");

        for (Object o : q.list()) {
            Object[] arr = (Object[]) o;
            System.out.println(arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3]);
        }

        session.getTransaction().commit();
    }


    @Test
    public void testHQL_26() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Topic t where t.createDate < :date");
        q.setParameter("date", new Date());

        for (Object o : q.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getCreateDate());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_27() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select t.title, count(*) from Topic t group by t.title");//group by 出现的东西必须在select里面


        for (Object o : q.list()) {
            Object[] arr = (Object[]) o;
            System.out.println(arr[0] + " " + arr[1]);
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_28() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("select t.title, count(*) from Topic t group by t.title having count(*) >= 1");//having 条件只能是组函数


        for (Object o : q.list()) {
            Object[] arr = (Object[]) o;
            System.out.println(arr[0] + " " + arr[1]);
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_29() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Topic t where t.id < (select avg(t.id) from Topic t)");


        for (Object o : q.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getId());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_30() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Topic t where t.id < ALL (select  t.id from Topic t where (mod(t.id, 2)=0))");


        for (Object o : q.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getId());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_31() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("from Topic t where not exists (select m.id from Msg m where m.topic.id=t.id)");//in也可以


        for (Object o : q.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getId());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_32() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Query q = session.createQuery("update Topic t set t.title = upper(t.title)");

        q.executeUpdate();
        q = session.createQuery("FROM Topic");
        for (Object o : q.list()) {
            Topic t = (Topic) o;
            System.out.println(t.getId());
        }

        session.getTransaction().commit();
    }

    @Test
    public void testHQL_33() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();


        Query q = session.getNamedQuery("topic.selectCertainTopic");
        q.setParameter("id", 5);

        Topic t = (Topic) q.uniqueResult();
        System.out.println(t.getId());

        session.getTransaction().commit();
    }


    //Native Query
    @Test
    public void testHQL_34() {
        testSave();
        Session session = sf.getCurrentSession();
        session.beginTransaction();


        SQLQuery q = session.createSQLQuery("SELECT * FROM Category limit 2,4").addEntity(Category.class);

        List<Category> categories = (List<Category>) q.list();
        for (Category c : categories) {
            System.out.println(c.getName());
        }


        session.getTransaction().commit();
    }

    @Test
    public void testHQL_35() {
        //可能未实现
    }


    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
