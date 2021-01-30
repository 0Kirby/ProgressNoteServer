package cn.zerokirby.note;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import java.io.Serial;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DatabaseManager extends HttpServlet {//负责与数据库的连接
    @Serial
    private static final long serialVersionUID = 1L;
    //private static final String db_url = "jdbc:mysql://zerokirby.cn:3306/progress_note?autoReconnect=true&serverTimezone=Asia/Shanghai";
    private static final String db_url = "jdbc:mysql://localhost:3306/progress_note?autoReconnect=true&serverTimezone=Asia/Shanghai";
    private static String db_username = "user";
    private static String db_password = "myPassword";
    private static Connection connection;
    private static boolean flag = false;
    ServletConfig config;

    public static Connection getConnection() {//与数据库建立连接
        //decrypt();
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
        super.init(config);
        this.config = config;
    }
}
