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
	
	private long nextInstance;
	
	private BackupObject internalBO;

	private String dayTimeSTR;
	private String intervalSTR;
	private String boSTR;
	private String nextInstanceSTR;
	
	//For new schedules
	public ScheduleObject(String name, int maxTimes, long dayTime, long interval, long startingDay, BackupObject bo){
		this.name=name;
		this.maxTimes=maxTimes;
		this.dayTime=dayTime;
		this.interval=interval;
		this.startingDay=startingDay;
		
		if(maxTimes!=-1){
			this.remainingTimes=maxTimes-timesDone;
			}
			else{
				this.remainingTimes=Integer.MAX_VALUE;
			}
		this.timesDone=0;
		
		this.internalBO=bo;
		
		this.boSTR=this.internalBO.printer();
		this.intervalSTR=MillisConverter.getDays(this.interval);
		this.dayTimeSTR=MillisConverter.getHoursMinutes(this.dayTime);
		
		this.nextInstance=this.startingDay+this.dayTime;
		
		System.out.println(this.startingDay+" "+this.dayTime);
		this.setNextInstanceSTR(MillisConverter.getStringFromLong(this.nextInstance));
	}
	
	//For imports
	public ScheduleObject(String name, int maxTimes, int timesDone, long dayTime, long interval, long startingDay, BackupObject bo){
		this.name=name;
		this.maxTimes=maxTimes;
		this.timesDone=timesDone;
		
		if(maxTimes!=-1){
		this.remainingTimes=maxTimes-timesDone;
		}
		else{
			this.remainingTimes=Integer.MAX_VALUE;
		}
		
		this.dayTime=dayTime;
		this.interval=interval;
		this.startingDay=startingDay;
		this.internalBO=bo;
		
		this.boSTR=this.internalBO.printer();
		this.intervalSTR=MillisConverter.getDays(this.interval);
		this.dayTimeSTR=MillisConverter.getHoursMinutes(this.dayTime);
		
		
		
		if(this.nextInstance!=0){
		this.nextInstanceSTR=MillisConverter.getStringFromLong(this.nextInstance);
		}
		else{
			this.nextInstanceSTR="Uncalculated";
		}
	}
	
	public ScheduleObject(){
		//Blank
	}
	
	public String toString(){
		String toRet ="[";
		toRet = toRet + this.maxTimes +"; " + this.remainingTimes +"; " + this.timesDone +"] ";
		
		toRet = toRet + "[" + this.dayTime +"; " + this.interval + "; " + this.startingDay +"; " + this.lastDone +"] ";
		
		toRet = toRet + "["+this.internalBO+"]";
		
		return toRet;
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
	
	public int getTimesDone() {
		return timesDone;
	}

	public void setTimesDone(int timesDone) {
		this.timesDone = timesDone;
	}

	public String getDayTimeSTR() {
		return dayTimeSTR;
	}

	public void setDayTimeSTR(String dayTimeSTR) {
		this.dayTimeSTR = dayTimeSTR;
	}

	public String getIntervalSTR() {
		return intervalSTR;
	}

	public void setIntervalSTR(String intervalSTR) {
		this.intervalSTR = intervalSTR;
	}

	public String getBoSTR() {
		return boSTR;
	}

	public void setBoSTR(String boSTR) {
		this.boSTR = boSTR;
	}

	public long getNextInstance() {
		return nextInstance;
	}

	public void setNextInstance(long nextInstance) {
		this.nextInstance = nextInstance;
	}

	public String getNextInstanceSTR() {
		return nextInstanceSTR;
	}

	public void setNextInstanceSTR(String nextInstanceSTR) {
		this.nextInstanceSTR = nextInstanceSTR;
	}



}
