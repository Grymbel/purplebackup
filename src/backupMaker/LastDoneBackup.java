package backupMaker;

import java.util.ArrayList;
import java.util.Scanner;

public class LastDoneBackup {
	private int baseID;
	private int lastID;
	private long lastTime;
	
	public LastDoneBackup(){
		LastDoneBackupDAO ldbdao = new LastDoneBackupDAO();
		Scanner sc =new Scanner(ldbdao.ldbCheck());
		sc.useDelimiter(";");
		
		this.lastID=Integer.parseInt(sc.next());
		this.lastTime=Long.parseLong(sc.next());
		this.baseID=Integer.parseInt(sc.next());
		sc.close();
	}

	public void updateBackup(long time){
		System.out.println("uBackup");
		this.lastTime = time;
		
		LastDoneBackupDAO ldbdao = new LastDoneBackupDAO();
		this.lastID+=1;
		System.out.println(this.lastID);
		ldbdao.updateLDB(this.lastID, this.lastTime, this.baseID);
	}
	
	public void updateBase(long time){
		System.out.println("uBase");
		this.lastTime = time;
		
		LastDoneBackupDAO ldbdao = new LastDoneBackupDAO();
		this.lastID+=1;
		System.out.println(this.lastID);
		ldbdao.updateLDB(this.lastID, this.lastTime, this.lastID);
	}
	
	public ArrayList<Integer> getBases(){
		LastDoneBackupDAO ldd= new LastDoneBackupDAO();
		return ldd.getBases();
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
