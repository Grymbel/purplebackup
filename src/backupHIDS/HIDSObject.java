package backupHIDS;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import zipper.SHA1;

public class HIDSObject {
	private String hash;
	private int relID;
	private String relDir;
	
	public HIDSObject(String hash, int relid, String relDir){
		this.hash=hash;
		this.relDir=relDir;
		this.relID=relid;
	}
	
	public String CompareToCurr(){
		String toRet="";
		File toCompare = new File("src/output/"+relID+"/"+relDir+"/"+relDir+".zip");
		if(toCompare.exists()){
			try {
				String enemyHash = SHA1.sha1(toCompare);
				if(!enemyHash.equals(this.hash)){
					toRet= "Backup Modified! "+toCompare.toString();
				}
			} catch (NoSuchAlgorithmException e) {
				System.err.println("Comparing failed: "+e.getMessage());
				toRet= "Algorithm Error on: "+toCompare.toString();
			} catch (IOException e) {
				System.err.println("Comparing failed: "+e.getMessage());
				toRet= "IO Error on: "+toCompare.toString();
			}
		}else{
			toRet= "File Missing! "+toCompare.toString();
		}
		return toRet;
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
}
