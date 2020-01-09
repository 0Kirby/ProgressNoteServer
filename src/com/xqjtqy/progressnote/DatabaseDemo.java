package com.xqjtqy.progressnote;

import java.sql.*;

public class DatabaseDemo {

	// MySQL 8.0 ���ϰ汾 - JDBC �����������ݿ� URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static String DB_URL = "jdbc:mysql://��������������������ĵ�ַ:3306/progress_note?serverTimezone=CST";

	// ���ݿ���û���������
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
			// ע�� JDBC ����
			Class.forName(JDBC_DRIVER);

			// ������
			System.out.println("�������ݿ�...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// ִ�в�ѯ
			System.out.println("ʵ����Statement����...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM user";
			ResultSet rs = stmt.executeQuery(sql);

			// չ����������ݿ�
			while (rs.next()) {
				// ͨ���ֶμ���
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setValid(rs.getBoolean("isValid"));

				// �������
				System.out.print("ID: " + user.getId());
				System.out.print(", �û���: " + user.getUsername());
				System.out.print(", ����: " + user.getPassword());
				System.out.print(", �Ƿ���Ч: " + user.isValid());
				System.out.print("\n");
			}
			// ��ɺ�ر�
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// ���� JDBC ����
			se.printStackTrace();
		} catch (Exception e) {
			// ���� Class.forName ����
			e.printStackTrace();
		} finally {
			// �ر���Դ
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // ʲô������
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
