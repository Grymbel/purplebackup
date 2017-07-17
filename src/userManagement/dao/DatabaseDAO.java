package userManagement.dao;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import userManagement.model.Login;
import userManagement.model.Student;
import userManagement.model.Teacher;
import userManagement.model.User;
import userManagement.model.UserAll;

public class DatabaseDAO {
	final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    final String DB_URL="jdbc:mysql://localhost:3306/nspj_database";
    final String USER = "root";
    final String PASS = "SassyPenguin@123";
    
    final String DB_URL2="jdbc:mysql://119.74.135.44:3306/nspj_database";
    final String USER_S = "Student";
    final String PASS_S = "StudentPassword_101";
    
    final String USER_T = "Teacher";
    final String PASS_T = "TeacherPassword_102";
    Connection conn;
    Statement stmt;
    PreparedStatement ppstmt;
    
	public DatabaseDAO(int permission) throws ClassNotFoundException, SQLException{
		 Class.forName(JDBC_DRIVER);
		 switch(permission){
		 	case 0:
		 		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		 		break;
		 	case 1:
		 		conn = DriverManager.getConnection(DB_URL2, USER_S, PASS_S);
		 		break;
		 	case 2:
		 		conn = DriverManager.getConnection(DB_URL2, USER_T, PASS_T);
		 		break;
		 }
	     
	     stmt = conn.createStatement();
	}

	private ResultSet getDatabaseData(String sql) throws SQLException{
		return stmt.executeQuery(sql);
	}
	
	public ResultSet getSearchDatabaseData(String sql, String value) throws SQLException{
		ppstmt = conn.prepareStatement(sql);
		ppstmt.setString(1, "%" + value + "%");
		return ppstmt.executeQuery();
	}
	
	public ResultSet getDatabaseData(String sql, String value) throws SQLException{
		ppstmt = conn.prepareStatement(sql);
		ppstmt.setString(1, value);
		return ppstmt.executeQuery();
	}
	
	public ArrayList<UserAll> getDatabaseUserAll() throws SQLException{
		ResultSet rs = getDatabaseData("SELECT User.UserID, Login.Username, Login.Password, Login.Salt, User.Name, User.Gender, User.DOB, User.ContactNo, User.Email, User.Class, User.Address, Student.NRIC, Student.CCA, Teacher.TeacherID, Teacher.Department FROM User LEFT OUTER JOIN Login ON (User.UserID = Login.UserID) LEFT OUTER JOIN Student ON (User.UserID = Student.UserID) LEFT OUTER JOIN Teacher ON (User.UserID = Teacher.UserID) ORDER BY UserID;");
		ArrayList<UserAll> userAllArray = new ArrayList<UserAll>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			User user = new User(userID, name, gender, dOB, contactNo, email, schoolClass, address);
			
			String username = rs.getString("Username");
			String password = rs.getString("Password");
			String salt = rs.getString("Salt");
			Login login = new Login(username, password, salt, user);
			
			String nRIC = rs.getString("NRIC");
			String cCA = rs.getString("CCA");
			Student student = new Student(nRIC, cCA, user);
			
			int teacherID = rs.getInt("TeacherID");
			String department = rs.getString("Department");
			Teacher teacher = new Teacher(teacherID, department, user);
			userAllArray.add(new UserAll(user, login, student, teacher));
		}
		
		rs.close();
		return userAllArray;
	}
	
	public ArrayList<User> getDatabaseUser() throws SQLException{
		ResultSet rs = getDatabaseData("SELECT * FROM User;");
		ArrayList<User> userArray = new ArrayList<User>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			userArray.add(new User(userID,name,gender,dOB,contactNo,email,schoolClass,address));
		}
		
		rs.close();
		return userArray;
	}
	
	public ArrayList<Login> getDatabaseLogin() throws SQLException{
		ResultSet rs = getDatabaseData("SELECT * FROM Login INNER JOIN User ON Login.userID = User.userID;");
		ArrayList<Login> loginArray = new ArrayList<Login>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			
			User user = new User(userID,name,gender,dOB,contactNo,email,schoolClass,address);
			String username = rs.getString("Username");
			String password = rs.getString("Password");
			String salt = rs.getString("Salt");
			Login login = new Login(username, password, salt, user);
			loginArray.add(login);
		}
		
		rs.close();
		return loginArray;
	}
	
	public ArrayList<Student> getDatabaseStudent() throws SQLException{
		ResultSet rs = getDatabaseData("SELECT * FROM Student INNER JOIN User ON Login.userID = User.userID;");
		ArrayList<Student> studentArray = new ArrayList<Student>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			User user = new User(userID,name,gender,dOB,contactNo,email,schoolClass,address);
			
			String nRIC = rs.getString("NRIC");
			String cCA = rs.getString("CCA");
			Student student = new Student(nRIC, cCA, user);
			
			studentArray.add(student);
		}
		
		rs.close();
		return studentArray;
	}
	
	public ArrayList<Teacher> getDatabaseTeacher() throws SQLException{
		ResultSet rs = getDatabaseData("SELECT * FROM Teacher INNER JOIN User ON Login.userID = User.userID;");
		ArrayList<Teacher> teacherArray = new ArrayList<Teacher>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			
			User user = new User(userID,name,gender,dOB,contactNo,email,schoolClass,address);
			int teacherID = rs.getInt("TeacherID");
			String department = rs.getString("Department");
			Teacher teacher = new Teacher(teacherID, department, user);
			teacherArray.add(teacher);
		}
		
		rs.close();
		return teacherArray;
	}
	
	public ArrayList<userManagement.model.File> getDatabaseFile() throws SQLException{
		ResultSet rs = getDatabaseData("SELECT * FROM File LEFT INNER JOIN User ON User.userID = File.userID;");
		ArrayList<userManagement.model.File> fileArray = new ArrayList<userManagement.model.File>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			
			User user = new User(userID, name, gender, dOB, contactNo, email, schoolClass, address);
			
			int fileID = rs.getInt("userID");
			String fileName = rs.getString("Name");;
			int fileSize = rs.getInt("userID");
			Blob dataBlob = rs.getBlob("Data");
			byte [] fileData = dataBlob.getBytes(0, (int)dataBlob.length());
			userManagement.model.File file = new userManagement.model.File(fileID, fileName, fileSize, fileData, user);
			fileArray.add(file);
		}
		
		rs.close();
		return fileArray;
	}
	
	public ArrayList<UserAll> convertResultSetToArrayList(ResultSet rs) throws SQLException{
		ArrayList<UserAll> userAllArray = new ArrayList<UserAll>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			User user = new User(userID, name, gender, dOB, contactNo, email, schoolClass, address);
			
			String username = rs.getString("Username");
			String password = rs.getString("Password");
			String salt = rs.getString("Salt");
			Login login = new Login(username, password, salt, user);
			
			String nRIC = rs.getString("NRIC");
			String cCA = rs.getString("CCA");
			Student student = new Student(nRIC, cCA, user);
			
			int teacherID = rs.getInt("TeacherID");
			String department = rs.getString("Department");
			Teacher teacher = new Teacher(teacherID, department, user);
			
			userAllArray.add(new UserAll(user, login, student, teacher));
		}
		
		rs.close();
		return userAllArray;
	}
	
	//Working but not done
	public void updateDatabaseData(String sql) throws SQLException{
		int count = stmt.executeUpdate(sql);
        if(count ==0){
        	System.out.println("!!! Update failed !!!\n");
        }else{
    	    System.out.println("!!! Update successful !!!\n");
        }
	}
	
	public void updateDatabaseDataFileUpload(String sql, String fileName, long fileSize, InputStream data) throws SQLException{
		ppstmt = conn.prepareStatement(sql);
		ppstmt.setString(1, fileName);
		ppstmt.setLong(2, fileSize);
		ppstmt.setBlob(3, data);
		int count = ppstmt.executeUpdate();
        if(count ==0){
        	System.out.println("!!! Update failed !!!\n");
        }else{
    	    System.out.println("!!! Update successful !!!\n");
        }
	}
	
	public void close() throws SQLException{
		conn.close();
		stmt.close();
	}
	
	/*
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		DatabaseDAO dao = new DatabaseDAO();
		ArrayList<UserAll> userAllArray= dao.getDatabaseUserAll();
		
		for(UserAll u:userAllArray){
			u.printInfo();
		}
		
		dao.close();
	}
	*/
}
