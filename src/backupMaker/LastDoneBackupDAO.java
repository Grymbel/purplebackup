package backupMaker;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LastDoneBackupDAO {
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
					
					outFile.append("00000000;");
					time=System.currentTimeMillis();
					outFile.append(time+"");
					//Backup All
					outFile.append("00000000;");
					outFile.close();
					
					return "00000000;"+time+";"+"00000000;";
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
