package backupScheduler;

import backupMaker.BackupObject;

public class ScheduleObject {
	private String name;
	private int maxTimes;
	private int remainingTimes;
	private int timesDone;
	private long dayTime;
	private long interval;
	private long startingDay;
	private long lastDone;
	
	private BackupObject internalBO;
	
	public ScheduleObject(String name, int maxTimes, long dayTime, long interval, long startingDay){
		this.name=name;
		this.maxTimes=maxTimes;
		this.dayTime=dayTime;
		this.interval=interval;
		this.startingDay=startingDay;
		
		this.remainingTimes=maxTimes;
		this.timesDone=0;
	}
	
	public long getNextTime(){
		return startingDay+dayTime+(timesDone*interval);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxTimes() {
		return maxTimes;
	}

	public void setMaxTimes(int maxTimes) {
		this.maxTimes = maxTimes;
	}

	public int getRemainingTimes() {
		return remainingTimes;
	}

	public void setRemainingTimes(int remainingTimes) {
		this.remainingTimes = remainingTimes;
	}

	public long getDayTime() {
		return dayTime;
	}

	public void setDayTime(long dayTime) {
		this.dayTime = dayTime;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public long getStartingDay() {
		return startingDay;
	}

	public void setStartingDay(long startingDay) {
		this.startingDay = startingDay;
	}

	public long getLastDone() {
		return lastDone;
	}

	public void setLastDone(long lastDone) {
		this.lastDone = lastDone;
	}

	public BackupObject getInternalBO() {
		return internalBO;
	}

	public void setInternalBO(BackupObject internalBO) {
		this.internalBO = internalBO;
	}


}
