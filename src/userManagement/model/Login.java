package userManagement.model;

public class Login {
	String username;
	String password;
	String salt;
	int userID;
	
	public Login(String username, String password, String salt, User user) {
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.userID = user.getUserID();
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
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
