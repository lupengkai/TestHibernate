import com.fasterxml.classmate.AnnotationConfiguration;
import com.hibernate.model.Student;
import com.hibernate.model.Teacher;
import com.hibernate.model.Title;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

/**
 * Created by tage on 3/18/16.
 */
public class HibernateCoreAPITest {
    private static SessionFactory sf = null;

    @BeforeClass
    public static void beforeClass() {
        //sf = new Configuration().configure("hibernate.xml").buildSessionFactory();
        sf = new Configuration().configure().buildSessionFactory();
    }

    @Test
    public void testStudentSave() {

        Student s = new Student();
        s.setAge(1);


        //Session session = sf.openSession();//永远创建新的session 需要close
        Session session = sf.getCurrentSession();//上下文中有session就用，没有创建新的 不用close

//openSession 之后getCurrentSession 不是同一个session 两者是对同一接口的不同实现


        session.beginTransaction();
        session.save(s);
        session.getTransaction().commit();
        //session.close();

    }

    @Test
    public void testTeacherSave() {
        Teacher t = new Teacher();
        t.setName("233");
        t.setTitle(Title.A);
        t.setAge(1);
        t.setBirthDate(new Date());

        Session session = sf.openSession();
        session.save(t);

//三种状态 transient 内存中有没id， persistent缓存和数据库中有 detached内存有，缓存没有
        //commit 之后 session被关闭 不会再取到同一session
        //session.close();

    }


    @Test
    public void testDelete() {
        Teacher t = new Teacher();
        t.setName("233");
        t.setTitle(Title.A);
        t.setAge(1);
        t.setBirthDate(new Date());

        Teacher t1 = new Teacher();
        t1.setName("23");
        t1.setTitle(Title.A);
        t1.setAge(1);
        t1.setBirthDate(new Date());


        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();


        session.beginTransaction();
        session.save(t1);
        session.getTransaction().commit();

        System.out.println(t1.getId());


        session.beginTransaction();
        session.delete(t1);
        session.getTransaction().commit();


        //persistent detached下可以删
    }


    @Test
    public void testLoad() {
        Session session = sf.openSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.load(Teacher.class, 1);//生成代理对象，真正用到对象时才会产生sql语句
        //System.out.println(t.getId());
        System.out.println(t.getClass());
        session.getTransaction().commit();
    }

    @Test
    public void testGet() {
        Session session = sf.openSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.get(Teacher.class, 1);
        System.out.println(t.getName());
        session.getTransaction().commit();
        System.out.println(t.getName());
    }

    @Test
    public void testUpdate1() {


        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.get(Teacher.class, 1);
        session.getTransaction().commit();
        //detached

        t.setName("hahahah");
        session = sf.getCurrentSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
    }


    @Test
    public void testUpdate2() {

        Teacher t = new Teacher();
        t.setId(1);
        t.setName("233");
        t.setTitle(Title.A);
        t.setAge(1);
        t.setBirthDate(new Date());


        t.setName("hahahah");


        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.update(t);//如果自己设了id， 数据库中有对应的就不会报错
        session.getTransaction().commit();
    }


    @Test
    public void testUpdate4() {
//p状态 只更新不同字段


        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.get(Teacher.class, 1);
        t.setName("hahawwwhah");//改了不一定，不同才会更新。更新所有字段
        session.getTransaction().commit();
    }

    @Test
    public void testUpdate5() {
//p状态 只更新不同字段


        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Student s = (Student) session.get(Student.class, 1);
        s.setAge(20);//改了不一定，和缓存不同才会更新。更新改变字段  p状态
        session.getTransaction().commit();

        s.setAge(30);
        session = sf.getCurrentSession();
        session.beginTransaction();
        session.update(s);//t状态 更新所有状态 没有缓存不能比较
        session.getTransaction().commit();

    }

    @Test
    public void testUpdate6() {


        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Student s = (Student) session.get(Student.class, 1);
        s.setAge(40);
        session.getTransaction().commit();

        s.setAge(60);
        session = sf.getCurrentSession();
        session.beginTransaction();
        session.merge(s);//merge 先load 再比较 只更新不同的   annotation的不行，只能xml配置
        session.getTransaction().commit();

    }

    @Test
    public void testUpdate7() {


        Session session = sf.getCurrentSession();

        session.beginTransaction();
        Query q = session.createQuery("update Student s  set s.age = 50 where s.id =1");
        q.executeUpdate();
        session.getTransaction().commit();

    }


    @Test
    public void testClear() {


        Session session = sf.openSession();
        session.beginTransaction();
        Teacher t = (Teacher) session.load(Teacher.class, 1);//生成代理对象，真正用到对象时才会产生sql语句
        System.out.println(t.getName());

        session.clear();//清除缓存 load get 都是 先查缓存 没有这句只查一次

        Teacher t2 = (Teacher) session.load(Teacher.class, 1);//生成代理对象，真正用到对象时才会产生sql语句
        System.out.println(t2.getName());

        session.getTransaction().commit();

    }

    @Test
    public void testFlush() {


        Session session = sf.openSession();
        // session.beginTransaction(FlushMode.);
        Teacher t = (Teacher) session.load(Teacher.class, 1);//生成代理对象，真正用到对象时才会产生sql语句
        t.setName("gggg");

        session.flush();//强制同步缓存和数据库中的内容 没有这句只更新一次    如果clear 就变成t状态 不会update 而是应该手动写save方法

        t.setName("ggggddddd");


        session.getTransaction().commit();

    }

    @Test
    public void testSchemaExport() {

    }









    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
