package backupMaker;

import java.text.SimpleDateFormat;

import zipper.Zipper;

public class BackupObject {
	private Boolean userBackup, cloudBackup, webBackup, auditBackup, messageBackup;
	private long creationDate;
	private String userBackupSTR, cloudBackupSTR, webBackupSTR, auditBackupSTR, messageBackupSTR, creationDateSTR;
	
	private String userTarget,cloudTarget,webTarget,auditTarget,messageTarget;
	
	public BackupObject(){
		this.userBackup=false;
		this.cloudBackup=false;
		this.webBackup=false;
		this.auditBackup=false;
		this.messageBackup=false;
		
		this.creationDate = 0;
	}
	public BackupObject(boolean user, boolean cloud, boolean web, boolean audit, boolean message){
		this.userBackup=user;
		this.cloudBackup=cloud;
		this.webBackup=web;
		this.auditBackup=audit;
		this.messageBackup=message;
		
		this.creationDate = System.currentTimeMillis();
	}
	
	public BackupObject(boolean userBackupSTR, boolean cloudBackupSTR, boolean webBackupSTR, boolean auditBackupSTR, boolean messageBackupSTR, long dateOfC){
	
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

		SimpleDateFormat sdf = new SimpleDateFormat();
		this.creationDateSTR = sdf.format(dateOfC);
	}
	
	public static void makeDir(String dirName){
		BackupDAO.makeDir(dirName);
	}
	
	public void makeBaseBackup(String baseID, long time){
		//Zip all and put it in the output dir
		Zipper z1=new Zipper(auditTarget,"src/output/"+baseID+"/audit/audit.zip");
		z1.zipUp();
		Zipper z2=new Zipper(webTarget,"src/output/"+baseID+"/web/web.zip");
		z2.zipUp();
		Zipper z3=new Zipper(messageTarget,"src/output/"+baseID+"/message/message.zip");
		z3.zipUp();
		Zipper z4=new Zipper(userTarget,"src/output/"+baseID+"/user/user.zip");
		z4.zipUp();
		Zipper z5=new Zipper(cloudTarget,"src/output/"+baseID+"/cloud/cloud.zip");
		z5.zipUp();
		
		BackupObject bco = new BackupObject();
		bco.setAuditBackup(true);
		bco.setCloudBackup(true);
		bco.setMessageBackup(true);
		bco.setUserBackup(true);
		bco.setWebBackup(true);
		
		bco.setCreationDate(time);
		
		BackupDAO.manualBackup(bco);
	}
	public String toString(){
		String toRet="";
		toRet=toRet+this.userBackup+";";
		toRet=toRet+this.cloudBackup+";";
		toRet=toRet+this.webBackup+";";
		toRet=toRet+this.auditBackup+";";
		toRet=toRet+this.messageBackup+";";
		
		toRet=toRet.replace("true", "1");
		toRet=toRet.replace("false", "0");
		
		toRet=toRet+this.getCreationDate()+";";
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
	
	
	
	
	
	
	
	
}
