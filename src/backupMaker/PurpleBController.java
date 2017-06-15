package backupMaker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;

public class PurpleBController {
	
	private BackupObject bo;
	
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
    private TableView<BackupObject> bmtable;

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colUser;

    @FXML
    private TableColumn colCloud;

    @FXML
    private TableColumn colWeb;

    @FXML
    private TableColumn colAudit;

    @FXML
    private TableColumn colMessage;

    @FXML
    private JFXButton btnSchedule;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnRestore;

    public void initialize(){
    	bo = new BackupObject();
    	allBackups=new ArrayList<BackupObject>();

		try {
			FileReader fr = new FileReader("src/output/backupLog.txt");
		
			Scanner sc = new Scanner(fr);
			sc.useDelimiter(";");
		
		while(sc.hasNextLine()){
			BackupObject bck = new BackupObject();
			
			String line1 = sc.nextLine();
			
			Scanner st = new Scanner(line1);
			st.useDelimiter(";");
			String binaryCheck="0";
			
			binaryCheck=st.next();
			if(binaryCheck.equals("1")){
				bck.setUserBackup(true);
			}
			binaryCheck=st.next();
			if(binaryCheck.equals("1")){
				bck.setCloudBackup(true);
			}
			binaryCheck=st.next();
			if(binaryCheck.equals("1")){
				bck.setWebBackup(true);
			}
			binaryCheck=st.next();
			if(binaryCheck.equals("1")){
				bck.setAuditBackup(true);
			}
			binaryCheck=st.next();
			if(binaryCheck.equals("1")){
				bck.setMessageBackup(true);
			}
			bck.setCreationDate(st.nextLong());
			
			allBackups.add(bck);
			
			ObservableList<BackupObject> data = bmtable.getItems();
			
			for(int i=0;i<allBackups.size();i++){
		    data.add(new BackupObject(allBackups.get(i).getUserBackup(),allBackups.get(i).getCloudBackup(),allBackups.get(i).getWebBackup(),allBackups.get(i).getAuditBackup(),allBackups.get(i).getMessageBackup(),allBackups.get(i).getCreationDate()));
			}
			st.close();
		}
		sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
	protected void addBackupObject() {
		//Display object constructor
        ObservableList<BackupObject> data = bmtable.getItems();
        data.add(new BackupObject(bo.getUserBackup(),bo.getCloudBackup(),bo.getWebBackup(),bo.getAuditBackup(),bo.getMessageBackup(),bo.getCreationDate()));
    }
    
    @FXML
    void doAddBackup(ActionEvent event) {
    	bo.setCreationDate(System.currentTimeMillis());
    	addBackupObject();
    }

    @FXML
    void doRestore(ActionEvent event) {

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
    void gotoBack(ActionEvent event) {

    }

    @FXML
    void gotoSchedule(ActionEvent event) {

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
