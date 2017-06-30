package zipper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MDWriter {

	private String destinationDir;
	private String destinationDirFull;
	private String destinationFL;
	private String destinationDelta;
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
		this.setDestinationDelta(destinationDir+"delta.data");
		this.setDestinationFL(destinationDir+"meta.data");
		this.setIsBase(isBase);
	}

	public boolean writeMD(){
		if(destinationDirFull!=null&&filesAcquired!=null&&filesAcquired!=null){	
			try {
				FileWriter fw = new FileWriter(destinationFL);
				for(int y=0;y<filesAcquired.size();y++){
				fw.append(filesAcquired.get(y)+"*"+filesDigest.get(y)+"\n");
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		else{
			System.out.println("Instance has no valid input");
			return false; 
		}
	}
	
	public void writeDelta(){
		try {
			FileWriter fw = new FileWriter(destinationDelta);
			System.out.println(destinationDelta);
			if(this.isBase==true){
				for(int r=0;r<filesAcquired.size();r++){
					fw.append("ADD"+"*"+filesAcquired.get(r)+"*"+filesDigest.get(r)+"\n");
					}
					fw.close();
			}
			else{
				ArrayList<String> filesAcCurr = filesAcquired;
				ArrayList<String> filesDiCurr = filesDigest;
				
				ArrayList<String> filesAcPast = new ArrayList<String>();
				ArrayList<String> filesDiPast = new ArrayList<String>();
				
				FileReader frP1 = new FileReader(destinationDir+(backupID-1)+"meta.data");
				
				Scanner scP1 = new Scanner(frP1);
				scP1.useDelimiter("*");
				
				while(scP1.hasNext()){
					filesAcPast.add(scP1.next());
					filesDiPast.add(scP1.next());
				}
				
				//Check New Against Old
				for(int ii=0;ii<filesAcCurr.size();ii++){
					for(int oo=0;oo<filesAcPast.size();oo++){
						if(filesAcPast.get(oo).equals(filesAcCurr.get(ii))){
							if(filesDiPast.get(oo).equals(filesAcPast.get(ii))){
								//Total Match
							}
							else{
								//Hash mismatch
								fw.append("UDT*"+filesAcCurr.get(ii)+"*"+filesDiCurr+"\n");
							}
							
						}
						else{
							//No matches found
							fw.append("ADD*"+filesAcCurr.get(ii)+"*"+filesDiCurr.get(oo));
						}
					}
				}
				
				for(int y=0;y<filesAcPast.size();y++){
					for(int u=0;u<filesAcCurr.size();u++){
						
					}
				}
				
				scP1.close();
				}
		} catch (IOException e) {
			e.printStackTrace();
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
	public String getDestinationDirFull() {
		return destinationDirFull;
	}

	public void setDestinationDirFull(String destinationDir) {
		this.destinationDirFull = destinationDir;
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

	public String getDestinationDir() {
		return destinationDir;
	}

	public void setDestinationDir(String destinationDir) {
		this.destinationDir = destinationDir;
	}

	
}
