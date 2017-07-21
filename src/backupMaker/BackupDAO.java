package backupMaker;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnect;

public class BackupDAO {
	
	public BackupDAO(){
	}
	//Returns ResultSet for table displayed
	public ArrayList<BackupObject> getExistingBackups(){
		ArrayList<BackupObject> existing = new ArrayList<BackupObject>();
		DBConnect dbc = new DBConnect();
		try {
			ResultSet rez = dbc.getAllBackups();
			while(rez.next()){
				BackupObject bo = new BackupObject(
						rez.getBoolean("user"),
						rez.getBoolean("cloud"),
						rez.getBoolean("web"),
						rez.getBoolean("audit"),
						Long.parseLong(rez.getString("creation")),
						rez.getBoolean("isBase")
						);
				existing.add(bo);
			}
			return existing;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			dbc.close();
		}
		return null;
	}
	//Manually backup the supplied backup object
	public static void manualBackup(BackupObject bcko){
			DBConnect dbc = new DBConnect();
			try {
				System.out.println("Documenting: "+bcko);
				dbc.addBackup(bcko.getAuditBackup(), bcko.getCloudBackup(), bcko.getUserBackup(), 
						bcko.getWebBackup(), bcko.getCreationDate(), bcko.getIsBase());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				dbc.close();
			}
	}
	
	public static void makeDir(String dirName){
		File file = new File("src/output/"+dirName);
		file.mkdir();
	}
	//Reads off the current text file. Will change to SQLite when I make the app
	public String[] getTargetDirs(){
		String[] toRet=new String[4];
		
			DBConnect dbc = new DBConnect();
			
			try {
				ResultSet toRead = dbc.getFileLocation();
				while(toRead.next()){
				String test = toRead.getString("relDir");
				
				if(test.equals("audit")){
					toRet[0]=toRead.getString("target");
				}
				if(test.equals("cloud")){
					toRet[1]=toRead.getString("target");
				}
				if(test.equals("user")){
					toRet[2]=toRead.getString("target");
				}
				if(test.equals("web")){
					toRet[3]=toRead.getString("target");
				}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return toRet;
	}
}
