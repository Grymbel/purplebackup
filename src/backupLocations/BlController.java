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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class BlController{
	private File auditURL;
	private File cloudURL;
	private File userURL;
	private File webURL;

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
		    private JFXTextArea taAudit;

		    @FXML
		    private JFXTextArea taCloud;

		    @FXML
		    private JFXTextArea taWeb;

		    @FXML
		    private JFXTextArea taUser;

		    @FXML
		    private JFXButton btnFindAudit;

		    @FXML
		    private JFXButton btnFindCloud;

		    @FXML
		    private JFXButton btnFindMessage;

		    @FXML
		    private JFXButton btnDoFindUser;

		    @FXML
		    private JFXButton btnBack;

		    @FXML
		    private JFXButton btnAccept;


		private boolean openClose = false;

		public void initialise(){
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
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			taAudit.setText(auditURL.toString());
			taCloud.setText(cloudURL.toString());
			taUser.setText(userURL.toString());
			taWeb.setText(webURL.toString());
			
			dbc.close();
		}
		@FXML
	    void doAccept(ActionEvent event) {
			if(
			nonNullCheck(auditURL)||
			nonNullCheck(webURL)||
			nonNullCheck(cloudURL)||
			nonNullCheck(userURL)){
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Null values");
				alert.setContentText("Spaces cannot be left null");
	
				alert.showAndWait();
			}
			
			if(nonDupeCheck()){
				DBConnect dbc = new DBConnect();
				try {
					dbc.setFileLocation("audit", auditURL.toString());
					dbc.setFileLocation("user", userURL.toString());
					dbc.setFileLocation("web", webURL.toString());
					dbc.setFileLocation("cloud", cloudURL.toString());
					
					Alert alert = new Alert(AlertType.ERROR);
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
	    void doFindAudit(ActionEvent event) {
	    	File auditTest = getDir();
	    	this.auditURL = auditTest;
	    	taAudit.setText(auditTest.toString());
	    }

	    @FXML
	    void doFindCloud(ActionEvent event) {
	    	File cloudTest = getDir();
	    	this.cloudURL = cloudTest;
	    	taCloud.setText(cloudTest.toString());
	    }

	    @FXML
	    void doFindWeb(ActionEvent event) {
	    	File webTest = getDir();
	    	this.webURL=webTest;
	    	taWeb.setText(webTest.toString());
	    }

	    @FXML
	    void doFindUser(ActionEvent event) {
	    	File userTest = getDir();
	    	this.userURL=userTest;
	    	taUser.setText(userTest.toString());
	    }

	    @FXML
	    void gotoBack(ActionEvent event) {

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
			else if (event.getSource().equals(backupItem)) {
				backupItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(settingsItem)) {
				settingsItem.setStyle("-fx-background-color: #673AB7");
			}
			else if (event.getSource().equals(logoutItem)) {
				logoutItem.setStyle("-fx-background-color: #673AB7");
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
			else if (event.getSource().equals(backupItem)) {
				backupItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(settingsItem)) {
				settingsItem.setStyle("-fx-background-color: #9575CD");
			}
			else if (event.getSource().equals(logoutItem)) {
				logoutItem.setStyle("-fx-background-color: #9575CD");
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
				root = FXMLLoader.load(getClass().getResource("../view/Backup Scheduler.fxml"));
			}
			else if (event.getSource().equals(blItem)) {
				root = FXMLLoader.load(getClass().getResource("../view/BackupLocations.fxml"));
			}
			//XZ's Feature
			
			else if (event.getSource().equals(bmItem)) {
				root = FXMLLoader.load(getClass().getResource("../view/BackupMaker.fxml"));
			}
			else if (event.getSource().equals(settingsItem)) {
				root = FXMLLoader.load(getClass().getResource("../view/"));
			}
			else if (event.getSource().equals(logoutItem)) {
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