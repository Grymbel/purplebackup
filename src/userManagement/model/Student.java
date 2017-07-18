package userManagement.model;

import java.util.ArrayList;

public class Student {
	ArrayList<Lesson> lesson;
	String cCA;
	int userType = 1;
	int userID;
	
	public Student( String cCA, User user) {
		super();
		this.cCA = cCA;
		this.userID = user.getUserID();
	}

	public ArrayList<Lesson> getLesson() {
		return lesson;
	}
	public void setLesson(ArrayList<Lesson> lesson) {
		this.lesson = lesson;
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
