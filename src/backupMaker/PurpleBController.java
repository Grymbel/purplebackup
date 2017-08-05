package backupMaker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import backupScheduler.TimerAccess;
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
import javafx.scene.control.ButtonType;
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
import zipper.DBLocker;
import zipper.Zipper;

public class PurpleBController {
	
	private BackupObject bo;

	private int noOfPages;
	private int pageNo;
	
	private ArrayList<BackupObject> allBackups;
	private Window scene;
	
	private File exportURL;
	
    @FXML
    private JFXButton btnAddBackup;

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
    private JFXButton btnDoExport;

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
    
    public void initialize(){
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
        if(!bo.getIsBase()){
        data.add(new BackupObject(bo.getUserBackup(),bo.getCloudBackup(),bo.getWebBackup(),bo.getAuditBackup(),bo.getCreationDate(),bo.getIsBase()));
        }
        else{
        	data.add(new BackupObject(true,true,true,true,bo.getCreationDate(),true));
        }
    }
    
    //Process of registering a manual backup
    @FXML
    void doAddBackup(ActionEvent event) {
    	long time =System.currentTimeMillis();
    	bo.initBackupLocations();
    	bo.setCreationDate(time);
    	//Sets the tables
    	if(!(bo.getAuditBackup()==false&&bo.getCloudBackup()==false&&bo.getWebBackup()==false&&bo.getUserBackup()==false)){
    		addBackupObject();
    	
    	if(bo.getIsBase()==true){
    		bo.setAuditBackup(true);
    		bo.setCloudBackup(true);
    		bo.setWebBackup(true);
    		bo.setUserBackup(true);
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

    //Restore a backup 
    @FXML
    void doRestore(ActionEvent event) {
    	if(bmtable.getSelectionModel().getSelectedIndex()>=0){
    		//Asks for confirmation 
    		Alert alert = new Alert(AlertType.CONFIRMATION, "When your backup is restored, it is fully unencrypted and ready to use. Move it to its proper location quickly. Proceed?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
    	int selOnes = bmtable.getSelectionModel().getSelectedIndex();
    	int sel = (pageNo*10)+selOnes;
    	
    	BackupDAO bdao = new BackupDAO();
		allBackups.addAll(bdao.getExistingBackups());
    	
    	allBackups.get(sel).restore(sel);
			}
    	}
    }

    //Button is highlighted and option registered
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

    //Button is highlighted and option registered
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

    //Button is highlighted and option registered
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

    //Button is highlighted and option registered
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

    //Sets up a base Backup
    @FXML
    void doEnableBase(ActionEvent event){
    	ArrayList<JFXButton> buttons = new ArrayList<JFXButton>();
		buttons.add(btnSelAudit);
		buttons.add(btnSelCloud);
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
    
    //Allows export to backup to external media
    @FXML
    void doFindOutput(ActionEvent event){
    	this.exportURL = getDir();

    	if(this.exportURL!=null&&this.exportURL.list().length==0){
    	try {    		
    		File f1 = new File("src/output/");
    		File f2 = new File("src/zipper/the.key");
    		File f3 = new File("purplebackups.db");
    		
			FileUtils.copyDirectoryToDirectory(f1 , this.exportURL);
			FileUtils.copyFileToDirectory(f2 , this.exportURL);
			FileUtils.copyFileToDirectory(f3, this.exportURL);
			
			Zipper zip = new Zipper(this.exportURL.toString());
			zip.generateFileList(this.exportURL,true);
			zip.plainZip(this.exportURL,new File(this.exportURL.toString()+"/output.zip"));
			
			FileUtils.deleteDirectory(new File(this.exportURL.toString()+"/output"));
			new File(this.exportURL.toString()+"/the.key").delete();
			new File(this.exportURL.toString()+"/purplebackups.db").delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	}
    }
    
    @FXML
    void doImport(ActionEvent event){
    	
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
    
    //Generic directory selection method
    public File getDir(){
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("Select a directory");
    	File selectedDirectory = chooser.showDialog(scene);
    	
    	return selectedDirectory;
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
