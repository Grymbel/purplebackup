package backupScheduler;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class MillisConverter {

	public static String getDaysHours(long millis){
		String toRet="";
		int days = (int) (millis / (1000*60*60*24));
		
		toRet=toRet+days+"d ";
		
		int hours = (int) (millis / (1000*60*60)%24);
		
		toRet=toRet+hours+"h";
		return toRet;
	}
	
	public static String getDays(long millis){
		String toRet="";
		int days = (int) (millis / (1000*60*60*24));
		
		toRet=toRet+days+"d ";

		return toRet;
	}
	
	public static String getHoursMinutes(long millis){
		String toRet = "";
		int hours = (int) (millis / (1000*60*60)%24);
		
		toRet=toRet+hours+"h ";
		
		int minutes = (int) (millis / (1000*60)%60);
		
		toRet=toRet+minutes+"m";
		return toRet;
	}
	
	public static long getLongTime(String s){
		long toRet = 0;
		Scanner sc = new Scanner(s);
		sc.useDelimiter(":");
		
		toRet += (Integer.parseInt(sc.next())*60*60*1000);
		
		toRet += (Integer.parseInt(sc.next())*60*1000);
		
		sc.close();
		return toRet;
	}
	
	public static long getLongDate(String s){
		Scanner sc = new Scanner(s);
		sc.useDelimiter("-");
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(sc.nextInt(), sc.nextInt(), sc.nextInt(),0 ,0);	
	
		sc.close();
		return gc.getTimeInMillis();
	}
	
	public static long getDaysToLong(int days){
		return days *3600 *24 * 1000;
	}
	
	public static String getStringFromLong(long l){
		Date d = new Date(l);
		return d.toString();
	}
}
