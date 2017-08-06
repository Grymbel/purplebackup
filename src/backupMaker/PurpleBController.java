package backupMaker;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import zipper.AESThing;
import zipper.DBLocker;
import zipper.Unzipper;
import zipper.Zipper;

public class PurpleBController {
	
	private BackupObject bo;

	private int noOfPages;
	private int pageNo;
	
	private ArrayList<BackupObject> allBackups;
	private Window scene;
	
	private File exportURL;
	private File exportSwapURL;
	
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
    private JFXButton btnActuallyDoExport;
    
    @FXML
    private JFXButton btnDoImport;
    
    @FXML
    private JFXButton btnActuallyDoImport;

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
    
    @FXML
    private JFXPasswordField passwordField1;
    
    @FXML
    private JFXPasswordField passwordField2;
    
    @FXML
    private JFXPasswordField passwordField3;
    
    @FXML
    private JFXPasswordField passwordField4;
    
    @FXML
    private JFXPasswordField passwordField5;
    
    private File importFile;
    private String password;
    private String importPassword;
    
    public void initialize(){
    	bo = new BackupObject();
    	allBackups=new ArrayList<BackupObject>();

    	btnScrollLeft.setVisible(false);
    	btnScrollRight.setVisible(false);
    	
    	passwordField1.setVisible(false);
    	passwordField2.setVisible(false);
    	passwordField3.setVisible(false);
    	passwordField4.setVisible(false);
    	passwordField5.setVisible(false);
    	
    	btnActuallyDoExport.setVisible(false);
    	btnActuallyDoImport.setVisible(false);
    	
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
    		passwordField1.setVisible(true);
    		passwordField2.setVisible(true);
    		btnActuallyDoExport.setVisible(true);
    		
    		passwordField1.setPromptText("Archive Password");
    		passwordField2.setPromptText("Confirm Password");
    	}
    }
    
    @FXML
    void doExport(ActionEvent event){
    	if(passwordField1.getText().equals(passwordField2.getText())){
    		password=passwordField1.getText();

    	if(this.exportURL!=null){
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
    			
    			AESThing aes = new AESThing(password);
    			try {
					aes.encryptFile(new File(this.exportURL.toString()+"/output.zip"));
				} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
					e.printStackTrace();
				}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	}
    	passwordField1.setText("");
    	passwordField2.setText("");
    	}else{
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Passwords do not match");
    		alert.setContentText("Your chosen passwords do not match");

    		alert.showAndWait();
    	}
    }
    
    @FXML
    void doImport(ActionEvent event){
    	this.importFile=getZip();
    	if(importFile!=null){
    	passwordField3.setVisible(true);
    	passwordField4.setVisible(true);
    	passwordField5.setVisible(true);
    	
    	passwordField3.setPromptText("Import Password");
    	passwordField4.setPromptText("Swap Password");
    	passwordField5.setPromptText("Confirm Swap Password");
    	
    	btnActuallyDoImport.setVisible(true);
    	}
    }
    
    @FXML
    void doImporting(ActionEvent event){
    	boolean success = false;
    	
    	this.exportSwapURL = getDir();
    	if(exportSwapURL!=null){
    	if(passwordField4.getText().equals(passwordField5.getText())){
    	importPassword=passwordField3.getText();
    	AESThing aes = new AESThing(importPassword);
    	
    	try {    		
    		File f1 = new File("src/output/");
    		File f2 = new File("src/zipper/the.key");
    		File f3 = new File("purplebackups.db");
    		
			FileUtils.copyDirectoryToDirectory(f1 , this.exportSwapURL);
			FileUtils.copyFileToDirectory(f2 , this.exportSwapURL);
			FileUtils.copyFileToDirectory(f3, this.exportSwapURL);
			
			Zipper zip = new Zipper(this.exportSwapURL.toString());
			System.out.println(this.exportSwapURL.exists()+" exists?");
			zip.generateFileList(this.exportSwapURL,true);
			zip.plainZip(this.exportSwapURL,new File(this.exportSwapURL.toString()+"/output.zip"));
			
			FileUtils.deleteDirectory(new File(this.exportSwapURL.toString()+"/output"));
			new File(this.exportSwapURL.toString()+"/the.key").delete();
			new File(this.exportSwapURL.toString()+"/purplebackups.db").delete();

			try {
				aes.encryptFile(new File(this.exportSwapURL.toString()+"/output.zip"));
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	File tempUnzipLocation = new File("src/tempToUnzip");
    	tempUnzipLocation.mkdir();
    	
    	File tempZip = new File("src/tempToUnzip/"+importFile.getName());
    	System.out.println(tempZip.getName());
    	
    	try {
			FileUtils.copyFileToDirectory(importFile, tempUnzipLocation);
		} catch (IOException e1) {
			System.err.println(e1.getMessage());
		}
    	boolean errorless = true;
    	try {
			aes.decryptFile(tempZip);
			
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Cannot Decrypt!");
    		alert.setHeaderText("Decryption failed");
    		alert.setContentText("Wrong password or damaged input zip");

    		alert.showAndWait();
			errorless=false;
			
			
		}
    	if(errorless){
    		try {
				aes.writeToFile(tempZip);
				Unzipper unz = new Unzipper(tempZip.toString(),tempUnzipLocation.toString());
				unz.unzipImport();
				
				File t1 = new File(tempUnzipLocation.toString()+"/purplebackups.db");
				File t2 = new File(tempUnzipLocation.toString()+"/the.key");
				File t3 = new File(tempUnzipLocation.toString()+"/output");
				
				if(t1.exists()&&t2.exists()&&t3.exists()){
				new File("purplebackups.db").delete();
				new File("src/zipper/the.key").delete();
				FileUtils.deleteDirectory(new File("src/output"));
				
				FileUtils.copyFile(t1, new File("purplebackups.db"));
				FileUtils.copyFile(t2, new File("src/zipper/the.key"));
				FileUtils.copyDirectoryToDirectory(t3, new File("src"));
				
				success=true;
				
	    		
				}
				else{
					Alert alert = new Alert(AlertType.WARNING);
		    		alert.setTitle("Bad content");
		    		alert.setHeaderText("Zip has invalid content");
		    		alert.setContentText("The zip file did not contain the right files");

		    		alert.showAndWait();
				}
			} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Cannot Decrypt!");
	    		alert.setHeaderText("Decryption failed");
	    		alert.setContentText("Wrong password or damaged input zip");

	    		alert.showAndWait();
			}
    	}
    	
    }
    	}
    	if(!success){
    		try {
				FileUtils.deleteDirectory(new File("src/tempToUnzip"));
			} catch (IOException e) {

			}
    		try{
    		FileUtils.cleanDirectory(new File(this.exportSwapURL.toString()));
    		}
    		catch(Exception e){
    			
    		}
    	}
    	if(success){
    		System.out.println("Done!");
    		
    		try {
				FileUtils.deleteDirectory(new File("src/tempToUnzip"));
			} catch (IOException e) {

			}
    		try{
    		FileUtils.cleanDirectory(new File(this.exportSwapURL.toString()));
    		}
    		catch(Exception e){
    			
    		}
			
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Restart required");
    		alert.setHeaderText("The application will now restart");

    		alert.showAndWait();
    		DBLocker.lockDB();
			TimerAccess.closeTime();
			
			System.exit(0);
    	}
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
    
    public static File getZip(){
		JFileChooser chooser = new JFileChooser();
		File f = null;
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP Archives", "zip");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       f = chooser.getSelectedFile();
	    }
    	return f;
    }
    
    @FXML
	private ImageView sideIcon;
	@FXML
	private ImageView closeIcon;
	@FXML
	private VBox sidebarNav;
	@FXML
	private HBox homeItem;
	@FXML
	private HBox userItem;
	@FXML
	private HBox firewallItem;
	@FXML
	private HBox auditItem;
	@FXML
	private HBox backupItem;
	@FXML
	private HBox bLocation;
	@FXML
	private HBox bScheduler;
	@FXML
	private HBox bHIDS;
	@FXML
	private HBox secureItem;
	@FXML
	private HBox logoutItem;

	@FXML
	public void showSidebar(MouseEvent event) {
		closeIcon.setVisible(true);
		sideIcon.setVisible(false);
		Timeline timeline = new Timeline();
		KeyValue sidebarNavValue = new KeyValue(sidebarNav.layoutXProperty(), 0);
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), sidebarNavValue);
		
		timeline.getKeyFrames().addAll(keyFrame);
		timeline.play();
	}
	
	@FXML
	public void hideSidebar(MouseEvent event) {
		closeIcon.setVisible(false);
		sideIcon.setVisible(true);
		Timeline timeline = new Timeline();
		KeyValue sidebarNavValue = new KeyValue(sidebarNav.layoutXProperty(), -240);
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), sidebarNavValue);
		
		timeline.getKeyFrames().addAll(keyFrame);
		timeline.play();
	}
	
	@FXML
	public void showHoverColor(MouseEvent event) {
		if (event.getSource().equals(homeItem)) {
			homeItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(userItem)) {
			userItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(firewallItem)) {
			firewallItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(auditItem)) {
			auditItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(backupItem)) {
			backupItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(bLocation)) {
			bLocation.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(bScheduler)) {
			bScheduler.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(bHIDS)) {
			bHIDS.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(secureItem)) {
			secureItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(logoutItem)) {
			logoutItem.setStyle("-fx-background-color: #673AB7");
		}
	}
	
	@FXML
	public void hideHoverColor(MouseEvent event) {
		if (event.getSource().equals(homeItem)) {
			homeItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(userItem)) {
			userItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(firewallItem)) {
			firewallItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(auditItem)) {
			auditItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(backupItem)) {
			backupItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(bLocation)) {
			bLocation.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(bScheduler)) {
			bScheduler.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(bHIDS)) {
			bHIDS.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(secureItem)) {
			secureItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(logoutItem)) {
			logoutItem.setStyle("-fx-background-color: #9575CD");
		}
	}
	
	@FXML
	public void changePage(MouseEvent event) throws IOException {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		Parent root = null;
		if (event.getSource().equals(homeItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"));
		}
		if (event.getSource().equals(userItem)) {
			root = FXMLLoader.load(getClass().getResource("../userManagement/view/DatabaseTableView.fxml"));
		}
		else if (event.getSource().equals(firewallItem)) {
			root = FXMLLoader.load(getClass().getResource("../basicFirewall/view/BasicFirewallView.fxml"));
		}
		else if (event.getSource().equals(auditItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/AuditLog.fxml"));
		}
		else if (event.getSource().equals(backupItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/BackupMaker.fxml"));
		}
		else if (event.getSource().equals(bLocation)) {
			root = FXMLLoader.load(getClass().getResource("../view/BackupLocations.fxml"));
		}
		else if (event.getSource().equals(bScheduler)) {
			root = FXMLLoader.load(getClass().getResource("../view/BackupScheduler.fxml"));
		}
		else if (event.getSource().equals(bHIDS)) {
			root = FXMLLoader.load(getClass().getResource("../view/BackupHIDS.fxml"));
		}
		else if (event.getSource().equals(secureItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/Settings.fxml"));
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
