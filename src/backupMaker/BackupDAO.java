package backupMaker;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BackupDAO {
	
	public BackupDAO(){
	}
	public ArrayList<String> getExistingBackups(){
		ArrayList<String> existing = new ArrayList<String>();
		String line2Add="";
		try {
			FileReader fr = new FileReader("src/output/backupLog.txt");
			
			Scanner sc = new Scanner(fr);
			sc.useDelimiter(";");
			
			while(sc.hasNextLine()){
				line2Add=sc.nextLine();
				existing.add(line2Add);
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
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter outFile = new PrintWriter(bw);

			outFile.append(bcko.toString()+"\n");
			outFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
