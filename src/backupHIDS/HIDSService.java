package backupHIDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HIDSService {

	//The all in one execution for the HIDS alert process
	public static void doHIDS(){
		DBConnect dbc = new DBConnect();
		ArrayList<String> toPrint = new ArrayList<String>();
		
		boolean alertOrNot = false;
		try {
			ResultSet res = dbc.getHashList();
			while(res.next()){
				//Creates an object from the DB, compares and produces alerts
				HIDSObject hidso = new HIDSObject(res.getString("sha1hash"),res.getInt("relid"),res.getString("relDir"),res.getBoolean("read"));
				hidso.CompareToCurr();
				
				if((!hidso.isResolved()||hidso.getAlertMessage()!=null)&&!hidso.isRead()){
					dbc.setHIDSAlert(hidso.getRelID(), 
							hidso.getRelDir(), 
							hidso.getAlertMessage());
				}
				
				if(!hidso.isResolved()&&hidso.getAlertMessage()!=null&&!hidso.isRead()){
					System.out.println(!hidso.isResolved()+hidso.getAlertMessage());
					alertOrNot=true;
					toPrint.add(hidso.getAlertMessage()+"\n");
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		if(alertOrNot){
			String toAlert="";
			for(String s : toPrint){
				toAlert = toAlert+s;
			}
			
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("HIDS ALERT");
    		alert.setHeaderText("A new base backup is advised");
    		alert.setContentText(toAlert);

    		alert.show();
		}
	}
}
