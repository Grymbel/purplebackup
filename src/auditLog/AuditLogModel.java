package auditLog;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class AuditLogModel {
	private String dateTime;
	private String ipAddress;
	private String username;
	private String activity;
	
	public AuditLogModel() {
		super();
	}

	public AuditLogModel(String dateTime, String ipAddress, String username, String activity) {
		super();
		this.dateTime = dateTime;
		this.ipAddress = ipAddress;
		this.username = username;
		this.activity = activity;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	@Override
	public String toString() {
		return dateTime + ";" + ipAddress + ";" + username + ";" + activity; 
	}
	
	public static ArrayList<AuditLogModel> getAllData() {
		AuditLogDAO logDAO = new AuditLogDAO();
		return logDAO.getAllData();
	}
	
	public void getData() {
		AuditLogDAO logDAO = new AuditLogDAO();
		AuditLogModel aLM = logDAO.getData(dateTime);
		setIpAddress(aLM.getIpAddress());
		setUsername(aLM.getUsername());
		setActivity(aLM.getActivity());
	}
	
	public void hackTooltipStartTiming(Tooltip tooltip) {
	    try {
	        Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
	        fieldBehavior.setAccessible(true);
	        Object objBehavior = fieldBehavior.get(tooltip);

	        Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
	        fieldTimer.setAccessible(true);
	        Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(new Duration(150)));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String [] args) throws ParseException {
		/*
		ArrayList<AuditLogModel> dataList = AuditLogModel.getAllData();
		Collections.reverse(dataList);
		for (AuditLogModel aLM : dataList) {
			String dateTime = aLM.getDateTime();
			System.out.println(dateTime);
		}
		*/
		
		String d1 = "01:00 am";
		Date time1 = new SimpleDateFormat("hh:mm a").parse(d1);
		Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(time1);
	    Date a = calendar1.getTime();
	    
	    String d2 = "06:00 am";
		Date time2 = new SimpleDateFormat("hh:mm a").parse(d2);
		Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(time2);
	    Date b = calendar2.getTime();
	    
	    String d3 = "9:01 am";
		Date time3 = new SimpleDateFormat("hh:mm a").parse(d3);
		Calendar calendar3 = Calendar.getInstance();
	    calendar3.setTime(time3);
	    
	    Date x = calendar3.getTime();
	    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
	    	//System.out.println("true");
	    }
	    else {
	    	//System.out.println("false");
	    }
	    
	    ArrayList<AuditLogModel> dataList = AuditLogModel.getAllData();
		Collections.reverse(dataList);
		String timeStr = null;
		int i = 1;
		int z = 2;
		for (AuditLogModel aLM : dataList) {
			aLM.getDateTime();
			timeStr = aLM.getDateTime().substring(11);
			Date d = new SimpleDateFormat("hh:mm a").parse(timeStr);
			Calendar c = Calendar.getInstance();
		    c.setTime(d);
		    Date y = c.getTime();
		    if (aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b)) {
		    	System.out.println("true");
		    	System.out.println(timeStr);
		    }
		}
	}
}
