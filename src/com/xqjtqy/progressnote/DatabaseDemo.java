package com.xqjtqy.progressnote;

import java.sql.*;

public class DatabaseDemo {

	// MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static String DB_URL = "jdbc:mysql://请您在这里输入服务器的地址:3306/progress_note?serverTimezone=CST";

	// 数据库的用户名与密码
	static String USER = "user";
	static String PASS = "mypassword";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = new User();
		Decrypt decrypt = new Decrypt();
		Connection conn = null;
		Statement stmt = null;
		DB_URL = decrypt.OperateURL(DB_URL);
		USER = decrypt.OperateUser(USER);
		PASS = decrypt.OperatePass(PASS);
		try {
			// 注册 JDBC 驱动
			Class.forName(JDBC_DRIVER);

			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// 执行查询
			System.out.println("实例化Statement对象...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM user";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setValid(rs.getBoolean("isValid"));

				// 输出数据
				System.out.print("ID: " + user.getId());
				System.out.print(", 用户名: " + user.getUsername());
				System.out.print(", 密码: " + user.getPassword());
				System.out.print(", 是否有效: " + user.isValid());
				System.out.print("\n");
			}
			// 完成后关闭
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // 什么都不做
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
	}
}
