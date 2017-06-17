package backupMaker;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter outFile = new PrintWriter(bw);
					
					outFile.append("0;");
					time=System.currentTimeMillis();
					outFile.append(time+"");
					outFile.append("0;");
					outFile.close();
					
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
				String toRet=sc.nextLine();
				sc.close();
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
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter outFile = new PrintWriter(bw);
			
			outFile.append(newLastID+";"+newTime+";"+newBaseID+";");
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
