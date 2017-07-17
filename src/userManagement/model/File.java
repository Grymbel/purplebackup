package userManagement.model;

public class File {
	int fileID;
	int UserID;
	String fileName;
	int fileSize;
	byte[] fileData;
	
	public File(int fileID, String fileName, int fileSize, byte[] fileData, User user) {
		this.fileID = fileID;
		UserID = user.getUserID();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileData = fileData;
	}

	public int getFileID() {
		return fileID;
	}
	public int getUserID() {
		return UserID;
	}
	public String getFileName() {
		return fileName;
	}
	public int getFileSize() {
		return fileSize;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
}
