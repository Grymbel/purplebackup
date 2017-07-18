package userManagement.model;

import java.sql.Date;

public class File {
	int fileID;
	int UserID;
	String fileName;
	int fileSize;
	byte[] fileData;
	String recipient;
	Date date;
	
	
	public File(int fileID, User user, String fileName, int fileSize, byte[] fileData,String recipient, Date date) {
		this.fileID = fileID;
		UserID = user.getUserID();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileData = fileData;
		this.recipient = recipient;
		this.date = date;
	}

	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
