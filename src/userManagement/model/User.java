package userManagement.model;

public class User {
	int userID;
	String name;
	String gender;
	String dOB;
	String contactNo;
	String email;
	String schoolClass;
	String address;
	
	public User(String name, String gender, String dOB, String contactNo, String email, String schoolClass,
			String address) {
		this.name = name;
		this.gender = gender;
		this.dOB = dOB;
		this.contactNo = contactNo;
		this.email = email;
		this.schoolClass = schoolClass;
		this.address = address;
	}

	public User(int userID, String name, String gender, String dOB, String contactNo, String email, String schoolClass, String address) {
		this.userID = userID;
		this.name = name;
		this.gender = gender;
		this.dOB = dOB;
		this.contactNo = contactNo;
		this.email = email;
		this.schoolClass = schoolClass;
		this.address = address;
	}

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getdOB() {
		return dOB;
	}
	public void setdOB(String dOB) {
		this.dOB = dOB;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSchoolClass() {
		return schoolClass;
	}
	public void setSchoolClass(String schoolClass) {
		this.schoolClass = schoolClass;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void printInfo(){
		System.out.println("UserID: " + userID + ", Name: " + name + ", Gender: " + gender + ", Date Of Birth: " + dOB + ", Contact Number: " + contactNo + ", Email: " + email + ", Class: " + schoolClass + ", Address: " + address);
	}
	
}
