package cn.zerokirby.note;

public class User {

	private int id;// id
	private String username;// 用户名
	private String password;// 密码
	private String language;// 语言
	private String version;// 版本
	private String display;// 显示信息
	private String model;// 型号
	private String brand;// 品牌
	private long registerTime; // 注册时间
	private long syncTime; // 同步时间
	boolean isValid;// 账号是否有效

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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(long registerTime) {
		this.registerTime = registerTime;
	}

	public long getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(long syncTime) {
		this.syncTime = syncTime;
	}
	
}
