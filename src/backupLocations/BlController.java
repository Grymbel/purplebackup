package backupLocations;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import backupScheduler.TimerAccess;
import database.DBConnect;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import zipper.AESThing;
import zipper.DBLocker;
import zipper.KeyReader;
import zipper.SHA1;

public class BlController{
	private File auditURL;
	private File cloudURL;
	private File userURL;
	private File webURL;
	private File restoreURL;

	private Window scene;
	 	@FXML
		private TextFlow sideIcon;
		@FXML
		private VBox sidebarNav;
		@FXML
		private HBox userItem;
		@FXML
		private HBox auditItem;
		@FXML
		private HBox backupItem;
		@FXML
		private HBox settingsItem;
		@FXML
		private HBox logoutItem;
		@FXML
		private HBox bsItem;
		@FXML
		private HBox bmItem;
		@FXML
		private HBox blItem;
		@FXML
		private HBox hidsItem;
		@FXML
		private HBox firewallItem;
		  @FXML
		    private JFXTextArea taAudit;

		    @FXML
		    private JFXTextArea taCloud;

		    @FXML
		    private JFXTextArea taWeb;

		    @FXML
		    private JFXTextArea taUser;
		    
		    @FXML
		    private JFXTextArea taRestore;

		    @FXML
		    private JFXButton btnFindAudit;

		    @FXML
		    private JFXButton btnFindCloud;

		    @FXML
		    private JFXButton btnFindMessage;

		    @FXML
		    private JFXButton btnDoFindUser;
		    
		    @FXML
		    private JFXButton btnDoFindRestore;

		    @FXML
		    private JFXButton btnAccept;
		    
		    @FXML
		    private JFXButton btnRecrypt;


		private boolean openClose = false;

		public void initialize(){
			DBConnect dbc = new DBConnect();
			try {
				ResultSet res = dbc.getFileLocation();
				while(res.next()){
				String test = res.getString("relDir");
				if(test.equals("audit")){
					this.auditURL=new File(res.getString("target"));
				}
				if(test.equals("web")){
					this.webURL=new File(res.getString("target"));
				}
				if(test.equals("user")){
					this.userURL=new File(res.getString("target"));
				}
				if(test.equals("cloud")){
					this.cloudURL=new File(res.getString("target"));
				}
				if(test.equals("restore")){
					this.restoreURL=new File(res.getString("target"));
				}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			taAudit.setText(auditURL.toString());
			taCloud.setText(cloudURL.toString());
			taUser.setText(userURL.toString());
			taWeb.setText(webURL.toString());
			taRestore.setText(restoreURL.toString());
			
			dbc.close();
		}
		
		@FXML
		void doSetRestore(ActionEvent event){
			if(nonNullCheck(restoreURL)){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Null values");
				alert.setContentText("Spaces and file locations cannot be left empty");
	
				alert.showAndWait();
			}
			else{
				DBConnect dbc = new DBConnect();
				try {
					dbc.setFileLocation("restore", restoreURL.toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		@FXML
	    void doAccept(ActionEvent event) {
			if(
			nonNullCheck(auditURL)||
			nonNullCheck(webURL)||
			nonNullCheck(cloudURL)||
			nonNullCheck(userURL)){
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Null values");
				alert.setContentText("Spaces and file locations cannot be left empty");
	
				alert.showAndWait();
			}
			
			if(nonDupeCheck()){
				DBConnect dbc = new DBConnect();
				try {
					dbc.setFileLocation("audit", auditURL.toString());
					dbc.setFileLocation("user", userURL.toString());
					dbc.setFileLocation("web", webURL.toString());
					dbc.setFileLocation("cloud", cloudURL.toString());
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("Success");
					alert.setContentText("Success");
		
					alert.showAndWait();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Duplicates");
				alert.setContentText("Duplicates are not allowed");
	
				alert.showAndWait();
			}
	    }

		@FXML
		void doRecrypt(ActionEvent event){
			Alert alert = new Alert(AlertType.CONFIRMATION, "Recrypting backups encrypts backups with a different random key for security purposes. Proceed?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				try {
					AESThing aes = new AESThing();
					DBConnect dbc = new DBConnect();
					ResultSet res = dbc.getHIDSData();

					ResultSet res2 = dbc.getHIDSData();
					while(res.next()){
						String todo = "src/output/"+res.getInt("relID")+"/"+res.getString("relDir")+"/"+res.getString("relDir")+".zip";
						System.out.println(todo);
						aes.decryptFile(new File(todo));
						aes.writeToFile(new File(todo));
					}
					
					KeyReader.genKey();
					AESThing aes2 = new AESThing();
					
					while(res2.next()){
						String todo = "src/output/"+res2.getInt("relID")+"/"+res2.getString("relDir")+"/"+res2.getString("relDir")+".zip";
						System.out.println("Encrypting "+todo.toString());
						aes2.encryptFile(new File(todo));
						dbc.updateHash(res2.getInt("relID"), res2.getString("relDir"), SHA1.sha1(new File(todo)));
					}
					
					
				} catch (Exception e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			}
			}
		
	    @FXML
	    void doFindAudit(ActionEvent event) {
	    	try{
	    	File auditTest = getDir();
	    	this.auditURL = auditTest;
	    	taAudit.setText(auditTest.toString());
	    	}catch(NullPointerException e){
	    		
	    	}
	    }

	    @FXML
	    void doFindCloud(ActionEvent event) {
	    	try{
	    	File cloudTest = getDir();
	    	this.cloudURL = cloudTest;
	    	taCloud.setText(cloudTest.toString());
	    }catch(NullPointerException e){
    		
    	}
	    }

	    @FXML
	    void doFindWeb(ActionEvent event) {
	    	try{
	    	File webTest = getDir();
	    	this.webURL=webTest;
	    	taWeb.setText(webTest.toString());
	    }catch(NullPointerException e){
    		
    	}
	    }

	    @FXML
	    void doFindUser(ActionEvent event) {
	    try{
	    	File userTest = getDir();
	    	this.userURL=userTest;
	    	taUser.setText(userTest.toString());
	    }catch(NullPointerException e){
    		
    	}
	    }
	    
	    @FXML
	    void doFindRestore(ActionEvent event) {
	    	try{
	    	File restoreTest = getDir();
	    	this.restoreURL=restoreTest;
	    	taRestore.setText(restoreTest.toString());
	    	}catch(NullPointerException e){
	    		
	    	}
	    }
	    
	    public File getDir(){
	    	DirectoryChooser chooser = new DirectoryChooser();
	    	chooser.setTitle("Select a directory");
	    	File selectedDirectory = chooser.showDialog(scene);
	    	
	    	return selectedDirectory;
	    }
	    
	    public boolean nonNullCheck(File toAuth){
	    	if(toAuth.list()!=null){
	    		return false;
	    	}
	    	else{
	    		return true;
	    	}
	    }
	    
	    public boolean nonDupeCheck(){
	    	ArrayList<String> urls = new ArrayList<String>();
	    	
	    	urls.add(userURL.toString());
	    	urls.add(cloudURL.toString());
	    	urls.add(webURL.toString());
	    	urls.add(auditURL.toString());
	    	
	    	Set<String> set = new HashSet<String>();
	    	set.addAll(urls);
	    	if(set.size() < urls.size()){
	    	    return false;
	    	}
	    	else{
	    		return true;
	    	}
	    }
		@FXML
		public void showSidebar(MouseEvent event) {
			if (openClose == false) {
				openClose = true;
				Timeline timeline = new Timeline();
				KeyValue sidebarNavValue = new KeyValue(sidebarNav.layoutXProperty(), 0);
				
				KeyFrame keyFrame = new KeyFrame(Duration.millis(300), sidebarNavValue);
				
				timeline.getKeyFrames().addAll(keyFrame);
				timeline.play();
			}
			else {
				openClose = false;
				Timeline timeline = new Timeline();
				KeyValue sidebarNavValue = new KeyValue(sidebarNav.layoutXProperty(), -240);
				
				KeyFrame keyFrame = new KeyFrame(Duration.millis(300), sidebarNavValue);
				
				timeline.getKeyFrames().addAll(keyFrame);
				timeline.play();
			}
		}
		
		@FXML
		public void showHoverColor(MouseEvent event) {
			if (event.getSource().equals(userItem)) {
				userItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(auditItem)) {
				auditItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(bmItem)) {
				bmItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(bsItem)) {
				bsItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(blItem)) {
				blItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(hidsItem)) {
				hidsItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(settingsItem)) {
				settingsItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(logoutItem)) {
				logoutItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(firewallItem)) {
				firewallItem.setStyle("-fx-background-color: #673AB7");
			}
		}
		
		@FXML
		public void hideHoverColor(MouseEvent event) {
			if (event.getSource().equals(userItem)) {
				userItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(auditItem)) {
				auditItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(bmItem)) {
				bmItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(bsItem)) {
				bsItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(blItem)) {
				blItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(hidsItem)) {
				hidsItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(settingsItem)) {
				settingsItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(logoutItem)) {
				logoutItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(firewallItem)) {
				firewallItem.setStyle("-fx-background-color: #9575CD");
			}
		}
		
		@FXML
		public void changePage(MouseEvent event) throws IOException {
			Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			Parent root = null;
			if (event.getSource().equals(userItem)) {
				root = FXMLLoader.load(getClass().getResource("../userManagement/view/DatabaseTableView.fxml"));
			}
			else if (event.getSource().equals(auditItem)) {
				root = FXMLLoader.load(getClass().getResource("../view/AuditLog.fxml"));
			}
			else if (event.getSource().equals(bsItem)) {
				root = FXMLLoader.load(getClass().getResource("../view/BackupScheduler.fxml"));
			}
			else if (event.getSource().equals(blItem)) {
				root = FXMLLoader.load(getClass().getResource("../view/BackupLocations.fxml"));
			}
			else if (event.getSource().equals(hidsItem)) {
				root = FXMLLoader.load(getClass().getResource("../view/BackupHIDS.fxml"));
			}
			//XZ's Feature
			
			else if (event.getSource().equals(bmItem)) {
				root = FXMLLoader.load(getClass().getResource("../view/BackupMaker.fxml"));
			}
			else if (event.getSource().equals(settingsItem)) {
				root = FXMLLoader.load(getClass().getResource("../view/"));
			}
			else if (event.getSource().equals(logoutItem)) {
				DBLocker.lockDB();
				TimerAccess.closeTime();
				stage.setX(450);
				stage.setY(128);
				stage.setWidth(1020);
				stage.setHeight(650);
				
				root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
				stage.setMaximized(false);
			}
			stage.setScene(new Scene(root));
	 	    stage.show();
		}
}