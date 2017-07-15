package backupMaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import database.DBConnect;

public class BackupDAO {
	
	public BackupDAO(){
	}
	public ArrayList<String> getExistingBackups(){
		ArrayList<String> existing = new ArrayList<String>();
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
				System.out.println(bo);
				existing.add(bo.toString());
				
				dbc.close();
				return existing;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			dbc.close();
		}
		return null;
	}
	
	public static void manualBackup(BackupObject bcko){
			DBConnect dbc = new DBConnect();
			try {
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
	
	public String[] getTargetDirs(){
		String[] toRet=new String[5];
		String buffer;
		try {
			FileReader fr = new FileReader("src/output/targetLocations.txt");
			
			Scanner sc = new Scanner(fr);
			sc.useDelimiter(";");
			int c=0;
			while(sc.hasNextLine()){
				buffer=sc.nextLine();
				toRet[c]=buffer.replace(";", "");
				c++;
			}
			
			sc.close();
			return toRet;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return toRet;
	}
}
