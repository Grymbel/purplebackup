package backupHIDS;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import backupScheduler.TimerAccess;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import zipper.DBLocker;

public class HIDSController {

	private int noOfPages;
	private int pageNo;
	
	private ArrayList<HIDSObject> allEntries;
	private ArrayList<HIDSObject> crudeEntries;

    @FXML
    private JFXButton btnResolve;

    @FXML
    private JFXButton btnUnresolve;
    
    @FXML
    private JFXButton btnScrollLeft;

    @FXML
    private JFXButton btnScrollRight;

    @FXML
    private TableView<HIDSObject> HIDSTable;

    @FXML
    private TableColumn<HIDSObject, Integer> colRelID;

    @FXML
    private TableColumn<HIDSObject, String> colRelDir;

    @FXML
    private TableColumn<HIDSObject, String> colMessage;

    @FXML
    private TableColumn<HIDSObject, String> colResolve;

    @FXML
    private JFXButton btnSchedule;

    @FXML
    private JFXButton btnRestore;
    
    @FXML
    private JFXButton btnManual;

    @FXML
    private JFXCheckBox chbEnableBase;
    
    public void initialize(){
    	allEntries=new ArrayList<HIDSObject>();
    	crudeEntries=new ArrayList<HIDSObject>();
    	btnScrollLeft.setVisible(false);
    	btnScrollRight.setVisible(false);
    	
    		HIDSDAO hdao = new HIDSDAO();
    		
			crudeEntries.addAll(hdao.getAllHIDSEntries());
			for(HIDSObject hso : crudeEntries){
				if(hso.getAlertMessage()!=null){
				allEntries.add(hso);
				}
			}
		ObservableList<HIDSObject> data = HIDSTable.getItems();
		int todo;
		//For page formatting 
		if(allEntries.size()>10){
			todo=10;
		}
		else{
			todo=allEntries.size();
		}
		for(int i=0;i<todo;i++){
	    data.add(allEntries.get(i));
		}
		
		this.noOfPages = (data.size()/10)+1;
		this.pageNo = 0;
		if(noOfPages>1){
			btnScrollLeft.setVisible(true);
			btnScrollRight.setVisible(true);
		}
    }

    @FXML
    void doResolve(ActionEvent event){
    	if(HIDSTable.getSelectionModel().getSelectedIndex()>=0){
        	int selOnes = HIDSTable.getSelectionModel().getSelectedIndex();
        	int sel = (pageNo*10)+selOnes;
        	
    		allEntries.get(sel).setResolved(true);
    		allEntries.get(sel).setResolvedSTR("YES");
    		
    		ObservableList<HIDSObject> data = HIDSTable.getItems();
    		data.clear();
    		data.addAll(allEntries);
    		
    		DBConnect dbc = new DBConnect();
    		try {
				dbc.resolveHIDS(allEntries.get(sel).getRelID(), allEntries.get(sel).getRelDir());
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	}
    }
    
    @FXML
    void doUnresolve(ActionEvent event){
    	if(HIDSTable.getSelectionModel().getSelectedIndex()>=0){
        	int selOnes = HIDSTable.getSelectionModel().getSelectedIndex();
        	int sel = (pageNo*10)+selOnes;
        	
    		allEntries.get(sel).setResolved(false);
    		allEntries.get(sel).setResolvedSTR("NO");
    		
    		ObservableList<HIDSObject> data = HIDSTable.getItems();
    		data.clear();
    		data.addAll(allEntries);
    		
    		DBConnect dbc = new DBConnect();
    		try {
				dbc.unresolveHIDS(allEntries.get(sel).getRelID(), allEntries.get(sel).getRelDir());
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	}
    }
    
    @FXML
    void doScrollLeft(ActionEvent event){
    	int minEntries;
    	if((this.pageNo-1)>=0){
    	minEntries = (pageNo-1)*10;
    	ObservableList<HIDSObject> data = HIDSTable.getItems();
		data.clear();
    	for(int y =minEntries;y<pageNo*10;y++){
    		data.add(allEntries.get(y));
    	}
    	this.pageNo=this.pageNo-1;
    	}
    	else{

    	}
    }
    
    @FXML
    void doScrollRight(ActionEvent event){
    	int maxEntries = allEntries.size();
    	if(maxEntries>=noOfPages*10){
    	maxEntries = pageNo*10;
    	}
    	else{
    		maxEntries = allEntries.size();
    	}
    	if(maxEntries>=(pageNo+1)*10){
    		ObservableList<HIDSObject> data = HIDSTable.getItems();
    		data.clear();
    	for(int y =(noOfPages-1)*10;y<maxEntries;y++){
    		data.add(allEntries.get(y));
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
