package backupScheduler;

import java.util.Timer;

public class TimerV2 extends Timer{
	private boolean isRunning;

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning() {
		this.isRunning = true;
	}
	
	public TimerV2(){
		super();
		isRunning=false;
	}
	
}
