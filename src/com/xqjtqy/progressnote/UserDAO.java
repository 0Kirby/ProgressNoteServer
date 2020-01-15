package com.xqjtqy.progressnote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {//用户数据处理内部逻辑

	public static User queryUser(String username) {//查询用户
		Connection connection = DatabaseManager.getConnection();//和数据库建立连接
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("select * from progress_note.user where username=?");//SQL语句
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);//将第一个?替换为用户名
			resultSet = preparedStatement.executeQuery();//执行查询
			User user = new User();
			if (resultSet.next()) {//根据数据库的内容给User对象赋值
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setValid(resultSet.getBoolean("isValid"));
				return user;
			} else
				return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, resultSet);//关闭连接
		}
	}

	public static int registerUser(String username,String password) {//注册用户
		Connection connection = DatabaseManager.getConnection();//和数据库建立连接
		PreparedStatement preparedStatement = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("insert into progress_note.user (username,password,isValid) values (?,?,1)");//SQL语句
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);//将第一个?替换为用户名
			preparedStatement.setString(2, password);//将第二个?替换为密码
			preparedStatement.executeUpdate();//执行更新
			User user = queryUser(username);
			return user.getId();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, null);//关闭连接
		}
		return -1;
	}
}