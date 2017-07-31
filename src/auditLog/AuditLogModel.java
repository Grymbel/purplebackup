package auditLog;

import java.util.ArrayList;

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
	
}
