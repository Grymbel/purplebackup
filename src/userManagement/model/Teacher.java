package userManagement.model;

public class Teacher {
	int teacherID;
	String department;
	int userType = 2;
	int userID;
	
	public Teacher(String department, User user) {
		this.department = department;
		this.userID = user.getUserID();
	}
	
	public Teacher(int teacherID, String department, User user) {
		this.teacherID = teacherID;
		this.department = department;
		this.userID = user.getUserID();
	}
	
	public int getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
