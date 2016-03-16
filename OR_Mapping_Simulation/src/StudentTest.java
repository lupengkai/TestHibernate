import com.hibernate.model.Student;


/**
 * Created by tage on 3/16/16.
 */
public class StudentTest {
    public static void main(String[] args) throws Exception{
        Student s = new Student();
        s.setId(1);
        s.setName("s1");
        s.setAge(1);

        Session session = new Session();

        session.save(s);
    }
}
