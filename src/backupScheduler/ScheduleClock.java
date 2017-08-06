package backupScheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import backupMaker.BackupObject;
import database.DBConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ScheduleClock {
	private ArrayList<String> alertNames;
	private long time;

	public ScheduleClock(){
		
	}
	
	//Starts the clock and checks for missed schedule entries
	public void scheduleServiceFirst(){
		DBConnect dbc = new DBConnect();
		alertNames = new ArrayList<String>();
		try {
			System.out.println("Schedule Clock");
			ResultSet res = dbc.getAllSchedules();
			time=System.currentTimeMillis();
			
			while(res.next()){
				System.out.println("Reading result set");
				
				System.out.println(MillisConverter.getTimeDifference());
				
				long nextTime = res.getLong("nextTime");
				int timesDone = res.getInt("timesDone");
				int maxTimes = res.getInt("timesDone");
				
				System.out.println(nextTime+"||"+time);
				if(nextTime<time){
					alertNames.add(
							res.getString("sname"));

					while(nextTime<time){
						nextTime+=res.getLong("interval");
						timesDone++;
						if(timesDone==maxTimes){
							System.out.println("Outdated!");
							dbc.removeSchedule(res.getInt("id"));
						}
					}
				dbc.updateScheduleLate(nextTime, res.getInt("id"), timesDone);
				}
			}
			if(alertNames.size()>0){
				Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Schedule(s) missed");
	    		alert.setHeaderText("Some backups were missed because the app was closed");
	    		alert.setContentText("Names of missed Backups \n"+alertNames);

	    		alert.showAndWait();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Checks for backups to do and does them
	public BackupObject scheduleClock(){
		BackupObject bo = new BackupObject();
		DBConnect dbc = new DBConnect();
		
		try {
			System.out.println("Schedule Clock: Minute Mode");
			ResultSet res = dbc.getAllSchedules();
			time=System.currentTimeMillis();
			
			while(res.next()){
				long nextTime = res.getLong("nextTime");
				int timesDone = res.getInt("timesDone");
				int maxTimes = res.getInt("maxTimes");
				
				if(nextTime<time){
					System.out.println("Doing Backup!");
					
								bo.setAuditBackup(res.getBoolean("audit"));
								bo.setCloudBackup(res.getBoolean("cloud"));
								bo.setUserBackup(res.getBoolean("user"));
								bo.setWebBackup(res.getBoolean("web"));
								bo.setCreationDate(time);
						
						nextTime+=res.getLong("interval");
						timesDone++;
						System.out.println(timesDone+"||"+maxTimes);
						if(timesDone==maxTimes){
							System.out.println("Outdated!");
							dbc.removeSchedule(res.getInt("id"));
						}
						dbc.updateScheduleLate(nextTime, res.getInt("id"), timesDone);
					}
				}
			
			}catch (SQLException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		return bo;
	}

}
