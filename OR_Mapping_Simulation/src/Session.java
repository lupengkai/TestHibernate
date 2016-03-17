import com.hibernate.model.Student;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tage on 3/16/16.
 */
public class Session {

    String tableName = "_Student";

    //columns fields
    Map<String, String> cfs = new HashMap<>();


    String[] methodNames;

    public Session() {
        cfs.put("_id", "id");
        cfs.put("_name", "name");
        cfs.put("_age", "age");

        methodNames = new String[cfs.size()];
    }



    public void save(Student s) throws Exception{




        String sql = createSQL();
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hibernate", "root", "0715");
        PreparedStatement ps = conn.prepareStatement(sql);

        //根据方法名获得方法
        for (int i = 0; i < cfs.size(); i++) {
            Method m = s.getClass().getMethod(methodNames[i]);
            Class r = m.getReturnType();
            //System.out.println(r.getName());


            if (r.getName().equals("java.lang.String")) {

                String returnValue = (String)m.invoke(s);//根据（，，，）元素位置，调用相应的getXXX方法
                ps.setString(i+1, returnValue);
            }
            if (r.getName().equals("int")) {

                int returnValue = (Integer)m.invoke(s);
                ps.setInt(i+1, returnValue);
            }
        }

        ps.execute();
        ps.close();
        conn.close();



    }


    private String createSQL() {
        String str1 = "";

        int index = 0;

        for (String s : cfs.keySet()) {
            //拼出get方法名
            String v = cfs.get(s);
            v = Character.toUpperCase(v.charAt(0)) + v.substring(1,v.length());
            methodNames[index++] = "get" + v;
            str1 += s + ",";
        }
        str1 = str1.substring(0, str1.length()-1);//不包括endIndex

        String str2 = "";
        for(int i = 0; i<cfs.size(); i++) {
            str2 +=" ?,";
        }
        str2 = str2.substring(0, str2.length()-1);//不包括endIndex
         String sql = "insert into " + tableName + " ( " + str1 +" ) " + " values ( " + str2 + " ) ";

        System.out.println(sql);
        return sql;
    }

}
