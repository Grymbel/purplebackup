package backupScheduler;

import java.util.Timer;
import java.util.TimerTask;

public class TimerAccess {
	private static Timer timer = new Timer();
	
	public static void startTimer(){
		ScheduleClock sch = new ScheduleClock();
		sch.scheduleServiceFirst();

    	TimerTask task = new AutoBackupProcess();

    	timer = new Timer();
    	timer.schedule(task, 1000, 30000);
	}
	
	public static void closeTime(){
		timer.cancel();
	}
}
