package backupMaker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnect;

public class LastDoneBackupDAO {
	//Reads most recent info on backups
	public String ldbRead(){
			DBConnect dbc = new DBConnect();
			String toRet;
			try {
				toRet = dbc.highestID()+";"+dbc.highestTime()+";"+dbc.highestBase()+";";
				System.out.println("Returning: "+toRet);
				return toRet;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				dbc.close();
			}
		return null;
	}
	//Gets a list of all base backups to see where to start the incremental
	public ArrayList<Integer> getBases(){
		ArrayList<Integer> bases = new ArrayList<Integer>();
		DBConnect dbc = new DBConnect();
		
		try {
			ResultSet res = dbc.getBaseList();
			while(res.next()){
				bases.add(res.getInt("id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(bases);
		return bases;
	}
}
