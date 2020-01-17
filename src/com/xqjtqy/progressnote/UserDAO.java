package com.xqjtqy.progressnote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {// 用户数据处理内部逻辑

	public static User queryUser(String username) {// 查询用户
		Connection connection = DatabaseManager.getConnection();// 和数据库建立连接
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("select * from progress_note.user where username=?");// SQL语句
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);// 将第一个?替换为用户名
			resultSet = preparedStatement.executeQuery();// 执行查询
			User user = new User();
			if (resultSet.next()) {// 根据数据库的内容给User对象赋值
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setValid(resultSet.getBoolean("isValid"));
				if (resultSet.getBoolean("isValid") == true)// 如果账号有效，更新数据库中的lastUse字段
				{
					sqlStatement = new StringBuilder();
					sqlStatement.append("update progress_note.user SET lastUse=CURRENT_TIMESTAMP where id="
							+ resultSet.getInt("id"));// SQL语句
					try {
						preparedStatement2 = connection.prepareStatement(sqlStatement.toString());
						preparedStatement2.executeUpdate();// 执行更新
					} catch (SQLException ex) {
						ex.printStackTrace();
					} finally {
						DatabaseManager.closeAll(connection, preparedStatement2, null);// 关闭连接
					}
				}
				return user;
			} else
				return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, resultSet);// 关闭连接
		}
	}

	public static int registerUser(String username, String password, String language, String version, String display,
			String model, String brand) {// 注册用户
		Connection connection = DatabaseManager.getConnection();// 和数据库建立连接
		PreparedStatement preparedStatement = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append(
				"insert into progress_note.user (username,password,language,version,display,model,brand,isValid) values (?,?,?,?,?,?,?,1)");// SQL语句
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);// 将第一个?替换为用户名
			preparedStatement.setString(2, password);// 将第二个?替换为密码
			preparedStatement.setString(3, language);// 将第三个?替换为语言
			preparedStatement.setString(4, version);// 将第四个?替换为版本
			preparedStatement.setString(5, display);// 将第五个?替换为显示
			preparedStatement.setString(6, model);// 将第六个?替换为型号
			preparedStatement.setString(7, brand);// 将第七个?替换为品牌
			preparedStatement.executeUpdate();// 执行更新
			User user = queryUser(username);
			return user.getId();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, null);// 关闭连接
		}
		return -1;
	}

	public static ArrayList<Note> fetchServer(int userId) {// 从服务器获取笔记
		Connection connection = DatabaseManager.getConnection();// 和数据库建立连接
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("select * from progress_note.note where userId=?");// SQL语句
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setInt(1, userId);// 将第一个?替换为用户ID
			resultSet = preparedStatement.executeQuery();// 执行查询
			ArrayList<Note> noteList = new ArrayList<>();
			while (resultSet.next()) {// 根据数据库的内容给Note对象赋值
				Note note = new Note();
				note.setNoteId(resultSet.getInt("noteId"));
				note.setTitle(resultSet.getString("title"));
				note.setTime(resultSet.getTimestamp("time").getTime());
				note.setContent(resultSet.getString("content"));
				noteList.add(note);
			}
			sqlStatement = new StringBuilder();// 更新数据库中的lastSync字段
			sqlStatement.append(
					"update progress_note.user SET lastSync=CURRENT_TIMESTAMP where id=" + userId);// SQL语句
			try {
				preparedStatement2 = connection.prepareStatement(sqlStatement.toString());
				preparedStatement2.executeUpdate();// 执行更新
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				DatabaseManager.closeAll(connection, preparedStatement2, null);// 关闭连接
			}
			return noteList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, resultSet);// 关闭连接
		}
		return null;
	}

}