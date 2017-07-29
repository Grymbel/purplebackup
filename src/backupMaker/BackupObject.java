package backupMaker;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import database.DBConnect;
import zipper.Unzipper;
import zipper.Zipper;

public class BackupObject {
	private Boolean userBackup, cloudBackup, webBackup, auditBackup, isBase;
	private long creationDate;
	private String userBackupSTR, cloudBackupSTR, webBackupSTR, auditBackupSTR, creationDateSTR, isBaseSTR;
	
	private String userTarget,cloudTarget,webTarget,auditTarget;
	
	//Empty constructor 
	public BackupObject(){
		this.userBackup=false;
		this.cloudBackup=false;
		this.webBackup=false;
		this.auditBackup=false;
		this.isBase=false;
		
		this.creationDate = 0;
	}
	
	//Fills the display for the tables
	public BackupObject(boolean userBackupSTR, boolean cloudBackupSTR, boolean webBackupSTR, boolean auditBackupSTR, long dateOfC, boolean isBaseSTR){
	
		this.setUserBackup(userBackupSTR);
		this.setCloudBackup(cloudBackupSTR);
		this.setWebBackup(webBackupSTR);
		this.setAuditBackup(auditBackupSTR);
		this.setIsBase(isBaseSTR);
		this.setCreationDate(dateOfC);
		
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
	
	public String printer(){
		String toRet="";
		if(this.auditBackup){
			toRet=toRet+"Audit ";
		}
		if(this.cloudBackup){
			toRet=toRet+"Cloud ";
		}
		if(this.userBackup){
			toRet=toRet+"User ";
		}
		if(this.webBackup){
			toRet=toRet+"Web ";
		}
		return toRet;
	}
	//Starts the restoration of the supplied id
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
		ArrayList<String> areas = new ArrayList<String>();
		for(int i = rangeStart;i<=id;i++){
		File node = new File("src/output/"+i+"/");
		

			String[] subNote = node.list();
			for(String filename : subNote){
				dirList.add(new File(node, filename).toString());
				areas.add(filename);
			}
		}
		String restDir = "src/output/"+id+"RESTORE/";
		int c = 0;
		for(String str : dirList){
			try {				
				String archive = getArchive(new File(str));
				Unzipper unz = new Unzipper(archive,restDir+areas.get(c));
				c++;
				DBConnect dbc = new DBConnect();
				ResultSet res = dbc.getFileDeltaRange(rangeStart, id);
				while(res.next()){
					String action = res.getString("deltaAction");
					if(action.equals("DEL")){
						String toDel = res.getString("fileLine");
						File file = new File((restDir+toDel).replace("\\", "/"));
						file.delete();
					}
					else if(action.equals("UDT")){
						String toDel = res.getString("fileLine");
						File file = new File((restDir+toDel).replace("\\", "/"));
						file.delete();
					}
					else if(action.equals("ADD")){
					}
					else{
						System.out.println("Malformed delta command:"+action);
					}
				}
				unz.unZipIt();
		}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isFilled(){
		return this.auditBackup||this.cloudBackup||this.userBackup||this.webBackup;
	}
	//Makes a base for the incremental 
	public void makeBaseBackup(long time){
		DBConnect dbc = new DBConnect();
		try {
			dbc.resolveAll();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		dbc.close();
		LastDoneBackup ldb = new LastDoneBackup();
		int base = ldb.getLastID()+1;
		System.out.println("BASE "+base);
		//Zip all and put it in the output dir
		this.initBackupLocations();
		
		this.setAuditBackup(true);
		this.setCloudBackup(true);
		this.setUserBackup(true);
		this.setWebBackup(true);
		this.setIsBase(true);
		this.setCreationDate(time);
		
		BackupDAO.manualBackup(this);
		
		makeDir(base+"");
		makeDir(base+"/audit/");
		makeDir(base+"/web/");
		makeDir(base+"/user/");
		makeDir(base+"/cloud/");
		
		Zipper z1=new Zipper(auditTarget,"src/output/","/audit/","audit.zip",base,true);
		z1.zipUp();
		Zipper z2=new Zipper(webTarget,"src/output/","/web/","web.zip",base,true);
		z2.zipUp();
		Zipper z4=new Zipper(userTarget,"src/output/","/user/","user.zip",base,true);
		z4.zipUp();
		Zipper z5=new Zipper(cloudTarget,"src/output/","/cloud/","cloud.zip",base,true);
		z5.zipUp();
	}
	
	//For first-ever backup. Independent of other files
	public void makeBaseBackupFirst(long time){
		System.out.println("FIRST");
		DBConnect dbc = new DBConnect();
		try {
			dbc.resolveAll();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		dbc.close();
		//Zip all and put it in the output dir
		this.initBackupLocations();
		makeDir(0+"");
		makeDir(0+"/audit/");
		makeDir(0+"/web/");
		makeDir(0+"/user/");
		makeDir(0+"/cloud/");
		
		Zipper z1=new Zipper(auditTarget,"src/output/","/audit/","audit.zip",0,true);
		z1.zipUp();
		Zipper z2=new Zipper(webTarget,"src/output/","/web/","web.zip",0,true);
		z2.zipUp();
		Zipper z4=new Zipper(userTarget,"src/output/","/user/","user.zip",0,true);
		z4.zipUp();
		Zipper z5=new Zipper(cloudTarget,"src/output/","/cloud/","cloud.zip",0,true);
		z5.zipUp();
	}
	
	//Makes manual backup of the object. Takes in time to standardise
	public void makeManualBackup(long time){
		LastDoneBackup ldb = new LastDoneBackup();
		int id = ldb.getLastID()+1;
		System.out.println("MANUAL FOR: " +id);
		
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
	}
	
	public String toString(){
		String toRet="";
		toRet=toRet+this.userBackup+";";
		toRet=toRet+this.cloudBackup+";";
		toRet=toRet+this.webBackup+";";
		toRet=toRet+this.auditBackup+";";
		toRet=toRet+this.getCreationDate()+";";
		toRet=toRet+this.isBase;
		
		toRet=toRet.replace("true", "1");
		toRet=toRet.replace("false", "0");
		
		return toRet;
	}
	
	//Loads locations
	public void initBackupLocations(){
		BackupDAO backs = new BackupDAO();
		String[] targets = new String[4];
		targets=backs.getTargetDirs();
		
		this.auditTarget=targets[0];
		this.cloudTarget=targets[1];
		this.userTarget=targets[2];
		this.webTarget=targets[3];
	}
	
	//Finds and returns the archive in the dir
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
