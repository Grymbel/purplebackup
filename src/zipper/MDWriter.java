package zipper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import database.DBConnect;

public class MDWriter {

	private String destinationFL;
	private String destinationDelta;
	private String destinationDir;
	private String destinationDirFull;
	private String lowerDir;
	private ArrayList<String> filesAcquired;
	private ArrayList<String> filesDigest;
	private int backupID;
	private boolean isBase;
	
	
	public MDWriter(ArrayList<String> fileList, ArrayList<String> digestList, String destinationDirFull, String destinationDir, int backupID,boolean isBase){
		this.setDestinationDirFull(destinationDirFull);
		this.setDestinationDir(destinationDir);
		this.setFilesAcquired(fileList);
		this.setFilesDigest(digestList);
		this.setBackupID(backupID);
		this.setIsBase(isBase);
		
		Scanner sc = new Scanner(destinationDirFull);
		sc.useDelimiter("/");
		ArrayList<String> breakDown = new ArrayList<String>();
		while(sc.hasNext()){
			breakDown.add(sc.next());
		}
		this.setLowerDir(breakDown.get(breakDown.size()-1));
		sc.close();
	}

	public boolean writeMD(){
		if(filesAcquired!=null&&filesDigest!=null){	
				DBConnect dbc = new DBConnect();
				for(int y=0;y<filesAcquired.size();y++){
					try {
						dbc.addFileIdx(this.backupID,filesAcquired.get(y),filesDigest.get(y), lowerDir);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			return true;
		}
		else{
			System.out.println("Instance has no valid input");
			return false; 
		}
	}
	
	public void writeDelta(){
		if(this.isBase==true){
			DBConnect dbc = new DBConnect();
			for(int r=0;r<filesAcquired.size();r++){
				try {
					dbc.addFileDelta(this.backupID, "ADD", filesAcquired.get(r), filesDigest.get(r),lowerDir);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				}
		}
		else{
			System.out.println("Is not base");
			
			DBConnect dbc = new DBConnect();
			
			ArrayList<String> filesAcCurr = filesAcquired;
			ArrayList<String> filesDiCurr = filesDigest;
			
			ArrayList<String> filesAcPast = new ArrayList<String>();
			ArrayList<String> filesDiPast = new ArrayList<String>();
			
			
			
			if(backupID!=0){
					ResultSet res;
					try {
						res = dbc.getFileIdx(this.backupID-1,lowerDir);
					
					while(res.next()){
						filesAcPast.add(res.getString("fileLine"));
						filesDiPast.add(res.getString("fileDigest"));
					}
					
			//Check New Against Old
			for(int ii=0;ii<filesAcCurr.size();ii++){
				boolean found=false;
				for(int oo=0;oo<filesAcPast.size();oo++){
					if(filesAcPast.get(oo).equals(filesAcCurr.get(ii))){
						if(filesDiPast.get(oo).equals(filesDiCurr.get(ii))&&found==false){
							//Total Match
							found=true;
							System.out.println("Total Match");
						}
						else{
							//Hash mismatch
							dbc.addFileDelta(this.backupID, "UDT", filesAcCurr.get(ii), filesDiCurr.get(ii),lowerDir);
							found=true;
							System.out.println("Hash Mismatch");
						}
						
					}
					if(found==true){
						break;
					}
				}
				if(found==false){
					//No matches found
					dbc.addFileDelta(this.backupID, "ADD", filesAcCurr.get(ii), filesDiCurr.get(ii),lowerDir);
					System.out.println("No matches");
				}
			}

			for(int y=0;y<filesAcPast.size();y++){
				boolean found=false;
				for(int u=0;u<filesAcCurr.size();u++){
					if(filesAcPast.get(y).equals(filesAcCurr.get(u))){
						found=true;
						break;
					}
				}
				if(found==false){
					dbc.addFileDelta(this.backupID, "DEL", filesAcPast.get(y), filesDiPast.get(y),lowerDir);
				}
			}
					} catch (SQLException e) {
						e.printStackTrace();
					}
			
			}

			}
		}
	
	public List<String> getFilesAcquired() {
		return filesAcquired;
	}

	public void setFilesAcquired(ArrayList<String> fileList) {
		this.filesAcquired = fileList;
	}

	public int getBackupID() {
		return backupID;
	}

	public void setBackupID(int backupID) {
		this.backupID = backupID;
	}

	public String getDestinationFL() {
		return destinationFL;
	}

	public void setDestinationFL(String destinationFL) {
		this.destinationFL = destinationFL;
	}

	public String getDestinationDelta() {
		return destinationDelta;
	}

	public void setDestinationDelta(String destinationDelta) {
		this.destinationDelta = destinationDelta;
	}

	public ArrayList<String> getFilesDigest() {
		return filesDigest;
	}

	public void setFilesDigest(ArrayList<String> filesDigest) {
		this.filesDigest = filesDigest;
	}

	public boolean getIsBase() {
		return isBase;
	}

	public void setIsBase(boolean isBase) {
		this.isBase = isBase;
	}

	public String getLowerDir() {
		return lowerDir;
	}

	public void setLowerDir(String lowerDir) {
		this.lowerDir = lowerDir;
	}

	public String getDestinationDir() {
		return destinationDir;
	}

	public void setDestinationDir(String destinationDir) {
		this.destinationDir = destinationDir;
	}

	public String getDestinationDirFull() {
		return destinationDirFull;
	}

	public void setDestinationDirFull(String destinationDirFull) {
		this.destinationDirFull = destinationDirFull;
	}

	
}
