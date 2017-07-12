package backupMaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import zipper.Unzipper;
import zipper.Zipper;

public class BackupObject {
	private Boolean userBackup, cloudBackup, webBackup, auditBackup, messageBackup, isBase;
	private long creationDate;
	private String userBackupSTR, cloudBackupSTR, webBackupSTR, auditBackupSTR, messageBackupSTR, creationDateSTR, isBaseSTR;
	
	private String userTarget,cloudTarget,webTarget,auditTarget,messageTarget;
	
	public BackupObject(){
		this.userBackup=false;
		this.cloudBackup=false;
		this.webBackup=false;
		this.auditBackup=false;
		this.messageBackup=false;
		this.isBase=false;
		
		this.creationDate = 0;
	}
	
	public BackupObject(boolean userBackupSTR, boolean cloudBackupSTR, boolean webBackupSTR, boolean auditBackupSTR, boolean messageBackupSTR, long dateOfC, boolean isBaseSTR){
	
		if(userBackupSTR==true){
			this.setUserBackupSTR("X");
		}else{
			this.setUserBackupSTR("");
		}
		
		if(cloudBackupSTR==true){
			this.setCloudBackupSTR("X");
		}else{
			this.setCloudBackupSTR("");
		}
		
		if(webBackupSTR==true){
			this.setWebBackupSTR("X");
		}else{
			this.setWebBackupSTR("");
		}
		
		if(auditBackupSTR==true){
			this.setAuditBackupSTR("X");
		}else{
			this.setAuditBackupSTR("");
		}
		
		if(messageBackupSTR==true){
			this.setMessageBackupSTR("X");
		}else{
			this.setMessageBackupSTR("");
		}
		if(isBaseSTR==true){
			this.setIsBaseSTR("X");
		}else{
			this.setIsBaseSTR("");
		}

		SimpleDateFormat sdf = new SimpleDateFormat();
		this.creationDateSTR = sdf.format(dateOfC);
	}
	
	public static void makeDir(String dirName){
		BackupDAO.makeDir(dirName);
	}
	
	public void restore(int id){
		System.out.println("RESTORING " + id);
		LastDoneBackup ldb = new LastDoneBackup();
		ArrayList<String> dirList = new ArrayList<String>();
		ArrayList<Integer> baseList = ldb.getBases();
		Collections.sort(baseList);
		
		int rangeStart=0;
		
		for(Integer in : baseList){
			if(in<=id){
				
			}
			else{
				rangeStart=in;
				break;
			}
		}
		for(int i = rangeStart;i<=id;i++){
		File node = new File("src/output/"+i+"/");
		

			String[] subNote = node.list();
			for(String filename : subNote){
				dirList.add(new File(node, filename).toString());
			}
		}
		
		String restDir = "src/output/"+id+"RESTORE/";
		for(String str : dirList){
			try {
				String archive = getArchive(new File(str));
				System.out.println(archive);
				Unzipper unz = new Unzipper(archive,restDir);
				FileReader fr = new FileReader(str+"/delta.data");
				Scanner sc = new Scanner(fr);
				
				
				sc.useDelimiter("><");
				while(sc.hasNextLine()){
					String action = sc.next().replace("\n", "");
					action.replace(" ", "");
					if(action.equals("DEL")){
						String toDel = sc.next();
						File file = new File((restDir+toDel).replace("\\", "/"));
						file.delete();
						sc.next();
					}
					else if(action.equals("UDT")){
						String toDel = sc.next();
						File file = new File((restDir+toDel).replace("\\", "/"));
						file.delete();
						sc.next();
					}
					else if(action.equals("ADD")){
						sc.next();
						sc.next();
					}
					else{
						System.out.println("Malformed delta command:"+action);
					}
				}
				unz.unZipIt();
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void makeBaseBackup(long time){
		LastDoneBackup ldb = new LastDoneBackup();
		int base = ldb.getLastID()+1;
		System.out.println("BASE "+base);
		//Zip all and put it in the output dir
		this.initBackupLocations();
		
		this.setAuditBackup(true);
		this.setCloudBackup(true);
		this.setMessageBackup(true);
		this.setUserBackup(true);
		this.setWebBackup(true);
		this.setIsBase(true);
		this.setCreationDate(time);
		
		BackupDAO.manualBackup(this);
		
		makeDir(base+"");
		makeDir(base+"/audit/");
		makeDir(base+"/web/");
		makeDir(base+"/message/");
		makeDir(base+"/user/");
		makeDir(base+"/cloud/");
		
		Zipper z1=new Zipper(auditTarget,"src/output/","/audit/","audit.zip",base,true);
		z1.zipUp();
		Zipper z2=new Zipper(webTarget,"src/output/","/web/","web.zip",base,true);
		z2.zipUp();
		Zipper z3=new Zipper(messageTarget,"src/output/","/message/","message.zip",base,true);
		z3.zipUp();
		Zipper z4=new Zipper(userTarget,"src/output/","/user/","user.zip",base,true);
		z4.zipUp();
		Zipper z5=new Zipper(cloudTarget,"src/output/","/cloud/","cloud.zip",base,true);
		z5.zipUp();
		
		ldb.updateBase(time);
	}
	
	public void makeBaseBackupFirst(String baseID, long time){
		System.out.println("FIRST");
		//Zip all and put it in the output dir
		this.initBackupLocations();
		makeDir(baseID+"");
		makeDir(baseID+"/audit/");
		makeDir(baseID+"/web/");
		makeDir(baseID+"/message/");
		makeDir(baseID+"/user/");
		makeDir(baseID+"/cloud/");
		
		Zipper z1=new Zipper(auditTarget,"src/output/","/audit/","audit.zip",Integer.parseInt(baseID),true);
		z1.zipUp();
		Zipper z2=new Zipper(webTarget,"src/output/","/web/","web.zip",Integer.parseInt(baseID),true);
		z2.zipUp();
		Zipper z3=new Zipper(messageTarget,"src/output/","/message/","message.zip",Integer.parseInt(baseID),true);
		z3.zipUp();
		Zipper z4=new Zipper(userTarget,"src/output/","/user/","user.zip",Integer.parseInt(baseID),true);
		z4.zipUp();
		Zipper z5=new Zipper(cloudTarget,"src/output/","/cloud/","cloud.zip",Integer.parseInt(baseID),true);
		z5.zipUp();
		
		this.setAuditBackup(true);
		this.setCloudBackup(true);
		this.setMessageBackup(true);
		this.setUserBackup(true);
		this.setWebBackup(true);
		this.setIsBase(true);
		this.setCreationDate(time);
		
		BackupDAO.manualBackup(this);
	}
	
	public void makeManualBackup(long time){
		LastDoneBackup ldb = new LastDoneBackup();
		int id = ldb.getLastID()+1;
		System.out.println("MANUAL");
		
		this.initBackupLocations();
		BackupDAO.manualBackup(this);
		System.out.println(id);
		makeDir(id+"");
		
		if(this.auditBackup==true){
		makeDir(id+"/audit/");
		Zipper z1=new Zipper(auditTarget,"src/output/","/audit/","audit.zip",id);
		z1.zipUp();
		}
		if(this.webBackup==true){
		makeDir(id+"/web/");
		Zipper z2=new Zipper(webTarget,"src/output/","/web/","web.zip",id);
		z2.zipUp();
		}
		if(this.messageBackup==true){
		makeDir(id+"/message/");
		Zipper z3=new Zipper(messageTarget,"src/output/","/message/","message.zip",id);
		z3.zipUp();
		}
		if(this.userBackup==true){
		makeDir(id+"/user/");
		Zipper z4=new Zipper(userTarget,"src/output/","/user/","user.zip",id);
		z4.zipUp();
		}
		if(this.cloudBackup==true){
		makeDir(id+"/cloud/");
		Zipper z5=new Zipper(cloudTarget,"src/output/","/cloud/","cloud.zip",id);
		z5.zipUp();
		}
		
		ldb.updateBackup(time);
	}
	
	public String toString(){
		String toRet="";
		toRet=toRet+this.userBackup+";";
		toRet=toRet+this.cloudBackup+";";
		toRet=toRet+this.webBackup+";";
		toRet=toRet+this.auditBackup+";";
		toRet=toRet+this.messageBackup+";";
		
		toRet=toRet+this.getCreationDate()+";";
		toRet=toRet+this.isBase;
		
		toRet=toRet.replace("true", "1");
		toRet=toRet.replace("false", "0");
		
		return toRet;
	}
	
	public void initBackupLocations(){
		BackupDAO backs = new BackupDAO();
		String[] targets = new String[5];
		targets=backs.getTargetDirs();
		
		this.auditTarget=targets[0];
		this.cloudTarget=targets[1];
		this.messageTarget=targets[2];
		this.userTarget=targets[3];
		this.webTarget=targets[4];
	}
	
	public String getArchive(File node){
		File[] files = node.listFiles();
		String toRet="";
		
		for(File f:files){
			System.out.println(f.toString().substring(f.toString().replace(" ","").length()-4,(int)f.toString().replace(" ","").length()).equals(".zip"));
			if(f.toString().substring(f.toString().replace(" ","").length()-4,(int)f.toString().replace(" ","").length()).equals(".zip")){
			return f.toString();
			}
		}
		return toRet;
	    }
	    
	//String Get/Set
	public String getUserBackupSTR() {
		return userBackupSTR;
	}
	public void setUserBackupSTR(String userBackupSTR) {
		this.userBackupSTR = userBackupSTR;
	}
	public String getCloudBackupSTR() {
		return cloudBackupSTR;
	}
	public void setCloudBackupSTR(String cloudBackupSTR) {
		this.cloudBackupSTR = cloudBackupSTR;
	}
	public String getWebBackupSTR() {
		return webBackupSTR;
	}
	public void setWebBackupSTR(String webBackupSTR) {
		this.webBackupSTR = webBackupSTR;
	}
	public String getAuditBackupSTR() {
		return auditBackupSTR;
	}
	public void setAuditBackupSTR(String auditBackupSTR) {
		this.auditBackupSTR = auditBackupSTR;
	}
	public String getMessageBackupSTR() {
		return messageBackupSTR;
	}
	public void setMessageBackupSTR(String messageBackupSTR) {
		this.messageBackupSTR = messageBackupSTR;
	}
	public String getCreationDateSTR() {
		return creationDateSTR;
	}
	public void setCreationDateSTR(String creationDateSTR) {
		this.creationDateSTR = creationDateSTR;
	}
	
	//Default Get/Set
	public Boolean getUserBackup() {
		return userBackup;
	}
	public void setUserBackup(Boolean userBackup) {
		this.userBackup = userBackup;
	}
	public Boolean getCloudBackup() {
		return cloudBackup;
	}
	public void setCloudBackup(Boolean cloudBackup) {
		this.cloudBackup = cloudBackup;
	}
	public Boolean getWebBackup() {
		return webBackup;
	}
	public void setWebBackup(Boolean webBackup) {
		this.webBackup = webBackup;
	}
	public Boolean getAuditBackup() {
		return auditBackup;
	}
	public void setAuditBackup(Boolean auditBackup) {
		this.auditBackup = auditBackup;
	}
	public Boolean getMessageBackup() {
		return messageBackup;
	}
	public void setMessageBackup(Boolean messageBackup) {
		this.messageBackup = messageBackup;
	}
	public long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
	public String getUserTarget() {
		return userTarget;
	}
	public void setUserTarget(String userTarget) {
		this.userTarget = userTarget;
	}
	public String getCloudTarget() {
		return cloudTarget;
	}
	public void setCloudTarget(String cloudTarget) {
		this.cloudTarget = cloudTarget;
	}
	public String getWebTarget() {
		return webTarget;
	}
	public void setWebTarget(String webTarget) {
		this.webTarget = webTarget;
	}
	public String getAuditTarget() {
		return auditTarget;
	}
	public void setAuditTarget(String auditTarget) {
		this.auditTarget = auditTarget;
	}
	public String getMessageTarget() {
		return messageTarget;
	}
	public void setMessageTarget(String messageTarget) {
		this.messageTarget = messageTarget;
	}
	public Boolean getIsBase() {
		return isBase;
	}
	public void setIsBase(Boolean isBase) {
		this.isBase = isBase;
	}
	public String getIsBaseSTR() {
		return isBaseSTR;
	}
	public void setIsBaseSTR(String isBaseSTR) {
		this.isBaseSTR = isBaseSTR;
	}
	
	
	
	
	
	
	
	
}
