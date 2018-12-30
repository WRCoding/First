import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**连接到SQL server
 * @author LB
 * @create 2018-12-24 22:03
 */
public class GetConnection {
    private Connection con=null;
    public Connection GetConnection(){
        String URL="jdbc:sqlserver://localhost:1433;DatabaseName=students";
        String USER="sa";
        String KEY="lb1001101";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con= DriverManager.getConnection(URL, USER, KEY);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return con;
    }
}
