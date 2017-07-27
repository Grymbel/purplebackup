package backupScheduler;

import java.util.TimerTask;

import backupMaker.BackupObject;

public class AutoBackupProcess extends TimerTask{
	@Override
	public void run() {
		ScheduleClock sch = new ScheduleClock();
		BackupObject bo = sch.scheduleClock();
		
		if(bo.isFilled()){
			bo.makeManualBackup(bo.getCreationDate());
		}
	}
}