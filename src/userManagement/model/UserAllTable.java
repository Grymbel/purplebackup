package userManagement.model;

import javafx.beans.property.SimpleStringProperty;

public class UserAllTable{
	public SimpleStringProperty userID;
	public SimpleStringProperty username;
	public SimpleStringProperty password;
	public SimpleStringProperty salt;
	public SimpleStringProperty name;
	public SimpleStringProperty gender;
	public SimpleStringProperty dOB;
	public SimpleStringProperty contactNo;
	public SimpleStringProperty email;
	public SimpleStringProperty schoolClass;
	public SimpleStringProperty address;
	public SimpleStringProperty nRIC;
	public SimpleStringProperty cCA;
	public SimpleStringProperty teacherID;
	public SimpleStringProperty department;
    
	public UserAllTable(UserAll userAll) {
		this.userID 	= new SimpleStringProperty(String.valueOf(userAll.getUser().getUserID()));
		this.username	= new SimpleStringProperty(userAll.getLogin().getUsername());
		this.password 	= new SimpleStringProperty(userAll.getLogin().getPassword());
		this.salt 	= new SimpleStringProperty(userAll.getLogin().getSalt());
		this.name 		= new SimpleStringProperty(userAll.getUser().getName());
		this.gender 	= new SimpleStringProperty(userAll.getUser().getGender());
		this.dOB 		= new SimpleStringProperty(userAll.getUser().getdOB());
		this.contactNo 	= new SimpleStringProperty(userAll.getUser().getContactNo());
		this.email		= new SimpleStringProperty(userAll.getUser().getEmail());
		this.schoolClass= new SimpleStringProperty(userAll.getUser().getSchoolClass());
		this.address 	= new SimpleStringProperty(userAll.getUser().getAddress());
		this.nRIC 		= new SimpleStringProperty(userAll.getStudent().getnRIC());
		this.cCA 		= new SimpleStringProperty(userAll.getStudent().getcCA());
		this.teacherID 	= new SimpleStringProperty(String.valueOf(userAll.getTeacher().getTeacherID()));
		this.department = new SimpleStringProperty(userAll.getTeacher().getDepartment());
	}
	
	public UserAllTable(int userID, String username, String password,
			String salt, String name, String gender, String dOB,
			String contactNo, String email, String schoolClass,
			String address, String nRIC, String cCA,
			int teacherID, String department) {
		this.userID 	= new SimpleStringProperty(String.valueOf(userID));
		this.username	= new SimpleStringProperty(username);
		this.password 	= new SimpleStringProperty(password);
		this.salt 	= new SimpleStringProperty(salt);
		this.name 		= new SimpleStringProperty(name);
		this.gender 	= new SimpleStringProperty(gender);
		this.dOB 		= new SimpleStringProperty(dOB);
		this.contactNo 	= new SimpleStringProperty(contactNo);
		this.email		= new SimpleStringProperty(email);
		this.schoolClass= new SimpleStringProperty(schoolClass);
		this.address 	= new SimpleStringProperty(address);
		this.nRIC 		= new SimpleStringProperty(nRIC);
		this.cCA 		= new SimpleStringProperty(cCA);
		this.teacherID 	= new SimpleStringProperty(String.valueOf(teacherID));
		this.department = new SimpleStringProperty(department);
	}

	public SimpleStringProperty getUserID() {
		return userID;
	}
	public void setUsername(SimpleStringProperty username) {
		this.username = username;
	}
	public void setPassword(SimpleStringProperty password) {
		this.password = password;
	}
	public void setSalt(SimpleStringProperty salt) {
		this.salt = salt;
	}
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}
	public void setGender(SimpleStringProperty gender) {
		this.gender = gender;
	}
	public void setdOB(SimpleStringProperty dOB) {
		this.dOB = dOB;
	}
	public void setContactNo(SimpleStringProperty contactNo) {
		this.contactNo = contactNo;
	}
	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}
	public void setSchoolClass(SimpleStringProperty schoolClass) {
		this.schoolClass = schoolClass;
	}
	public void setAddress(SimpleStringProperty address) {
		this.address = address;
	}
	public void setnRIC(SimpleStringProperty nRIC) {
		this.nRIC = nRIC;
	}
	public void setcCA(SimpleStringProperty cCA) {
		this.cCA = cCA;
	}
	public void setTeacherID(SimpleStringProperty teacherID) {
		this.teacherID = teacherID;
	}
	public void setDepartment(SimpleStringProperty department) {
		this.department = department;
	}
	public SimpleStringProperty getUsername() {
		return username;
	}
	public SimpleStringProperty getPassword() {
		return password;
	}
	public SimpleStringProperty getSalt() {
		return salt;
	}
	public SimpleStringProperty getName() {
		return name;
	}
	public SimpleStringProperty getGender() {
		return gender;
	}
	public SimpleStringProperty getdOB() {
		return dOB;
	}
	public SimpleStringProperty getContactNo() {
		return contactNo;
	}
	public SimpleStringProperty getEmail() {
		return email;
	}
	public SimpleStringProperty getSchoolClass() {
		return schoolClass;
	}
	public SimpleStringProperty getAddress() {
		return address;
	}
	public SimpleStringProperty getnRIC() {
		return nRIC;
	}
	public SimpleStringProperty getcCA() {
		return cCA;
	}
	public SimpleStringProperty getTeacherID() {
		return teacherID;
	}
	public SimpleStringProperty getDepartment() {
		return department;
	}
	/*
    private SimpleStringProperty checkNull(SimpleStringProperty line){
    	if (line == null) {
    		line = new SimpleStringProperty(this, "Wolf");
          }
    	return line;
    }
    */
}
