import com.hibernate.model.Teacher;
import com.hibernate.model.Title;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;

/**
 * Created by tage on 3/16/16.
 */
public class TeacherTest {
    public static void main(String[] args) {
        Teacher t = new Teacher();
        t.setTitle(Title.A);
        t.setAge(1);
        t.setBirthDate(new Date());

        Configuration cfg = new Configuration() ;
        SessionFactory sf = cfg.configure().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
        sf.close();
    }
}
