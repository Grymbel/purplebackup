package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteException;

import backupMaker.BackupObject;
import backupScheduler.ScheduleObject;

public class DBConnect {
	 private static Connection con;
	 private static boolean hasData = false;
	 
	 private void getConnection() throws ClassNotFoundException, SQLException {
		  // sqlite driver
		  
		  // database path, if it's new database, it will be created in the project folder
		  Class.forName("org.sqlite.JDBC");
		 
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
	 
	 public long highestTime() throws SQLException, ClassNotFoundException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select max(creation) as ldbTime from Backups");
		 return res.getLong("ldbTime");
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
	 
	 public String getRestorePath() throws SQLException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select * from fileLocation where reldir ='restore';");
		 return res.getString("target");
	 }
	 
	 public void setFileLocation(String relDir, String fileURL) throws SQLException{
		 System.out.println(fileURL);
		 PreparedStatement state = con.prepareStatement("update fileLocation set target = '?' where relDir = '?';");
		 state.setString(1, relDir);
		 state.setString(2, fileURL);
		 state.execute();
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
	 
	 public void addSchedule(ScheduleObject so) throws SQLException{
		 PreparedStatement prep = con.prepareStatement("insert into schedule(sname, maxtimes, timesdone, daytime, interval, startingday, lastdone, nextTime, audit, cloud, user, web) values(?,?,?,?,?,?,?,?,?,?,?,?)");
		 prep.setString(1,so.getName());
		 prep.setInt(2, so.getMaxTimes());
		 prep.setInt(3, so.getTimesDone());
		 
		 prep.setLong(4, so.getDayTime());
		 prep.setLong(5, so.getInterval());
		 prep.setLong(6, so.getStartingDay());
		 prep.setLong(7, so.getLastDone());
		 
		 prep.setLong(8, so.getNextTime());
		 
		 prep.setBoolean(9, so.getInternalBO().getAuditBackup());
		 prep.setBoolean(10, so.getInternalBO().getCloudBackup());
		 prep.setBoolean(11, so.getInternalBO().getUserBackup());
		 prep.setBoolean(12, so.getInternalBO().getWebBackup());
		 
		 prep.execute();
	 }
	 
	 public ResultSet getAllSchedules() throws SQLException{
		 Statement state = con.createStatement();
		 ResultSet res = state.executeQuery("select * from schedule;");
		 return res;
	 }
	 
	 public void removeSchedule(int id) throws SQLException{
		 PreparedStatement prep = con.prepareStatement("delete from schedule where id="+id+";");
		 prep.execute();
	 }
	 
	 public void updateScheduleSuccess(long justDone, long nextTime, int id) throws SQLException{
		 int oldTimesDone;
		 
		 Statement state = con.createStatement();
		 ResultSet res1 = state.executeQuery("select timesDone from schedule where id = "+id+";");
		 
		 
		 while(res1.next()){
			 oldTimesDone = res1.getInt("timesDone")+1;
			 state.execute("update schedule set timesDone = "+oldTimesDone+", lastDone = "+justDone+" ,nextTime = "+nextTime+" where id = "+id);
		 }
	 }
	 
	public void updateScheduleLate(long nextTime, int id, int timesDone) throws SQLException{
		Statement state = con.createStatement();
		state.execute("update schedule set nextTime = "+nextTime + ", timesDone = "+timesDone+" where id = "+id);
	}
	
	public void addHIDS(int relID, String relDir, String hash) throws SQLException{
		PreparedStatement prep = con.prepareStatement("insert into HIDS(relid, relDir, sha1hash, resolved)values(?,?,?,?)");
		prep.setInt(1, relID);
		prep.setString(2, relDir);
		prep.setString(3, hash);
		prep.setBoolean(4, true);
		
		prep.execute();
	}
	
	public void updateHash(int relID, String relDir, String hash) throws SQLException{
		PreparedStatement prep = con.prepareStatement("update HIDS SET sha1hash = ? where relid = ? and relDir = ?;");
		prep.setString(1, hash);
		prep.setInt(2, relID);
		prep.setString(3, relDir);
		
		prep.executeUpdate();
	}
	
	public void resolveHIDS(int relID, String relDir) throws SQLException{
		PreparedStatement prep = con.prepareStatement("update HIDS SET resolved = ? where relid = ? and relDir = ?;");
		prep.setBoolean(1, true);
		prep.setInt(2, relID);
		prep.setString(3, relDir);
		
		prep.executeUpdate();
	}
	
	public void resolveAll() throws SQLException{
		PreparedStatement prep = con.prepareStatement("update HIDS Set resolved = ?;");
		prep.setBoolean(1, true);
		prep.executeUpdate();
	}
	
	public void unresolveHIDS(int relID, String relDir) throws SQLException{
		PreparedStatement prep = con.prepareStatement("update HIDS SET resolved = ? where relid = ? and relDir = ?;");
		prep.setBoolean(1, false);
		prep.setInt(2, relID);
		prep.setString(3, relDir);
		
		prep.executeUpdate();
	}
	
	public ResultSet getHashList() throws SQLException{
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("select sha1hash, relid, reldir, read from HIDS");
		return res;
	}
	
	public ResultSet getHIDSData() throws SQLException{
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("select * from HIDS");
		return res;
	}
	
	public void setHIDSAlert(int relid, String relDir, String message) throws SQLException{
		PreparedStatement prep = con.prepareStatement("update HIDS SET message = ?, resolved = ? where relid = ? and relDir = ?");
		prep.setString(1, message);
		prep.setBoolean(2, false);
		prep.setInt(3, relid);
		prep.setString(4, relDir);
		
		prep.executeUpdate();
	}
	
	public void setHIDSRead() throws SQLException{
		PreparedStatement prep = con.prepareStatement("update HIDS Set read = ?;");
		prep.setBoolean(1, true);
		prep.executeUpdate();
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
			 try{
				 Statement state4 = con.createStatement();
				 
				 state4.executeUpdate("create table HIDS(id integer, sha1hash varchar(128), relid integer, reldir varchar(20), message varchar(140), resolved boolean, read boolean, primary key(id))");
				 System.out.println("Made HIDS");
			 }catch(Exception e){
				 
			 }
			 
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
				  
				  prep.setString(1,"restore");
				  prep.setString(2,"src/restore");
				  prep.execute();
				  
				  System.out.println("Made fileLocation");
				 }
				 catch(Exception e){
				 }
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
				 
				  state6.executeUpdate("create table Schedule(id integer, sname varchar(40), maxTimes integer, timesDone integer, dayTime long, interval long, startingDay long, lastDone long, nextTime long, audit boolean, cloud boolean, user boolean, web boolean, primary key(id))");
				  
				  System.out.println("Made Schedule");
				 }
				 catch(Exception e){
				 }
			 }
			 }
		 }
		 
	 }
	 
