package basicFirewall.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import userManagement.dao.DatabaseDAO;

public class WhiteBlackListDAO {
	final private String filePath = "src/basicFirewall/WhiteBlackList.txt";
	final private String fileCommitPath = "C:/Users/Wei Xuan/workspace/Servers/Tomcat v9.0 Server at localhost-config/server.xml";
	final private String fileCommitPath2 = "C:/Apache24/conf/whiteblacklist/whitelist.conf";
	private File file;
	
	public WhiteBlackListDAO() throws IOException{
		file = new File(filePath);
	}
	
	public ArrayList<String> getBlackListArray() throws FileNotFoundException{
		ArrayList<String> blackListArray = new ArrayList<String>();
		Scanner sc = new Scanner(file);
		sc.useDelimiter(",");
		if(sc.hasNext()){
			String line = sc.next();
			blackListArray.add(line);
		}
		sc.close();
		return blackListArray;
	}
	
	public void writeToFile(String text) throws IOException{
		FileWriter fw = new FileWriter(file);
		fw.write(text); 
		fw.flush();
		fw.close();
	}
	
	/*
	public void commitRule() throws IOException{
		File file2 = new File(fileCommitPath);
		ArrayList<String> lines = new ArrayList<String>(Files.readAllLines(file2.toPath()));
		String combineLine = "\t\t<Valve className=\"org.apache.catalina.valves.RemoteAddrValve\" deny=\"";
		ArrayList<String> blackList = getBlackListArray();
		if(blackList.size() > 0){
			for(int i=0; i < blackList.size(); i++){
				if(i == 0)
					combineLine += blackList.get(i);
				else
					combineLine += "|" + blackList.get(i);
			}
		}
		combineLine += "\" />";
		lines.set(150, combineLine);
		Files.write(file2.toPath(), lines);
		
		File file3 = new File(fileCommitPath2);
		ArrayList<String> lines2 = new ArrayList<String>(Files.readAllLines(file3.toPath()));
		String combineLine2 = "\tSecRule REMOTE_ADDR \"@ipMatch ";
		ArrayList<String> whiteList = getWhiteListArray();
		if(whiteList.size() > 0){
			for(int i=0; i < whiteList.size(); i++){
				if(i == 0)
					combineLine2 += whiteList.get(i);
				else
					combineLine2 += "," + whiteList.get(i);
			}
		}
		combineLine2 += "\" phase:1,nolog,allow,ctl:ruleEngine=Off,id:999945";
		lines2.set(1, combineLine2);
		Files.write(file3.toPath(), lines2);
	}*/
	
	public void commitRule() throws ClassNotFoundException, SQLException, FileNotFoundException{
		System.out.println("Commit Starting...");
		DatabaseDAO dao = new DatabaseDAO(0);
		ArrayList<String> as = new ArrayList<String>();
		for(basicFirewall.model.IpAddress a:dao.getDatabaseBlacklist()){ //as (Database)
			as.add(a.getIpAddress());
		}
		ArrayList<String> blackList = getBlackListArray();
		if(as.size() == blackList.size()){
			System.out.println("Nothing Changed...");
			for(int i = 0; i < blackList.size(); i++){
				String sql = "UPDATE Blacklist SET IpAddress = '" + blackList.get(i) + "' WHERE Blacklist.ID = " + (++i) +";";
				dao.updateDatabaseData(sql);
			}
		}else{
			int number = as.size() - blackList.size(); 
			if(number < 1){ 										//-1 = 1 - 2
				System.out.println("New IP Address Added...");
				for(int i = 0; i < blackList.size() + number; i++){ //i = 0; i < 1; i++
					String sql = "UPDATE Blacklist SET IpAddress = '" + blackList.get(i) + "' WHERE Blacklist.ID = " + (++i) +";";
					dao.updateDatabaseData(sql);
				}
				for(int i = blackList.size() + number; i < blackList.size(); i++){ //i = 1; i < 2; i++
					String sql = "INSERT INTO Blacklist(ID, IpAddress) VALUES (" + (i + 1) + ", \"" + blackList.get(i) + "\");";
					dao.updateDatabaseData(sql);
				}
			}
			else{													// 3 = 4 - 1
				System.out.println("IP Address Deleted...");
				for(int i = 0; i < blackList.size(); i++){	//i = 0; i < 1; i++
					String sql = "UPDATE Blacklist SET IpAddress = '" + blackList.get(i) + "' WHERE Blacklist.ID = " + (++i) +";";
					dao.updateDatabaseData(sql);
				}
				for(int i = blackList.size(); i < as.size(); i++){ //i = 1; i < 4; i++
					String sql = "DELETE FROM BlackList WHERE Blacklist.ID = " + (i + 1) + ";";
					dao.updateDatabaseData(sql);
				}
			}
		}
		System.out.println("Commit Ending...");
		dao.close();
	}
}
