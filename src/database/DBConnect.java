package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteException;

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
	 
	 public void addFileIdx(int backupID, String fileLine, String digest, String targetDir) throws SQLException{
		 PreparedStatement prep = con.prepareStatement("insert into fileIdx(backupID, fileLine, filedigest, targetdir) values (?,?,?,?);");
		 prep.setInt(1, backupID);
		 prep.setString(2, fileLine);
		 prep.setString(3, digest);
		 prep.setString(4, targetDir);
		 prep.execute();
	 }
	 
	 public ResultSet getFileIdx(int toGet,String targetDir) throws SQLException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select fileLine, fileDigest from fileIdx where backupID = "+toGet +" and targetDir = '" +targetDir+"'");
		 return res;
	 }
	 
	 public ResultSet getFileLocation() throws SQLException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select * from fileLocation;");
		 return res;
	 }
	 
	 public void setFileLocation(String relDir, String fileURL) throws SQLException{
		 System.out.println(fileURL);
		 Statement state = con.createStatement();
		 state.executeUpdate("update fileLocation set target = '"+fileURL+"' where relDir = '"+relDir+"';");
	 }
	 public void addFileDelta(int backupID, String deltaAction, String fileLine, String fileDigest, String targetDir) throws SQLException{
		 PreparedStatement prep = con.prepareStatement("insert into fileDelta(backupID, deltaAction, fileLine, fileDigest, targetDir) values (?,?,?,?,?)");
		 prep.setInt(1,backupID);
		 prep.setString(2, deltaAction);
		 prep.setString(3, fileLine);
		 prep.setString(4, fileDigest);
		 prep.setString(5, targetDir);
		 prep.execute();
	 }
	 
	 public ResultSet getFileDelta(int toGet,String targetDir) throws SQLException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select deltaAction, fileLine, fileDigest from fileDelta where backupID = "+toGet + " and targetDir = '" +targetDir+"'");
		 return res;
	 }
	 
	 public ResultSet getFileDeltaRange(int toGetStart,int toGetEnd) throws SQLException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select deltaAction, fileLine, fileDigest from fileDelta where backupID between "+toGetStart+" and "+toGetEnd+";");
		 return res;
	 }
	 
	 public void debugBackups() throws SQLException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select * from Backups");
		 
		 while(res.next()){
			 System.out.print(res.getInt("id")+"|");
		 }
	 }

	 public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 private void initialise() throws SQLException {
		 long time=0;
		 boolean doBackup = false;
		 if( !hasData ) {
			 hasData = true;
			 // check for database table
			 Statement state = con.createStatement();
			 ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Backups'");
			 if( !res.next()) {
				 System.out.println("Building tables with prepopulated values.");
				 time = System.currentTimeMillis();
				 
				 
				 // need to build the tables
				  Statement state2 = con.createStatement();
				  state2.executeUpdate("create table Backups(id integer,"
				    + "audit boolean," + "web boolean,"
						  + "user boolean," + "cloud boolean,"
						  + "isBase boolean," + "creation varchar(14)," 
				    + "primary key (id));");

				  Statement state3 = con.createStatement();
				  state3.executeUpdate("create table fileidx(id integer, "
				  		+ "backupid integer," + "fileLine varchar(250),"
						  +"fileDigest varchar(128),"
				  		+"targetDir varchar(50),"
						  + "primary key (id));");
				  
				  System.out.println("Made fileIdx");
				  
				  Statement state4 = con.createStatement();
				  state4.executeUpdate("create table fileDelta(id integer, backupid integer, deltaaction varchar(3), fileLine varchar(250), fileDigest varChar(128), targetDir varchar(50), primary key(id))");
				  
				  System.out.println("Made fileDelta");
		
				  // inserting some sample data
				  PreparedStatement prep = con.prepareStatement("insert into Backups(id, audit, cloud, user, web, creation, isBase) values(?,?,?,?,?,?,?);");
				  prep.setInt(1, 0);
				  prep.setBoolean(2, true);
				  prep.setBoolean(3, true);
				  prep.setBoolean(4, true);
				  prep.setBoolean(5, true);
				  prep.setString(6, time+"");
				  prep.setBoolean(7, true);
				  prep.execute();
				  
				  doBackup=true;
			 }
			 Statement state1 = con.createStatement();
			 ResultSet res1 = state1.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='filelocation'");
			 
			 if(!res1.next()){
				 try{
				 Statement state5 = con.createStatement();
				 
				 try{
				  state5.executeUpdate("create table fileLocation(id integer, relDir varchar(16), target varchar(200), primary key(id))");
				  
				  System.out.println("Made fileLocation");
				 }
				 catch(Exception e){
				 }
				  
				  PreparedStatement prep = con.prepareStatement("insert into fileLocation(relDir, target) values(?,?);");

				  prep.setString(1,"audit");
				  prep.setString(2,"src/sampleTarget/auditTarget");
				  prep.execute();
				  
				  prep.setString(1,"cloud");
				  prep.setString(2,"src/sampleTarget/cloudTarget");
				  prep.execute();
				  
				  prep.setString(1,"user");
				  prep.setString(2,"src/sampleTarget/userTarget");
				  prep.execute();
				  
				  prep.setString(1,"web");
				  prep.setString(2,"src/sampleTarget/webTarget0");
				  prep.execute();
				 }
				 catch(SQLiteException s){
					 s.printStackTrace();
				 }
			 }
			 if(doBackup){
			 BackupObject bo =new BackupObject();
			 bo.makeBaseBackupFirst(time);
			 }
			 //
			 Statement state2 = con.createStatement();
			 ResultSet res2 = state2.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Schedule'");
			 
			 if(!res2.next()){
				 try{
				 Statement state6 = con.createStatement();
				 
				  state6.executeUpdate("create table Schedule(id integer, sname varchar(40), maxTimes integer, timesDone integer, dayTime long, interval long, startingDay long, lastDone long, primary key(id))");
				  
				  System.out.println("Made Schedule");
				 }
				 catch(Exception e){
				 }
			 }
		 }
		 
	 }
	 
}
