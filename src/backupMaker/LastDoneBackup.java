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
	public void updateBackup(long time){
		this.lastTime = time;
		this.lastID+=1;
		
		LastDoneBackupDAO ldbdao = new LastDoneBackupDAO();
		ldbdao.updateLDB(lastID, lastTime, baseID);
	}
}
