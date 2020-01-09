package com.xqjtqy.progressnote;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class DatabaseManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletConfig config;
	private static String db_url = "jdbc:mysql://请您在这里输入服务器的地址:3306/progress_note?autoReconnect=true&serverTimezone=CST";
	//private static String db_url = "jdbc:mysql://localhost:3306/progress_note?autoReconnect=true&serverTimezone=CST";
	private static String db_username = "user";
	private static String db_password = "mypassword";
	private static Connection connection;
	private static boolean flag = false;

	@Override
	public void init(ServletConfig config) throws ServletException {
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
	
	public static Connection getConnection() {
		decrypt();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			connection = DriverManager.getConnection(db_url, db_username, db_password);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
			Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		return connection;
	}

	public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException ex) {
			Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void closeAll(Connection connection, Statement statement) {
		try {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException ex) {
			Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
