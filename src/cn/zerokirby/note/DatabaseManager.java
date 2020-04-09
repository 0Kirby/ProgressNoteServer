package cn.zerokirby.note;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DatabaseManager extends HttpServlet {//负责与数据库的连接
    private static final long serialVersionUID = 1L;
    //private static String db_url = "jdbc:mysql://请您在这里输入服务器的地址:3306/progress_note?autoReconnect=true&serverTimezone=Asia/Shanghai";
    private static String db_url = "jdbc:mysql://localhost:3306/progress_note?autoReconnect=true&serverTimezone=Asia/Shanghai";
    private static String db_username = "user";
    private static String db_password = "mypassword";
    private static Connection connection;
    private static boolean flag = false;
    ServletConfig config;

    public static Connection getConnection() {//与数据库建立连接
        decrypt();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();//数据库驱动类
            connection = DriverManager.getConnection(db_url, db_username, db_password);//传参建立连接
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException
                | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public static void decrypt() {
        if (!flag) {
            Decrypt decrypt = new Decrypt();
            //db_url = decrypt.OperateURL(db_url);
            db_username = decrypt.OperateUser(db_username);
            db_password = decrypt.OperatePass(db_password);
            flag = true;
        }
    }

    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {//关闭连接
        try {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {//初始化
        // TODO Auto-generated method stub
        super.init(config);
        this.config = config;
    }
}
