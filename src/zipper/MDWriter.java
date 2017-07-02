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
		this.setDestinationDelta(destinationDirFull+"delta.data");
		this.setDestinationFL(destinationDirFull+"meta.data");
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
		if(destinationDirFull!=null&&filesAcquired!=null&&filesAcquired!=null){	
			try {
				FileWriter fw = new FileWriter(destinationFL);
				for(int y=0;y<filesAcquired.size();y++){
				fw.append(filesAcquired.get(y)+"><"+filesDigest.get(y)+"><\n");
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
			if(this.isBase==true){
				for(int r=0;r<filesAcquired.size();r++){
					fw.append("ADD"+"><"+filesAcquired.get(r)+"><"+filesDigest.get(r)+"><\n");
					}
			}
			else{
				System.out.println("Is not base");
				ArrayList<String> filesAcCurr = filesAcquired;
				ArrayList<String> filesDiCurr = filesDigest;
				
				ArrayList<String> filesAcPast = new ArrayList<String>();
				ArrayList<String> filesDiPast = new ArrayList<String>();
				
				FileReader frP1 = new FileReader(destinationDir+(backupID-1)+"/"+lowerDir+"/meta.data");
				
				Scanner scP1 = new Scanner(frP1);
				
				while(scP1.hasNextLine()){
					Scanner scP2 = new Scanner(scP1.nextLine());
					scP2.useDelimiter("><");
					try{
						String one = scP2.next();
						String two = scP2.next();
						if(!(one==""||one==null||two==""||two==null)){
					filesAcPast.add(one);
					filesDiPast.add(two);
						}
					}catch(java.util.NoSuchElementException e){
						
					}
					scP2.close();
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
								fw.append("UDT><"+filesAcCurr.get(ii)+"><"+filesDiCurr.get(ii)+"><\n");
								found=true;
								System.out.println("Hash Mismatch");
							}
							
						}
						if(found==true){
							System.out.println("found==true");
							break;
						}
					}
					if(found==false){
						//No matches found
						fw.append("ADD><"+filesAcCurr.get(ii)+"><"+filesDiCurr.get(ii)+"><\n");
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
						fw.append("DEL><"+filesAcPast.get(y)+"><"+filesDiPast.get(y)+"><\n");
					}
				}
				
				scP1.close();
				}
			fw.close();
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

	public String getLowerDir() {
		return lowerDir;
	}

	public void setLowerDir(String lowerDir) {
		this.lowerDir = lowerDir;
	}

	
}
