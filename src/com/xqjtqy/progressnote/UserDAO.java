package com.xqjtqy.progressnote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {//�û����ݴ����ڲ��߼�

	public static User queryUser(String username) {//��ѯ�û�
		Connection connection = DatabaseManager.getConnection();//�����ݿ⽨������
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("select * from progress_note.user where username=?");//SQL���
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);//����һ��?�滻Ϊ�û���
			resultSet = preparedStatement.executeQuery();//ִ�в�ѯ
			User user = new User();
			if (resultSet.next()) {//�������ݿ�����ݸ�User����ֵ
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
			DatabaseManager.closeAll(connection, preparedStatement, resultSet);//�ر�����
		}
	}

	public static int registerUser(String username,String password) {//ע���û�
		Connection connection = DatabaseManager.getConnection();//�����ݿ⽨������
		PreparedStatement preparedStatement = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("insert into progress_note.user (username,password,isValid) values (?,?,1)");//SQL���
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);//����һ��?�滻Ϊ�û���
			preparedStatement.setString(2, password);//���ڶ���?�滻Ϊ����
			preparedStatement.executeUpdate();//ִ�и���
			User user = queryUser(username);
			return user.getId();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, null);//�ر�����
		}
		return -1;
	}
}