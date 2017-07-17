package basicFirewall.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import basicFirewall.model.IpAddress;

public class BasicFirewallViewController {
    @FXML
    private JFXTextField whiteField;
    @FXML
    private JFXButton whiteAddBtn;
    @FXML
    private JFXButton whiteDeleteBtn;
    @FXML
    private JFXListView<Label> whiteListView;
    @FXML
    private JFXTextField blackField;
    @FXML
    private JFXButton blackAddBtn;
    @FXML
    private JFXButton BlackDeleteBtn;
    @FXML
    private JFXListView<Label> blackListView;
    
    public void initialize() throws IOException{
    	System.out.println("Initialized");
    	for(IpAddress i:IpAddress.getWhiteList()){
    		//System.out.println("White: " + i);
			Label ipAddressLbl = new Label(i.getIpAddress());
			ipAddressLbl.setMinWidth(500);
			ipAddressLbl.setPrefWidth(500);
	
			whiteListView.getItems().add(ipAddressLbl);
    	}
    	
    	for(IpAddress i:IpAddress.getBlackList()){
    		//System.out.println("Black: " + i);
			Label ipAddressLbl = new Label(i.getIpAddress());
			ipAddressLbl.setMinWidth(500);
			ipAddressLbl.setPrefWidth(500);
	
			blackListView.getItems().add(ipAddressLbl);
    	}
    }
    
    @FXML
    void addBlack(ActionEvent event) throws IOException {
    	String ipLine = blackField.getText();
    	if(!ipLine.isEmpty() && ipLine != ""){
    		IpAddress ip = new IpAddress(ipLine);
	    	Label ipAddressLbl = new Label(ip.getIpAddress());
			ipAddressLbl.setMinWidth(500);
			ipAddressLbl.setPrefWidth(500);
	    	blackListView.getItems().add(0, ipAddressLbl);
    	}
    	blackField.clear();
    	updateFile();
    }

    @FXML
    void addWhite(ActionEvent event) throws IOException {
    	String ipLine = whiteField.getText();
    	if(!ipLine.isEmpty() && ipLine != ""){
	    	Label ipAddressLbl = new Label(ipLine);
			ipAddressLbl.setMinWidth(500);
			ipAddressLbl.setPrefWidth(500);
	    	whiteListView.getItems().add(0, ipAddressLbl);
    	}
    	whiteField.clear();
    	updateFile();
    }

    @FXML
    void deleteBlack(ActionEvent event) throws IOException {
    	int selectedIndex = blackListView.getSelectionModel().getSelectedIndex();
    	blackListView.getItems().remove(selectedIndex);
    	updateFile();
    }

    @FXML
    void deleteWhite(ActionEvent event) throws IOException {
    	int selectedIndex = whiteListView.getSelectionModel().getSelectedIndex();
    	whiteListView.getItems().remove(selectedIndex);
    	updateFile();
    }
    
	void updateFile() throws IOException{
		ArrayList<Label> whiteList = new ArrayList<Label>(whiteListView.getItems());
    	ArrayList<Label> blackList = new ArrayList<Label>(blackListView.getItems());
    	String writeToFileLine = "";
    	if(whiteList.isEmpty() || blackList.isEmpty()){
    		if(whiteList.isEmpty() && blackList.isEmpty()){
    			
    		}else if(whiteList.isEmpty()){
    			for(int i = 0; i < blackList.size(); i++){
    				if(i == 0)
    					writeToFileLine += "," + blackList.get(i).getText();
    				else
    					writeToFileLine += ">" + blackList.get(i).getText();
    			}
    		}else{
    			for(int i = 0; i < whiteList.size(); i++){
        			if(i == 0)
        				writeToFileLine += whiteList.get(i).getText();
        			else
        				writeToFileLine += ">" + whiteList.get(i).getText();
        		}
    		}
    	}else{
    		for(int i = 0; i < whiteList.size(); i++){
    			if(i == 0)
    				writeToFileLine += whiteList.get(i).getText();
    			else
    				writeToFileLine += ">" + whiteList.get(i).getText();
    		}
    		for(int i = 0; i < blackList.size(); i++){
				if(i == 0)
					writeToFileLine += "," + blackList.get(i).getText();
				else
					writeToFileLine += ">" + blackList.get(i).getText();
			}
    	}
		
		IpAddress.writeToFile(writeToFileLine);
    }
	
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

	private boolean openClose = false;

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
			root = FXMLLoader.load(getClass().getResource("../../view/DatabaseTableView.fxml"));
		}
		else if (event.getSource().equals(auditItem)) {
			root = FXMLLoader.load(getClass().getResource("../../view/AuditLog.fxml"));
		}
		
		//XZ's Feature
		
		else if (event.getSource().equals(backupItem)) {
			root = FXMLLoader.load(getClass().getResource("../../view/BackupMaker.fxml"));
		}
		else if (event.getSource().equals(settingsItem)) {
			root = FXMLLoader.load(getClass().getResource("../../view/"));
		}
		else if (event.getSource().equals(logoutItem)) {
			stage.setX(450);
			stage.setY(128);
			stage.setWidth(1020);
			stage.setHeight(650);
			
			root = FXMLLoader.load(getClass().getResource("../../view/Login.fxml"));
			stage.setMaximized(false);
		}
		stage.setScene(new Scene(root));
 	    stage.show();
	}
}
