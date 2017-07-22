package backupScheduler;

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
}
