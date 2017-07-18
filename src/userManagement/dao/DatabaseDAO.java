package userManagement.dao;

import java.io.InputStream;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import userManagement.model.File;
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
		ResultSet rs = getDatabaseData("SELECT User.UserID, Login.Username, Login.Password, Login.Salt, User.NRIC, User.Name, User.Gender, User.DOB, User.ContactNo, User.Email, User.Class, User.Address, User.Keys, Student.CCA, Teacher.TeacherID, Teacher.Department FROM User LEFT OUTER JOIN Login ON (User.UserID = Login.UserID) LEFT OUTER JOIN Student ON (User.UserID = Student.UserID) LEFT OUTER JOIN Teacher ON (User.UserID = Teacher.UserID) ORDER BY UserID;");
		ArrayList<UserAll> userAllArray = new ArrayList<UserAll>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String nRIC = rs.getString("NRIC");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			String keys = rs.getString("Keys");
			User user = new User(userID, nRIC, name, gender, dOB, contactNo, email, schoolClass, address, keys);
			
			String username = rs.getString("Username");
			String password = rs.getString("Password");
			String salt = rs.getString("Salt");
			Login login = new Login(username, password, salt, user);
			
			String cCA = rs.getString("CCA");
			Student student = new Student(cCA, user);
			
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
			String nRIC = rs.getString("NRIC");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			String keys = rs.getString("Keys");
			userArray.add(new User(userID, nRIC, name, gender, dOB, contactNo, email, schoolClass, address, keys));
		}
		
		rs.close();
		return userArray;
	}
	
	public ArrayList<Login> getDatabaseLogin() throws SQLException{
		ResultSet rs = getDatabaseData("SELECT * FROM Login INNER JOIN User ON Login.userID = User.userID;");
		ArrayList<Login> loginArray = new ArrayList<Login>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String nRIC = rs.getString("NRIC");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			String keys = rs.getString("Keys");
			
			User user = new User(userID, nRIC, name, gender, dOB, contactNo, email, schoolClass, address, keys);
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
			String nRIC = rs.getString("NRIC");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			String keys = rs.getString("Keys");
			
			User user = new User(userID, nRIC, name, gender, dOB, contactNo, email, schoolClass, address, keys);
			
			String cCA = rs.getString("CCA");
			Student student = new Student(cCA, user);
			
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
			String nRIC = rs.getString("NRIC");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			String keys = rs.getString("Keys");
			
			User user = new User(userID, nRIC, name, gender, dOB, contactNo, email, schoolClass, address, keys);
			int teacherID = rs.getInt("TeacherID");
			String department = rs.getString("Department");
			Teacher teacher = new Teacher(teacherID, department, user);
			teacherArray.add(teacher);
		}
		
		rs.close();
		return teacherArray;
	}
	
	public ArrayList<File> getDatabaseFile() throws SQLException{
		ResultSet rs = getDatabaseData("SELECT * FROM File LEFT JOIN User ON User.userID = File.userID;");
		ArrayList<File> fileArray = new ArrayList<File>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String nRIC = rs.getString("NRIC");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			String keys = rs.getString("Keys");
			
			User user = new User(userID, nRIC, name, gender, dOB, contactNo, email, schoolClass, address, keys);
			
			int fileID = rs.getInt("fileID");
			String fileName = rs.getString("fileName");;
			int fileSize = rs.getInt("size");
			Blob dataBlob = rs.getBlob("Data");
			String recipient = rs.getString("recipient");
			Date date = rs.getDate("Date");
			byte [] fileData = dataBlob.getBytes(1, (int)dataBlob.length());
			File file = new File(fileID, user, fileName, fileSize, fileData, recipient, date);
			fileArray.add(file);
		}
		
		rs.close();
		return fileArray;
	}
	
	public ArrayList<UserAll> convertResultSetToArrayList(ResultSet rs) throws SQLException{
		ArrayList<UserAll> userAllArray = new ArrayList<UserAll>();
		
		while(rs.next()){
			int userID = rs.getInt("userID");
			String nRIC = rs.getString("NRIC");
			String name = rs.getString("Name");
			String gender = rs.getString("Gender");
			String dOB = rs.getString("DOB");
			String contactNo = rs.getString("ContactNo");
			String email = rs.getString("Email");
			String schoolClass = rs.getString("Class");
			String address = rs.getString("Address");
			String keys = rs.getString("Keys");
			
			User user = new User(userID, nRIC, name, gender, dOB, contactNo, email, schoolClass, address, keys);
			
			String username = rs.getString("Username");
			String password = rs.getString("Password");
			String salt = rs.getString("Salt");
			Login login = new Login(username, password, salt, user);
			
			String cCA = rs.getString("CCA");
			Student student = new Student(cCA, user);
			
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
	
	public void updateDatabaseDataFileUpload(String sql, int userID, String fileName, long fileSize, InputStream data, Date date) throws SQLException{
		ppstmt = conn.prepareStatement(sql);
		ppstmt.setInt(1, userID);
		ppstmt.setString(2, fileName);
		ppstmt.setLong(3, fileSize);
		ppstmt.setBlob(4, data);
		ppstmt.setObject(5, date);
		
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

	public Connection getConn() {
		return conn;
	}
	
	/*
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		DatabaseDAO dao = new DatabaseDAO(0);
		
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String keys = salt.toString();
        
        Connection conn = dao.getConn();
        String query = "UPDATE User SET User.Keys = ? WHERE User.UserID = 13";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, keys);
        pstmt.execute();
        
		dao.close();
	}
	*/
	
	/* For File Uploads...
	 * 
	 * //"<form action='Home' method='POST' enctype='multipart/form-data'><input type='file' name='file' /><input type='submit' /></form>" (HTML)
	 * 
	 * Part filePart = request.getPart("file"); (Java)
	 * String fileName = getSubmittedFileName(filePart);
	 * long fileSize = filePart.getSize();
	 * System.out.println("Name: " + fileName + "\nSpace: " + fileSize);
	 * InputStream inputStream = filePart.getInputStream();
	 * try {
	 * 		DatabaseAccess dba = new DatabaseAccess(0);
	 * 		String sqlline = "INSERT INTO File(FileName, Size, Data) VALUES (?, ?, ?);";
	 *		dba.updateDatabaseDataFileUpload(sqlline, fileName, fileSize, inputStream);
	 *		dba.close();
	 *	} catch (ClassNotFoundException e) {
	 *		e.printStackTrace();
	 *	} catch (SQLException e) {
	 *		e.printStackTrace();
	 *	}
	 *
	 * 	private static String getSubmittedFileName(Part part) {
	 * 		for (String cd : part.getHeader("content-disposition").split(";")) {
	 *       	if (cd.trim().startsWith("filename")) {
	 *           	String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	 *           	return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
	 *     	 	}
	 *  	}
	 *  	return null;
	 *	}
	 *
	For File Downloads...
	*
	*	private static final int BUFFER_SIZE = 4096;
	*
	*	String fileName = "autoplaylist.txt";
	*	try {
	*		DatabaseAccess dba = new DatabaseAccess(0);
	*	  	String sqlline = "SELECT * FROM File WHERE fileName = ?;";
	*		ResultSet result = dba.getDatabaseData(sqlline, fileName);
	*	 	response.setHeader("Content-Disposition",
    *                "attachment;filename=" + fileName);
	*	 	if (result.next()) {
    *            Blob blob = result.getBlob("Data");
    *            InputStream inputStream = blob.getBinaryStream();
    *            OutputStream out = response.getOutputStream();
    *
    *           int bytesRead = -1;
    *            byte[] buffer = new byte[BUFFER_SIZE];
    *           while ((bytesRead = inputStream.read(buffer)) != -1) {
    *                out.write(buffer, 0, bytesRead);
    *            }
    *
    *            inputStream.close();
    *             out.flush();
    *            out.close();
    *           System.out.println("File saved");
    *       }
	*	 	dba.close();
	*	} catch (ClassNotFoundException e) {
	*	 	e.printStackTrace();
	*	} catch (SQLException e) {
	*	 	e.printStackTrace();
	*	}
	*
	*/
}
