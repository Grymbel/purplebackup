package basicFirewall.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class WhiteBlackListDAO {
	final private String filePath = "src/basicFirewall/WhiteBlackList.txt";
	final private String fileCommitPath = "C:/Users/Wei Xuan/workspace/Servers/Tomcat v9.0 Server at localhost-config/server.xml";
	private File file;
	
	public WhiteBlackListDAO() throws IOException{
		file = new File(filePath);
	}
	
	public ArrayList<String> getWhiteListArray() throws FileNotFoundException{
		ArrayList<String> whiteListArray = new ArrayList<String>();
		Scanner sc = new Scanner(file);
		sc.useDelimiter(",");
		if(sc.hasNext()){
			String line = sc.next();
			Scanner sc2 = new Scanner(line);
			sc2.useDelimiter(">");
			while(sc2.hasNext()){
				String line2 = sc2.next(); 
				whiteListArray.add(line2);
			}
			sc2.close();
		}
		sc.close();
		return whiteListArray;
	}
	
	public ArrayList<String> getBlackListArray() throws FileNotFoundException{
		ArrayList<String> blackListArray = new ArrayList<String>();
		Scanner sc = new Scanner(file);
		sc.useDelimiter(",");
		if(sc.hasNext()){
			sc.next();
			if(sc.hasNext()){
				String line = sc.next();
				Scanner sc2 = new Scanner(line);
				sc2.useDelimiter(">");
				while(sc2.hasNext()){
					String line2 = sc2.next();
					blackListArray.add(line2);
				}
				sc2.close();
			}
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
	
	public void commitRule() throws IOException{
		File file2 = new File(fileCommitPath);
		ArrayList<String> lines = new ArrayList<String>(Files.readAllLines(file2.toPath()));
		String combineLine = "\t\t<Valve className=\"org.apache.catalina.valves.RemoteAddrValve\" deny=\"";
		ArrayList<String> blackList = getBlackListArray();
		ArrayList<String> whiteList = getWhiteListArray();
		if(blackList.size() > 0){
			for(int i=0; i < blackList.size(); i++){
				if(i == 0)
					combineLine += blackList.get(i);
				else
					combineLine += "|" + blackList.get(i);
			}
		}
		combineLine += "\" allow=\"";
		if(whiteList.size() > 0){
			for(int i=0; i < whiteList.size(); i++){
				if(i == 0)
					combineLine += whiteList.get(i);
				else
					combineLine += "|" + whiteList.get(i);
			}
		}
		combineLine += "\" />";
		lines.set(150, combineLine);
		Files.write(file2.toPath(), lines);
	}
}
