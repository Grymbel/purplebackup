package backupHIDS;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import zipper.SHA1;

public class HIDSObject {
	private String hash;
	private int relID;
	private String relDir;
	private String alertMessage;
	
	private boolean resolved;
	private String resolvedSTR;

	public HIDSObject(String hash, int relid, String relDir){
		this.hash=hash;
		this.relDir=relDir;
		this.relID=relid;
	}
	
	public HIDSObject(String hash, int relid, String relDir, String alertMessage, boolean resolved){
		this.hash=hash;
		this.relDir=relDir;
		this.relID=relid;
		this.alertMessage=alertMessage;
		
		this.resolved=resolved;
		if(this.resolved){
			this.setResolvedSTR("YES");
		}
		else{
			this.setResolvedSTR("NO");
		}
	}
	
	public void CompareToCurr(){
		File toCompare = new File("src/output/"+relID+"/"+relDir+"/"+relDir+".zip");
		if(toCompare.exists()){
			try {
				String enemyHash = SHA1.sha1(toCompare);
				if(!enemyHash.equals(this.hash)){
					alertMessage = "Backup Modified! "+toCompare.toString();
					resolved=false;
				}
			} catch (NoSuchAlgorithmException e) {
				System.err.println("Comparing failed: "+e.getMessage());
				alertMessage = "Algorithm Error on: "+toCompare.toString();
			} catch (IOException e) {
				System.err.println("Comparing failed: "+e.getMessage());
				alertMessage = "IO Error on: "+toCompare.toString();
			}
		}else{
			alertMessage = "File Missing! "+toCompare.toString();
			resolved=false;
		}
	}
	
	public String toString(){
		return hash+";"+relID+";"+relDir+";"+alertMessage+";"+resolved+";";
	}
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getRelid() {
		return relID;
	}
	public void setRelid(int relid) {
		this.relID = relid;
	}
	public String getRelDir() {
		return relDir;
	}
	public void setRelDir(String relDir) {
		this.relDir = relDir;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public int getRelID() {
		return relID;
	}

	public void setRelID(int relID) {
		this.relID = relID;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public String getResolvedSTR() {
		return resolvedSTR;
	}

	public void setResolvedSTR(String resolvedSTR) {
		this.resolvedSTR = resolvedSTR;
	}
}
