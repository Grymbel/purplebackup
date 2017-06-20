package backupMaker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LastDoneBackupDAO {
	public String ldbRead(){
		try {
			FileReader fr = new FileReader("src/output/LDB.txt");
			Scanner sc = new Scanner(fr);
			sc.useDelimiter(";");
				String toRet=sc.nextLine();
				sc.close();
			sc.close();
			return toRet;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String ldbCheck(){
		long time=0;
		try {
			FileReader fr = new FileReader("src/output/backupLog.txt");
			Scanner sc = new Scanner(fr);
			sc.useDelimiter(";");
			if(sc.hasNextLine()==false){
				System.out.println("The LDB is empty. Writing new.");
				try {
					FileWriter fw = new FileWriter("src/output/LDB.txt");
					
					fw.append("0;");
					time=System.currentTimeMillis();
					fw.append(time+";");
					fw.append("0;");
					fw.close();
					
					BackupObject.makeDir("0");
					BackupObject.makeDir("0/audit/");
					BackupObject.makeDir("0/web/");
					BackupObject.makeDir("0/message/");
					BackupObject.makeDir("0/user/");
					BackupObject.makeDir("0/cloud/");
					
					BackupObject bcko = new BackupObject();
					bcko.initBackupLocations();
					bcko.makeBaseBackup("0",time);
					
					return "0;"+time+";"+"0;";
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			else{
				FileReader fr2 =new FileReader("src/output/LDB.txt");
				Scanner sc2 = new Scanner(fr2);
				String toRet=sc2.nextLine();
				sc.close();
				sc2.close();
				System.out.println("Check passed, returning : "+toRet);
				return toRet;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void updateLDB(int newLastID, long newTime, int newBaseID){
		try {
			FileWriter fw = new FileWriter("src/output/LDB.txt");

			fw.append(newLastID+";"+newTime+";"+newBaseID+";");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
