package backupMaker;

import java.util.Scanner;

public class LastDoneBackup {
	private int baseID;
	private int lastID;
	private long lastTime;
	
	public LastDoneBackup(){
		LastDoneBackupDAO ldbdao = new LastDoneBackupDAO();
		Scanner sc =new Scanner(ldbdao.ldbCheck());
		sc.useDelimiter(";");
		
		this.lastID=sc.nextInt();
		this.lastTime=sc.nextLong();
		this.baseID=sc.nextInt();
		sc.close();
	}
	public LastDoneBackup(boolean todo){
		LastDoneBackupDAO ldbdao = new LastDoneBackupDAO();
		Scanner sc =new Scanner(ldbdao.ldbRead());
		sc.useDelimiter(";");
		
		this.lastID=sc.nextInt();
		this.lastTime=sc.nextLong();
		this.baseID=sc.nextInt();
		sc.close();
	}
	public void updateBackup(long time){
		this.lastTime = time;
		this.lastID+=1;
		
		LastDoneBackupDAO ldbdao = new LastDoneBackupDAO();
		ldbdao.updateLDB(lastID, lastTime, baseID);
	}
	
	public int getBaseID() {
		return baseID;
	}
	public void setBaseID(int baseID) {
		this.baseID = baseID;
	}
	public int getLastID() {
		return lastID;
	}
	public void setLastID(int lastID) {
		this.lastID = lastID;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
}
