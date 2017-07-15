package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backupMaker.BackupObject;

public class DBConnect {
	 private static Connection con;
	 private static boolean hasData = false;
	 
	 private void getConnection() throws ClassNotFoundException, SQLException {
		  // sqlite driver
		  Class.forName("org.sqlite.JDBC");
		  // database path, if it's new database, it will be created in the project folder
		  con = DriverManager.getConnection("jdbc:sqlite:purplebackups.db");
		  initialise();
	 }
	 
	 public DBConnect(){
		 try {
			 System.out.println("Connecting!");
			getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 

	public void addBackup(boolean audit, boolean cloud, boolean user, boolean web, long creation, boolean isBase) throws ClassNotFoundException, SQLException {
		  PreparedStatement prep = con
				    .prepareStatement("insert into Backups(audit, cloud, user, web, creation, isBase) values(?,?,?,?,?,?);");
				  prep.setBoolean(1, audit);
				  prep.setBoolean(2, cloud);
				  prep.setBoolean(3, user);
				  prep.setBoolean(4, web);
				  prep.setString(5, creation+"");
				  prep.setBoolean(6, isBase);
				  prep.execute();
		 
	 }
	 
	 public ResultSet getAllBackups() throws SQLException, ClassNotFoundException {
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select id, audit, cloud, user, web, creation, isBase from Backups");
		 return res;
	 }
	 
	 public ResultSet getBaseList() throws SQLException{
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("select id from Backups where isBase");
			
			return res;
	 }
	 
	 public int highestID() throws ClassNotFoundException, SQLException{
		 try{
			 getConnection();
		 }
		 catch(Exception e){
			 
		 }
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select max(id) as ldbID from Backups");
		 return res.getInt("ldbID");
	 }
	 
	 public int highestBase() throws ClassNotFoundException, SQLException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select max(ID) as ldbBase from (select id from Backups where isBase)");
		 return res.getInt("ldbBase");
	 }
	 
	 public int highestTime() throws SQLException, ClassNotFoundException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select max(creation) as ldbTime from Backups");
		 return res.getInt("ldbTime");
	 }

	 public void close(){
		try {
			System.out.println("Closing!");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 private void initialise() throws SQLException {
		 if( !hasData ) {
			 hasData = true;
			 // check for database table
			 Statement state = con.createStatement();
			 ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Backups'");
			 
			 
			 
			 if( !res.next()) {
				 System.out.println("Building the User table with prepopulated values.");
				 long time = System.currentTimeMillis();
				 BackupObject bo =new BackupObject();
				 bo.makeBaseBackupFirst(time);
				 
				 // need to build the table
				  Statement state2 = con.createStatement();
				  state2.executeUpdate("create table Backups(id integer,"
				    + "audit boolean," + "web boolean,"
						  + "user boolean," + "cloud boolean,"
						  + "isBase boolean," + "creation varchar(14)," 
				    + "primary key (id));");

				  // inserting some sample data
				  PreparedStatement prep = con.prepareStatement("insert into Backups(audit, cloud, user, web, creation, isBase) values(?,?,?,?,?,?);");
				  prep.setBoolean(1, true);
				  prep.setBoolean(2, true);
				  prep.setBoolean(3, true);
				  prep.setBoolean(4, true);
				  prep.setString(5, time+"");
				  prep.setBoolean(6, true);
				  prep.execute();
			 }
			 
		 }
	 }
	 
}
