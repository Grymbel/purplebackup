package backupMaker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import database.DBConnect;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class PurpleBController {
	
	private BackupObject bo;

	private int noOfPages;
	private int pageNo;
	
	private ArrayList<BackupObject> allBackups;
	
	@FXML
	private JFXButton btnThing;
	
    @FXML
    private JFXButton btnAddBackup;

    @FXML
    private JFXButton btnSelMsg;

    @FXML
    private JFXButton btnSelAudit;

    @FXML
    private JFXButton btnSelUserData;

    @FXML
    private JFXButton btnSelCloud;

    @FXML
    private JFXButton btnSelWeb;
    
    @FXML
    private JFXButton btnScrollLeft;

    @FXML
    private JFXButton btnScrollRight;

    @FXML
    private TableView<BackupObject> bmtable;

    @FXML
    private TableColumn<BackupObject, String> colDate;

    @FXML
    private TableColumn<BackupObject, String> colUser;

    @FXML
    private TableColumn<BackupObject, String> colCloud;

    @FXML
    private TableColumn<BackupObject, String> colWeb;

    @FXML
    private TableColumn<BackupObject, String> colAudit;
    
    @FXML
    private TableColumn<BackupObject, String> colIsBase;

    @FXML
    private JFXButton btnSchedule;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnRestore;
    
    @FXML
    private JFXButton btnManual;

    @FXML
    private JFXCheckBox chbEnableBase;
    
    private Window scene;
    
    public void initialize(){
    	DBConnect dbc = new DBConnect();
    	dbc.close();
    	bo = new BackupObject();
    	allBackups=new ArrayList<BackupObject>();

    	btnScrollLeft.setVisible(false);
    	btnScrollRight.setVisible(false);
    	
			BackupDAO bdao = new BackupDAO();
			allBackups.addAll(bdao.getExistingBackups());
		ObservableList<BackupObject> data = bmtable.getItems();
		int todo;
		//For page formatting 
		if(allBackups.size()>10){
			todo=10;
		}
		else{
			todo=allBackups.size();
		}
		for(int i=0;i<todo;i++){
	    data.add(new BackupObject(allBackups.get(i).getUserBackup(),allBackups.get(i).getCloudBackup(),allBackups.get(i).getWebBackup(),allBackups.get(i).getAuditBackup(),allBackups.get(i).getCreationDate(),
	    		allBackups.get(i).getIsBase()));
		}
		
		this.noOfPages = (data.size()/10)+1;
		this.pageNo = 0;
		if(noOfPages>1){
			btnScrollLeft.setVisible(true);
			btnScrollRight.setVisible(true);
		}
    }
    
    @FXML
	protected void addBackupObject() {
		//Display object constructor for table
        ObservableList<BackupObject> data = bmtable.getItems();
        data.add(new BackupObject(bo.getUserBackup(),bo.getCloudBackup(),bo.getWebBackup(),bo.getAuditBackup(),bo.getCreationDate(),bo.getIsBase()));
    }
    
    @FXML
    void doAddBackup(ActionEvent event) {
    	long time =System.currentTimeMillis();
    	bo.initBackupLocations();
    	bo.setCreationDate(time);
    	//Sets the tables
    	if(!(bo.getAuditBackup()==false&&bo.getCloudBackup()==false&&bo.getWebBackup()==false&&bo.getUserBackup()==false)){
    		addBackupObject();
    	
    	if(bo.getIsBase()==true){
    		bo.makeBaseBackup(time);
    		}
    	else{
    		bo.makeManualBackup(time);
    		}
    	}
    	this.noOfPages = (allBackups.size()/10)+1;
		this.pageNo = 0;
		if(noOfPages>1){
			btnScrollLeft.setVisible(true);
			btnScrollRight.setVisible(true);
		}
    }

    @FXML
    void doRestore(ActionEvent event) {
    	if(bmtable.getSelectionModel().getSelectedIndex()>=0){
    	int selOnes = bmtable.getSelectionModel().getSelectedIndex();
    	int sel = (pageNo*10)+selOnes;
    	
    	BackupDAO bdao = new BackupDAO();
		allBackups.addAll(bdao.getExistingBackups());
    	System.out.println(allBackups.get(sel));
    	
    	allBackups.get(sel).restore(sel);
    	}
    }

    @FXML
    void doSelAudit(ActionEvent event) {
    	colorSwap(btnSelAudit);
    	if(bo.getAuditBackup()==false){
    	bo.setAuditBackup(true);
    	}
    	else{
    		bo.setAuditBackup(false);
    	}
    }

    @FXML
    void doSelCloud(ActionEvent event) {
    	colorSwap(btnSelCloud);
    	if(bo.getCloudBackup()==false){
        	bo.setCloudBackup(true);
        	}
        	else{
        		bo.setCloudBackup(false);
        	}
    }

    @FXML
    void doSelUserData(ActionEvent event) {
    	colorSwap(btnSelUserData);
    	if(bo.getUserBackup()==false){
        	bo.setUserBackup(true);
        	}
        	else{
        		bo.setUserBackup(false);
        	}
    }

    @FXML
    void doSelWeb(ActionEvent event) {
    	colorSwap(btnSelWeb);
    	if(bo.getWebBackup()==false){
        	bo.setWebBackup(true);
        	}
        	else{
        		bo.setWebBackup(false);
        	}
    }

    @FXML
    void doEnableBase(ActionEvent event){
    	ArrayList<JFXButton> buttons = new ArrayList<JFXButton>();
		buttons.add(btnSelAudit);
		buttons.add(btnSelCloud);
		buttons.add(btnSelMsg);
		buttons.add(btnSelUserData);
		buttons.add(btnSelWeb);
		
    	if(chbEnableBase.isSelected()){
    		bo.setIsBase(true);
    		
    		bo.setAuditBackup(true);
    		bo.setCloudBackup(true);
    		bo.setUserBackup(true);
    		bo.setWebBackup(true);
    		
    		for(JFXButton bt : buttons){
    			if(bt.getTextFill().equals(btnScrollLeft.getTextFill())){
    				colorSwap(bt);
    			}
    			else{
    				
    			}
    		}
    		
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Base Backup Selected");
    		alert.setHeaderText("Base backups are large!");
    		alert.setContentText("Base backups are used to start a new incremental chain. Use this wisely.");

    		alert.showAndWait();
    	}
    	else{
    		bo.setIsBase(false);
    		
    		bo.setAuditBackup(false);
    		bo.setCloudBackup(false);
    		bo.setUserBackup(false);
    		bo.setWebBackup(false);
    		
    		for(JFXButton bt : buttons){
    			if(!(bt.getTextFill().equals(btnScrollLeft.getTextFill()))){
    				colorSwap(bt);
    			}
    			else{
    				
    			}
    		}
    	}
    }
    
    @FXML
    void gotoBack(ActionEvent event) {
		try {
    	Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		Parent root;
			root = FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"));
		stage.setScene(new Scene(root,1920,1080));
 	    stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void gotoManual(ActionEvent event){
    	
    }
    
    @FXML
    void doScrollLeft(ActionEvent event){
    	int minBackups;
    	if((this.pageNo-1)>=0){
    	minBackups = (pageNo-1)*10;
    	ObservableList<BackupObject> data = bmtable.getItems();
		data.clear();
    	for(int y =minBackups;y<pageNo*10;y++){
    		data.add(new BackupObject(allBackups.get(y).getUserBackup(),allBackups.get(y).getCloudBackup(),allBackups.get(y).getWebBackup(),allBackups.get(y).getAuditBackup(),allBackups.get(y).getCreationDate(),allBackups.get(y).getIsBase()));
    	}
    	this.pageNo=this.pageNo-1;
    	}
    	else{

    	}
    }
    
    @FXML
    void doScrollRight(ActionEvent event){
    	int maxBackups = allBackups.size();
    	if(maxBackups>=noOfPages*10){
    	maxBackups = pageNo*10;
    	}
    	else{
    		maxBackups = allBackups.size();
    	}
    	if(maxBackups>=(pageNo+1)*10){
    		ObservableList<BackupObject> data = bmtable.getItems();
    		data.clear();
    	for(int y =(noOfPages-1)*10;y<maxBackups;y++){
    		data.add(new BackupObject(allBackups.get(y).getUserBackup(),allBackups.get(y).getCloudBackup(),allBackups.get(y).getWebBackup(),allBackups.get(y).getAuditBackup(),allBackups.get(y).getCreationDate(),allBackups.get(y).getIsBase()));
    	}
    	this.pageNo=this.pageNo+1;

    	}
    }
    
    @FXML
    public void openIt(ActionEvent event) {
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("JavaFX Projects");
    	File selectedDirectory = chooser.showDialog(scene);
    	
    	System.out.println(selectedDirectory);
    }
    
    //Looks good
    public void colorSwap(JFXButton jfxb){
    	Paint color1 = jfxb.getRipplerFill();
    	Paint color2 = jfxb.getTextFill();
    	if(jfxb.getTextFill()==color1){
    		jfxb.setTextFill(color2);
    		jfxb.setRipplerFill(color1);
    	}else{
    		jfxb.setTextFill(color1);
    		jfxb.setRipplerFill(color2);
    	}
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
	@FXML
	private HBox bsItem;
	@FXML
	private HBox bmItem;
	@FXML
	private HBox blItem;

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
