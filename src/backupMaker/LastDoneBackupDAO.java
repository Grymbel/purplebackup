package backupMaker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
		long time=System.currentTimeMillis();
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

					BackupObject bcko = new BackupObject();
					bcko.makeBaseBackupFirst("0",time);
					
					sc.close();
					return "0;"+time+";"+"0;";
				} catch (IOException e) {
					e.printStackTrace();
				}
				sc.close();
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
	
	public ArrayList<Integer> getBases(){
		ArrayList<Integer> bases = new ArrayList<Integer>();
		try {
			FileReader fr = new FileReader("src/output/backupLog.txt");
			Scanner sc = new Scanner(fr);
			sc.useDelimiter(";");
			int counter = 0;
			while(sc.hasNextLine()){
				String tg = sc.nextLine();
				Scanner sc2 = new Scanner(tg);
				sc2.useDelimiter(";");
				for(int i=0;i<5;i++){
				sc2.next();
				}
				if(sc2.next().equals("1")){
				bases.add(counter);
				}
				counter++;
				sc2.close();
			}
			sc.close();
			System.out.println(bases);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bases;
	}
}
