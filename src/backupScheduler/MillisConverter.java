package backupScheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

//A class for all my time conversion needs
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
		sdf.setTimeZone(tz);
		Date date = new Date();
		
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	public static long getDaysToLong(int days){
		return days *3600 *24 * 1000;
	}
	
	public static String getStringFromLong(long l){
		Date d = new Date(l);
		return d.toString();
	}
	
	public static long getTimeDifference(){
		long toRet = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
		sdf.setTimeZone(tz);
		Date date = new Date();
		
		try {
			date = sdf.parse("2017-07-25");
		} catch (ParseException e) {
		}
		
		toRet = (System.currentTimeMillis()-date.getTime())/3600000;
		return toRet;
	}
}
