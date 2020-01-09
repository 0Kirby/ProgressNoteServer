package com.xqjtqy.progressnote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

	public static User queryUser(String username) {
		Connection connection = DatabaseManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("select * from progress_note.user where username=?");
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			User user = new User();
			if (resultSet.next()) {
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setValid(resultSet.getBoolean("isValid"));
				return user;
			} else
				return null;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, resultSet);
		}
	}

	public static void registerUser(String username,String password) {
		Connection connection = DatabaseManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("insert into progress_note.user (username,password,isValid) values (?,?,1)");
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, resultSet);
		}
	}

}