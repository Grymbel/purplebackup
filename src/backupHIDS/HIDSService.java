package backupHIDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnect;

public class HIDSService {

	public static void doHIDS(){
		ArrayList<String> ErrorList=new ArrayList<String>();
		DBConnect dbc = new DBConnect();
		
		try {
			ResultSet res = dbc.getHashList();
			while(res.next()){
				HIDSObject hidso = new HIDSObject(res.getString("sha1hash"),res.getInt("relid"),res.getString("relDir"));
				if(!hidso.CompareToCurr().equals("")){
				ErrorList.add(hidso.CompareToCurr());
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println(ErrorList);
	}
}
