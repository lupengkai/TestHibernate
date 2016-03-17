import com.hibernate.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by tage on 3/16/16.
 */
public class StudentTest {
    public static void main(String[] args) {
        Student s = new Student();
        s.setId(1);
        s.setName("s1");
        s.setAge(1);

        Configuration cfg = new Configuration();
        SessionFactory sf = cfg.configure().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(s);
        session.getTransaction().commit();
        session.close();
        sf.close();
    }
}
