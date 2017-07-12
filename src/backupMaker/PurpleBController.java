package backupMaker;

import java.util.ArrayList;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;

public class PurpleBController {
	
	private BackupObject bo;
	private LastDoneBackup ldb;

	private int noOfPages;
	private int pageNo;
	
	private ArrayList<BackupObject> allBackups;
	
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
    private TableColumn<BackupObject, String> colMessage;
    
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
    	bo.setIsBase(false);
    	allBackups=new ArrayList<BackupObject>();

    	btnScrollLeft.setVisible(false);
    	btnScrollRight.setVisible(false);
    	
    	ldb = new LastDoneBackup();
			BackupDAO bdao = new BackupDAO();
			ArrayList<String> existingBackups = new ArrayList<String>();
			existingBackups.addAll(bdao.getExistingBackups());
			
		for(int o=0;o<existingBackups.size();o++){
			BackupObject bck = new BackupObject();
			Scanner st=new Scanner(existingBackups.get(o));

			st.useDelimiter(";");
			int binaryCheck=0;
			
			binaryCheck=st.nextInt();
			if(binaryCheck==1){
				bck.setUserBackup(true);
			}
			binaryCheck=st.nextInt();
			if(binaryCheck==1){
				bck.setCloudBackup(true);
			}
			binaryCheck=st.nextInt();
			if(binaryCheck==1){
				bck.setWebBackup(true);
			}
			binaryCheck=st.nextInt();
			if(binaryCheck==1){
				bck.setAuditBackup(true);
			}
			binaryCheck=st.nextInt();
			if(binaryCheck==1){
				bck.setMessageBackup(true);
			}
			
			bck.setCreationDate(st.nextLong());
			
			binaryCheck=st.nextInt();
			if(binaryCheck==1){
				bck.setIsBase(true);
			}
			else{
				bck.setIsBase(false);
			}
			
			
			allBackups.add(bck);
			st.close();
		}
		ObservableList<BackupObject> data = bmtable.getItems();
		int todo;
		if(allBackups.size()>10){
			todo=10;
		}
		else{
			todo=allBackups.size();
		}
		for(int i=0;i<todo;i++){
			System.out.println("==="+allBackups.get(i));
	    data.add(new BackupObject(allBackups.get(i).getUserBackup(),allBackups.get(i).getCloudBackup(),allBackups.get(i).getWebBackup(),allBackups.get(i).getAuditBackup(),allBackups.get(i).getMessageBackup(),allBackups.get(i).getCreationDate(),
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
		//Display object constructor
        ObservableList<BackupObject> data = bmtable.getItems();
        data.add(new BackupObject(bo.getUserBackup(),bo.getCloudBackup(),bo.getWebBackup(),bo.getAuditBackup(),bo.getMessageBackup(),bo.getCreationDate(),bo.getIsBase()));
    }
    
    @FXML
    void doAddBackup(ActionEvent event) {
    	long time =System.currentTimeMillis();
    	bo.initBackupLocations();
    	bo.setCreationDate(time);
    	//Sets the tables
    	if(!(bo.getAuditBackup()==false&&bo.getCloudBackup()==false&&bo.getMessageBackup()==false&&bo.getWebBackup()==false&&bo.getUserBackup()==false)){
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
    void doSelMsg(ActionEvent event) {
    	colorSwap(btnSelMsg);
    	if(bo.getMessageBackup()==false){
        	bo.setMessageBackup(true);
        	}
        	else{
        		bo.setMessageBackup(false);
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
    		bo.setMessageBackup(true);
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
    		bo.setMessageBackup(false);
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

    }

    @FXML
    void gotoSchedule(ActionEvent event) {

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
    		data.add(new BackupObject(allBackups.get(y).getUserBackup(),allBackups.get(y).getCloudBackup(),allBackups.get(y).getWebBackup(),allBackups.get(y).getAuditBackup(),allBackups.get(y).getMessageBackup(),allBackups.get(y).getCreationDate(),allBackups.get(y).getIsBase()));
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
    		data.add(new BackupObject(allBackups.get(y).getUserBackup(),allBackups.get(y).getCloudBackup(),allBackups.get(y).getWebBackup(),allBackups.get(y).getAuditBackup(),allBackups.get(y).getMessageBackup(),allBackups.get(y).getCreationDate(),allBackups.get(y).getIsBase()));
    	}
    	this.pageNo=this.pageNo+1;

    	}
    }
    
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
}
