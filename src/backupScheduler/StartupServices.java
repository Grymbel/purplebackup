package backupScheduler;

import java.util.Timer;
import java.util.TimerTask;

import backupHIDS.HIDSService;

public abstract class StartupServices {
	public void doStartupServices(){
		HIDSService.doHIDS();
		bsService();
	}
	public void bsService(){
		ScheduleClock sch = new ScheduleClock();
		sch.scheduleServiceFirst();

    	TimerTask task = new AutoBackupProcess();

    	Timer timer = new Timer();
    	timer.schedule(task, 1000, 30000);
    }
}
