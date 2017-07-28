package backupHIDS;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnect;
import zipper.SHA1;

public class HIDSService {
	private ArrayList<String> preHashes;
	private ArrayList<String> currHashes;
	
	public HIDSService(){
		this.preHashes = new ArrayList<String>();
		this.currHashes = new ArrayList<String>();
		
		DBConnect dbc = new DBConnect();
		
		try {
			ResultSet res = dbc.getHashList();
			while(res.next()){
				preHashes.add(res.getString("sha1hash"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		generateFileList(new File("src/output/"));
	}
	
	private void generateFileList(File node){
		if(node.isFile()){
			try {
				currHashes.add(SHA1.sha1(node));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<String> getCurrHashes() {
		return currHashes;
	}

	public void setCurrHashes(ArrayList<String> currHashes) {
		this.currHashes = currHashes;
	}

	public ArrayList<String> getPreHashes() {
		return preHashes;
	}

	public void setPreHashes(ArrayList<String> preHashes) {
		this.preHashes = preHashes;
	}
}
