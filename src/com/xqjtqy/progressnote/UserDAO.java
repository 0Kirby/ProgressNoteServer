package com.xqjtqy.progressnote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {// �û����ݴ����ڲ��߼�

	public static User queryUser(String username) {// ��ѯ�û�
		Connection connection = DatabaseManager.getConnection();// �����ݿ⽨������
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("select * from progress_note.user where username=?");// SQL���
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);// ����һ��?�滻Ϊ�û���
			resultSet = preparedStatement.executeQuery();// ִ�в�ѯ
			User user = new User();
			if (resultSet.next()) {// �������ݿ�����ݸ�User����ֵ
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setValid(resultSet.getBoolean("isValid"));
				if (resultSet.getBoolean("isValid") == true)// ����˺���Ч���������ݿ��е�lastUse�ֶ�
				{
					sqlStatement = new StringBuilder();
					sqlStatement.append("update progress_note.user SET lastUse=CURRENT_TIMESTAMP where id="
							+ resultSet.getInt("id"));// SQL���
					try {
						preparedStatement2 = connection.prepareStatement(sqlStatement.toString());
						preparedStatement2.executeUpdate();// ִ�и���
					} catch (SQLException ex) {
						ex.printStackTrace();
					} finally {
						DatabaseManager.closeAll(connection, preparedStatement2, null);// �ر�����
					}
				}
				return user;
			} else
				return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, resultSet);// �ر�����
		}
	}

	public static int registerUser(String username, String password, String language, String version, String display,
			String model, String brand) {// ע���û�
		Connection connection = DatabaseManager.getConnection();// �����ݿ⽨������
		PreparedStatement preparedStatement = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append(
				"insert into progress_note.user (username,password,language,version,display,model,brand,isValid) values (?,?,?,?,?,?,?,1)");// SQL���
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);// ����һ��?�滻Ϊ�û���
			preparedStatement.setString(2, password);// ���ڶ���?�滻Ϊ����
			preparedStatement.setString(3, language);// ��������?�滻Ϊ����
			preparedStatement.setString(4, version);// �����ĸ�?�滻Ϊ�汾
			preparedStatement.setString(5, display);// �������?�滻Ϊ��ʾ
			preparedStatement.setString(6, model);// ��������?�滻Ϊ�ͺ�
			preparedStatement.setString(7, brand);// �����߸�?�滻ΪƷ��
			preparedStatement.executeUpdate();// ִ�и���
			User user = queryUser(username);
			return user.getId();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, null);// �ر�����
		}
		return -1;
	}

	public static ArrayList<Note> fetchServer(int userId) {// �ӷ�������ȡ�ʼ�
		Connection connection = DatabaseManager.getConnection();// �����ݿ⽨������
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet = null;
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("select * from progress_note.note where userId=?");// SQL���
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setInt(1, userId);// ����һ��?�滻Ϊ�û�ID
			resultSet = preparedStatement.executeQuery();// ִ�в�ѯ
			ArrayList<Note> noteList = new ArrayList<>();
			while (resultSet.next()) {// �������ݿ�����ݸ�Note����ֵ
				Note note = new Note();
				note.setNoteId(resultSet.getInt("noteId"));
				note.setTitle(resultSet.getString("title"));
				note.setTime(resultSet.getTimestamp("time").getTime());
				note.setContent(resultSet.getString("content"));
				noteList.add(note);
			}
			sqlStatement = new StringBuilder();// �������ݿ��е�lastSync�ֶ�
			sqlStatement.append(
					"update progress_note.user SET lastSync=CURRENT_TIMESTAMP where id=" + userId);// SQL���
			try {
				preparedStatement2 = connection.prepareStatement(sqlStatement.toString());
				preparedStatement2.executeUpdate();// ִ�и���
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				DatabaseManager.closeAll(connection, preparedStatement2, null);// �ر�����
			}
			return noteList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatabaseManager.closeAll(connection, preparedStatement, resultSet);// �ر�����
		}
		return null;
	}

}