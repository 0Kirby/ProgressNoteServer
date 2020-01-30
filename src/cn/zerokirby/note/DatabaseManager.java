package cn.zerokirby.note;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class DatabaseManager extends HttpServlet {//���������ݿ������
	private static final long serialVersionUID = 1L;
	ServletConfig config;
	private static String db_url = "jdbc:mysql://��������������������ĵ�ַ:3306/progress_note?autoReconnect=true&serverTimezone=Asia/Shanghai";
	//private static String db_url = "jdbc:mysql://localhost:3306/progress_note?autoReconnect=true&serverTimezone=Asia/Shanghai";
	private static String db_username = "user";
	private static String db_password = "mypassword";
	private static Connection connection;
	private static boolean flag = false;

	@Override
	public void init(ServletConfig config) throws ServletException {//��ʼ��
		// TODO Auto-generated method stub
		super.init(config);
		this.config = config;
	}

	public static void decrypt()
	{
		if (!flag) {
			Decrypt decrypt = new Decrypt();
			db_url = decrypt.OperateURL(db_url);
			db_username = decrypt.OperateUser(db_username);
			db_password = decrypt.OperatePass(db_password);
			flag = true;
		}
	}
	
	public static Connection getConnection() {//�����ݿ⽨������
		decrypt();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();//���ݿ�������
			connection = DriverManager.getConnection(db_url, db_username, db_password);//���ν�������
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
			ex.printStackTrace();
		}
		return connection;
	}

	public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {//�ر�����
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
}
