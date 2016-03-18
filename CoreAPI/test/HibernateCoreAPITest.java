import com.hibernate.model.Student;
import com.hibernate.model.Teacher;
import com.hibernate.model.Title;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
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




        session.beginTransaction();
        session.save(s);
        session.getTransaction().commit();
        session.close();

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


        //commit 之后 session被关闭 不会再取到同一session
        //session.close();

    }

    @AfterClass
    public static void afterClass() {
        sf.close();
    }
}
