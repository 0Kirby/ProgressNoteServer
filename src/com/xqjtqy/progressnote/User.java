package com.xqjtqy.progressnote;

public class User {

	private int id;// id
	private String username;// �û���
	private String password;// ����
	boolean isValid;// �˺��Ƿ���Ч

	public User(int id, String username, String password, boolean isValid) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.isValid = isValid;
	}

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
