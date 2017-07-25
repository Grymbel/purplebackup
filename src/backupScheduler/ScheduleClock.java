package backupScheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnect;

public class ScheduleClock {
	private ArrayList<String> alertNames;
	private long time;

	public ScheduleClock(boolean todo){
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
						System.out.println("While "+timesDone);
						System.out.println(nextTime+"||"+res.getLong("interval"));
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
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public ScheduleClock(){
		
	}
	
	public void clockwork(){
		
	}
	public static long deriveNext(int timesDone, long interval, long dayTime, long startingDay){
		long toRet=0;
		
		toRet = (timesDone*interval) + dayTime + startingDay;

		return toRet;
	}	
}
