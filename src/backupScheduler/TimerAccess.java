package backupScheduler;

import java.util.TimerTask;

//The timer class
public class TimerAccess {
	private static TimerV2 timer = new TimerV2();
	
	//Starts the clock
	public static void startTimer(){
		System.out.println("Starting timer!");
		ScheduleClock sch = new ScheduleClock();
		sch.scheduleServiceFirst();

    	TimerTask task = new AutoBackupProcess();

    	timer = new TimerV2();
    	timer.schedule(task, 1000, 30000);
    	timer.setRunning();
	}
	
	public static boolean isRunning(){
		return timer.isRunning();
	}
	
	public static void closeTime(){
		timer.cancel();
	}
}
