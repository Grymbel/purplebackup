package backupHIDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnect;

public class HIDSDAO {
	public HIDSDAO(){
		
	}
	
	//Returns all HIDS entries
	public ArrayList<HIDSObject> getAllHIDSEntries(){
		ArrayList<HIDSObject> toRet = new ArrayList<HIDSObject>();
		DBConnect dbc = new DBConnect();
		try {
			ResultSet res = dbc.getHIDSData();
			
			while(res.next()){
				HIDSObject ho = new HIDSObject(res.getString("sha1hash"),res.getInt("relid"),res.getString("relDir"),res.getString("message"),res.getBoolean("resolved"));
				toRet.add(ho);
			}
			dbc.setHIDSRead();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return toRet;
	}
}
