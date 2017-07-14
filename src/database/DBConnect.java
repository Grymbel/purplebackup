package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
	 private static Connection con;
	 private static boolean hasData = false;
	 
	 private void getConnection() throws ClassNotFoundException, SQLException {
		  // sqlite driver
		  Class.forName("org.sqlite.JDBC");
		  // database path, if it's new database, it will be created in the project folder
		  con = DriverManager.getConnection("jdbc:sqlite:LiteTest.db");
		  initialise();
	 }
	 

	public void addBackups(boolean audit, boolean cloud, boolean user, boolean web, long creation, boolean isBase) throws ClassNotFoundException, SQLException {
		 if(con == null) {
			 // get connection
			 getConnection();
		 }
		  PreparedStatement prep = con
				    .prepareStatement("insert into Backups values(?,?,?,?,?,?);");
				  prep.setBoolean(2, audit);
				  prep.setBoolean(3, cloud);
				  prep.setBoolean(4, user);
				  prep.setBoolean(4, web);
				  prep.setString(5, creation+"");
				  prep.setBoolean(6, isBase);
				  prep.execute();
		 
	 }
	 
	 public ResultSet displayBackups() throws SQLException, ClassNotFoundException {
		 if(con == null) {
			 // get connection
			 getConnection();
		 }
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select * from Backups");
		 return res;
	 }
	 
	 public int highestID() throws ClassNotFoundException, SQLException{
		 if(con == null){
			 getConnection();
		 }
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select max(id) as ldbID from Backups");
		 return res.getInt("ldbID");
	 }
	 
	 public int highestBase() throws ClassNotFoundException, SQLException{
		 if(con == null){
			 getConnection();
		 }
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery(""
		 		+ "select max(id) as ldbBase from (select id from Backups where isBase=true))"
		 		+ "");
		 return res.getInt("ldbBase");
	 }

	 
	 private void initialise() throws SQLException {
		 if( !hasData ) {
			 hasData = true;
			 // check for database table
			 Statement state = con.createStatement();
			 ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Backups'");
			 
			 
			 
			 if( !res.next()) {
				 System.out.println("Building the User table with prepopulated values.");
				 // need to build the table
				  Statement state2 = con.createStatement();
				  state2.executeUpdate("create table Backups(id integer,"
				    + "audit boolean," + "message boolean,"
						  + "user boolean," + "cloud boolean,"
						  + "isBase boolean," + "creation varchar(14)," 
				    + "primary key (id));");

				  // inserting some sample data
				  PreparedStatement prep = con.prepareStatement("insert into Backups values(?,?,?,?,?);");
				  prep.setBoolean(2, true);
				  prep.setBoolean(3, true);
				  prep.setBoolean(4, true);
				  prep.setString(5, "1337");
				  prep.execute();
			 }
			 
		 }
	 }
	 
}
