package userManagement.model;

import java.util.ArrayList;

public class Student {
	ArrayList<Lesson> lesson;
	String nRIC;
	String cCA;
	int userType = 1;
	int userID;
	
	public Student(String nRIC, String cCA, User user) {
		super();
		this.nRIC = nRIC;
		this.cCA = cCA;
		this.userID = user.getUserID();
	}

	public ArrayList<Lesson> getLesson() {
		return lesson;
	}
	public void setLesson(ArrayList<Lesson> lesson) {
		this.lesson = lesson;
	}
	public String getnRIC() {
		return nRIC;
	}
	public void setnRIC(String nRIC) {
		this.nRIC = nRIC;
	}
	public String getcCA() {
		return cCA;
	}
	public void setcCA(String cCA) {
		this.cCA = cCA;
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
