package backupMaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BackupDAO {
	
	public BackupDAO(){
	}
	public ArrayList<String> getExistingBackups(){
		ArrayList<String> existing = new ArrayList<String>();
		try {
			FileReader fr = new FileReader("src/output/backupLog.txt");
			
			Scanner sc = new Scanner(fr);
			sc.useDelimiter(";");
			
			while(sc.hasNextLine()){
				existing.add(sc.nextLine());
			}
			
			sc.close();
			return existing;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void manualBackup(BackupObject bcko){
		try {
			FileWriter fw = new FileWriter("src/output/backupLog.txt",true);
			
			fw.append(bcko.toString());
			fw.append("\n");
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void makeDir(String dirName){
		File file = new File("src/output/"+dirName);
		file.mkdir();
	}
	
	public String[] getTargetDirs(){
		String[] toRet=new String[5];
		String buffer;
		try {
			FileReader fr = new FileReader("src/output/targetLocations.txt");
			
			Scanner sc = new Scanner(fr);
			sc.useDelimiter(";");
			int c=0;
			while(sc.hasNextLine()){
				buffer=sc.nextLine();
				toRet[c]=buffer.replace(";", "");
				c++;
			}
			
			sc.close();
			return toRet;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return toRet;
	}
}
