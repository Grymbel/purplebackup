package userManagement.model;

public class UserAll{
	User user;
	Login login;
	Student student;
	Teacher teacher;

	public UserAll(User user, Login login, Student student) {
		this.user = user;
		this.login = login;
		this.student = student;
	}

	public UserAll(User user, Login login, Teacher teacher) {
		this.user = user;
		this.login = login;
		this.teacher = teacher;
	}
	
	public UserAll(User user, Login login, Student student, Teacher teacher) {
		this.user = user;
		this.login = login;
		this.student = student;
		this.teacher = teacher;
	}

	public UserAll() {
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public void printInfo() {
		System.out.println("UserID: " + user.getUserID() + ", Username: " + login.getUsername() + ", Password: " + login.getPassword() + ", Name: " + user.getName() + ", Gender: " + user.getGender() + ", Date Of Birth: " + user.getdOB() + ", Contact Number: " + user.getContactNo() + ", Email: " + user.getEmail() + ", Class: " + user.getSchoolClass() + ", Address: " + user.getAddress() + ", NRIC: " + student.getnRIC() + ", CCA: " + student.getcCA() + ", TeacherID: " + teacher.getTeacherID() + ", Department: " + teacher.getTeacherID());
	}
}
