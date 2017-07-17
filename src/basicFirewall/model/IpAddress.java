package basicFirewall.model;

import java.io.IOException;
import java.util.ArrayList;

import basicFirewall.dao.WhiteBlackListDAO;

public class IpAddress {
	String ipAddress;

	public IpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	static public ArrayList<IpAddress> getWhiteList() throws IOException{
		WhiteBlackListDAO dao = new WhiteBlackListDAO();
		ArrayList<IpAddress> ipArray = new ArrayList<IpAddress>();
		for(String s:dao.getWhiteListArray()){
			IpAddress ip = new IpAddress(s);
			ipArray.add(ip);
		}
		return ipArray;
	}
	
	static public ArrayList<IpAddress> getBlackList() throws IOException{
		WhiteBlackListDAO dao = new WhiteBlackListDAO();
		ArrayList<IpAddress> ipArray = new ArrayList<IpAddress>();
		for(String s:dao.getBlackListArray()){
			IpAddress ip = new IpAddress(s);
			ipArray.add(ip);
		}
		return ipArray;
	}
	
	static public void writeToFile(String s) throws IOException{
		WhiteBlackListDAO dao = new WhiteBlackListDAO();
		dao.writeToFile(s);
		dao.commitRule();
	}
}
