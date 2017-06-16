package backupMaker;

import java.text.SimpleDateFormat;

public class BackupObject {
	private Boolean userBackup, cloudBackup, webBackup, auditBackup, messageBackup;
	private long creationDate;
	private String userBackupSTR, cloudBackupSTR, webBackupSTR, auditBackupSTR, messageBackupSTR, creationDateSTR;
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
	
	
	
	
	
	
	
}
