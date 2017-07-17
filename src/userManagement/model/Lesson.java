package userManagement.model;

public class Lesson {
	double attendance = 0;
	String subject;
	double grade = 0;
	String nRIC;
	
	public Lesson(double attendance, String subject, double grade, Student student) {
		super();
		this.attendance = attendance;
		this.subject = subject;
		this.grade = grade;
		this.nRIC = student.getnRIC();
	}

	public double getAttendance() {
		return attendance;
	}
	public void setAttendance(double attendance) {
		this.attendance = attendance;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String getnRIC() {
		return nRIC;
	}
	public void setnRIC(String nRIC) {
		this.nRIC = nRIC;
	}
	
}
