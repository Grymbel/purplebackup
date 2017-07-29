package backupHIDS;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnect;

public class HIDSService {

	public static void doHIDS(){
		DBConnect dbc = new DBConnect();
		
		try {
			ResultSet res = dbc.getHashList();
			while(res.next()){
				HIDSObject hidso = new HIDSObject(res.getString("sha1hash"),res.getInt("relid"),res.getString("relDir"));
				hidso.CompareToCurr();
				if(!hidso.isResolved()||hidso.getAlertMessage()!=null){
					dbc.setHIDSAlert(hidso.getRelid(), 
							hidso.getRelDir(), 
							hidso.getAlertMessage());
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
