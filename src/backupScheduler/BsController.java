package backupScheduler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import backupMaker.BackupObject;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import zipper.DBLocker;

public class BsController{
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
    private JFXButton btnScrollLeft;

    @FXML
    private JFXButton btnScrollRight;
    
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
    private JFXButton btnBack;

    @FXML
    private JFXButton btnCancel;
    
    @FXML
    private JFXDatePicker datePicker;
    
    @FXML
    private JFXDatePicker timePicker;
    
    @FXML
    private TextField taInterval;
    
    @FXML
    private TextField taName;
    
    @FXML
    private TextField taRepeat;
    
    @FXML
    private TableView<ScheduleObject> bsTable;
    
    @FXML
    private TableColumn<ScheduleObject, String> colName;

    @FXML
    private TableColumn<ScheduleObject, Integer> colMaxT;

    @FXML
    private TableColumn<ScheduleObject, Integer> colTDone;

    @FXML
    private TableColumn<ScheduleObject, String> colTofDay;

    @FXML
    private TableColumn<ScheduleObject, String> colNextInstance;

    @FXML
    private TableColumn<ScheduleObject, String> colBooleans;
    
    private BackupObject bo;
    
    private ArrayList<ScheduleObject> scheduleList;
	private int noOfPages;
	private int pageNo;
    
	public void initialize(){
		//Starts the timer
    	timePicker.setShowTime(true);
    	this.bo = new BackupObject();
    	
    	DBConnect dbc = new DBConnect();
    	this.scheduleList = new ArrayList<ScheduleObject>();
    
    	//Loads the table with objects generated from the DB
    	try {
			ResultSet res = dbc.getAllSchedules();
			
			while(res.next()){
				BackupObject bo = new BackupObject();
				
				bo.setAuditBackup(res.getBoolean("audit"));
				bo.setCloudBackup(res.getBoolean("cloud"));
				bo.setUserBackup(res.getBoolean("user"));
				bo.setWebBackup(res.getBoolean("web"));
				
				ScheduleObject so = new ScheduleObject(
						res.getString("sname"),
						res.getInt("maxtimes"), 
						res.getInt("timesDone"), 
						res.getLong("dayTime"), 
						res.getLong("interval"), 
						res.getLong("startingDay"), 
						res.getLong("nextTime"),
						bo);
				
				if(res.getLong("lastDone")==0){
					so.setNextInstance(so.getNextInstance());
					so.setNextInstanceSTR(MillisConverter.getStringFromLong(so.getNextInstance()));
				}
				if(so.getMaxTimes()==so.getTimesDone()){
					dbc.removeSchedule(res.getInt("id"));
				}
				else{
				scheduleList.add(so);
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
    	
    	ObservableList<ScheduleObject> data = bsTable.getItems();
		int todo;
		//For page formatting 
		if(scheduleList.size()>10){
			todo=10;
		}
		else{
			todo=scheduleList.size();
		}
		for(int i=0;i<todo;i++){
	    data.add(scheduleList.get(i));
		}
		
		this.noOfPages = (data.size()/10)+1;
		this.pageNo = 0;
		if(noOfPages>1){
			btnScrollLeft.setVisible(true);
			btnScrollRight.setVisible(true);
		}
		
		
    }
    
	//Adds an entry to the schedule
    @FXML
    void doAddBackup(ActionEvent event) {
    	boolean auth = false;
    	try{
    		auth = (MillisConverter.getLongDate(datePicker.getValue().toString())+MillisConverter.getLongTime(timePicker.getTime().toString()))> System.currentTimeMillis();
    	}catch(NullPointerException e){
    		
    	}
    			
    	if(auth){
    	try{
    		//Writes a new schedule object
    	ScheduleObject so = new ScheduleObject(
    			taName.getText(),
    			Integer.parseInt(taRepeat.getText()),
    			MillisConverter.getLongTime(timePicker.getTime().toString()),
    			MillisConverter.getDaysToLong(Integer.parseInt(taInterval.getText())),
    			MillisConverter.getLongDate(datePicker.getValue().toString()),
    			this.bo
    			);

    	DBConnect dbc = new DBConnect();
    	dbc.addSchedule(so);
    	
    	ObservableList<ScheduleObject> data = bsTable.getItems();
    	data.add(so);
    	}catch(Exception e){
    		System.err.println(e.getMessage());
    	}

    	//Cleans the UI
    	
    	taName.setText("");
    	taRepeat.setText("");
    	
    	datePicker.setValue(null);
    	taInterval.setText("");
    	
    	if(this.bo.getAuditBackup()){
    		colorSwap(btnSelAudit);
    	}
    	if(this.bo.getCloudBackup()){
    		colorSwap(btnSelCloud);
    	}
    	if(this.bo.getUserBackup()){
    		colorSwap(btnSelUserData);
    	}
    	if(this.bo.getWebBackup()){
    		colorSwap(btnSelWeb);
    	}
    	
    	this.bo = new BackupObject();
    	}
    }
    

    @FXML
    void doCancel(ActionEvent event) {
    	//Remove a scheduled object
    	if(bsTable.getSelectionModel().getSelectedIndex()>=0){
        	int selOnes = bsTable.getSelectionModel().getSelectedIndex();
        	int sel = (pageNo*10)+selOnes;
        	
    		scheduleList.remove(sel);
    		ObservableList<ScheduleObject> data = bsTable.getItems();
    		data.clear();
    		data.addAll(scheduleList);
    		
    		DBConnect dbc = new DBConnect();
    		try {
				dbc.removeSchedule(sel+1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	}
    }

    @FXML
    void doScrollLeft(ActionEvent event) {
    	int minSchedules;
    	if((this.pageNo-1)>=0){
    	minSchedules = (pageNo-1)*10;
    	ObservableList<ScheduleObject> data = bsTable.getItems();
		data.clear();
    	for(int y =minSchedules;y<pageNo*10;y++){
    		data.add(scheduleList.get(y));
    	}
    	this.pageNo=this.pageNo-1;
    	}
    }

    @FXML
    void doScrollRight(ActionEvent event) {
    	int maxSchedules = scheduleList.size();
    	if(maxSchedules>=noOfPages*10){
    	maxSchedules = pageNo*10;
    	}
    	else{
    		maxSchedules = scheduleList.size();
    	}
    	if(maxSchedules>=(pageNo+1)*10){
    		ObservableList<ScheduleObject> data = bsTable.getItems();
    		data.clear();
    	for(int y =(noOfPages-1)*10;y<maxSchedules;y++){
    		data.add(scheduleList.get(y));
    	}
    	this.pageNo=this.pageNo+1;

    	}
    }


	@FXML
	void doSelAudit(ActionEvent event) {
		this.bo.setAuditBackup(true);
		colorSwap((JFXButton) event.getSource());
	}

	@FXML
	void doSelCloud(ActionEvent event) {
		this.bo.setCloudBackup(true);
		colorSwap((JFXButton) event.getSource());
    }

	@FXML
    void doSelUserData(ActionEvent event) {
		this.bo.setUserBackup(true);
		colorSwap((JFXButton) event.getSource());
    }

    @FXML
    void doSelWeb(ActionEvent event) {
    	this.bo.setWebBackup(true);
    	colorSwap((JFXButton) event.getSource());
    }

    @FXML
    void gotoBack(ActionEvent event) {

    }
	    
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
