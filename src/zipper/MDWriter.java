package zipper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MDWriter {

	private String destinationDir;
	private String destinationFL;
	private String destinationDelta;
	private ArrayList<String> filesAcquired;
	private int backupID;
	
	public MDWriter(ArrayList<String> fileList, String destination, int backupID){
		this.setDestination(destination);
		this.setFilesAcquired(fileList);
		this.setBackupID(backupID);
		this.setDestinationDelta(destinationDir+"delta.data");
		this.setDestinationFL(destinationDir+"meta.data");
	}
	
	public boolean writeMD(){
		if(destinationDir!=null&&filesAcquired!=null){	
			try {
				FileWriter fw = new FileWriter(destinationFL);
				for(String s:filesAcquired){
				fw.append(s+"*"+"\n");
				}
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
			if(backupID==0){
				for(String s:filesAcquired){
					fw.append("ADD"+"*"+s+"*"+"\n");
					}
					fw.close();
			}
			else{/*
				FileReader fr = new FileReader((destinationDir+"").replace(backupID+"",(backupID-1)+""));
				Scanner sc = new Scanner(fr);
				sc.useDelimiter("*");
				while(sc.hasNextLine()){
					
				}
				sc.close();
				*/
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDestination() {
		return destinationDir;
	}

	public void setDestination(String destination) {
		this.destinationDir = destination;
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
	public String getDestinationDir() {
		return destinationDir;
	}

	public void setDestinationDir(String destinationDir) {
		this.destinationDir = destinationDir;
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

	
}
