package login;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BadTyping {
	public static void logTypo(){
		try {
			FileWriter fw = new FileWriter("ButterFingers",true);
			fw.write(System.currentTimeMillis()+";");
			fw.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	public static void clearTypo(){
		try {
			FileWriter fw = new FileWriter("ButterFingers");
			fw.write("");
			fw.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	public static int getPenalty(){
		ArrayList<Long> times = new ArrayList<Long>();
		try {
			FileReader fr = new FileReader("ButterFingers");
			Scanner sc = new Scanner(fr);
			sc.useDelimiter(";");
			
			while(sc.hasNext()){
				times.add(sc.nextLong());
			}
			sc.close();
			if(times.size()<=3){
				return 0;
			}
			else if(times.size()==4){
				int toRet=(int) ((times.get(3)+(30*1000)-System.currentTimeMillis())/1000);
				if(toRet>0){
					return toRet;
				}
				else{
					return 0;
				}
			}
			else{
				int toRet=(int) ((times.get(times.size()-1)+(60*1000)-System.currentTimeMillis())/1000);
				if(toRet>0){
				return toRet;
				}
				else{
					return 0;
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		return 0;
		}
	}
