package userManagement.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import backupScheduler.TimerAccess;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import userManagement.dao.DatabaseDAO;
import userManagement.model.EditingCell;
import userManagement.model.HashPass;
import userManagement.model.Login;
import userManagement.model.Student;
import userManagement.model.Teacher;
import userManagement.model.User;
import userManagement.model.UserAll;
import userManagement.model.UserAllTable;
import zipper.DBLocker;

public class DatabaseTableViewController {
	@FXML
    private JFXTextField Login_Username;
    @FXML
    private JFXTextField Login_Password;
    @FXML
    private JFXTextField User_NRIC;
    @FXML
    private JFXTextField User_Name;
    @FXML
    private JFXTextField User_ContactNo;
    @FXML
    private JFXRadioButton User_Gender_M;
    @FXML
    private ToggleGroup Gender;
    @FXML
    private JFXRadioButton User_Gender_F;
    @FXML
    private JFXTextField User_Email;
    @FXML
    private JFXTextField User_Class;
    @FXML
    private JFXDatePicker User_DOB;
    @FXML
    private JFXTextArea User_Address;
    @FXML
    private JFXToggleButton studentTeacherInputChange;
    @FXML
    private VBox student_Input;
    @FXML
    private JFXTextField Student_CCA;
    @FXML
    private JFXTextField Student_UserID;
    @FXML
    private VBox teacher_Input;
    @FXML
    private JFXTextField Teacher_TeacherID;
    @FXML
    private JFXTextField Teacher_Department;
    @FXML
    private JFXTextField Teacher_UserID;
    @FXML
    private JFXButton submitBtn;
    @FXML
    private JFXButton clearBtn;
    @FXML
    private JFXButton applyBtn;
    @FXML
    private TableView<UserAllTable> dataTable;
    @FXML
    private TableColumn<UserAllTable, String> userID;
    @FXML
    private TableColumn<UserAllTable, String> username;
    @FXML
    private TableColumn<UserAllTable, String> password;
    @FXML
    private TableColumn<UserAllTable, String> salt;
    @FXML
    private TableColumn<UserAllTable, String> nRIC;
    @FXML
    private TableColumn<UserAllTable, String> name;
    @FXML
    private TableColumn<UserAllTable, String> gender;
    @FXML
    private TableColumn<UserAllTable, String> dOB;
    @FXML
    private TableColumn<UserAllTable, String> contactNo;
    @FXML
    private TableColumn<UserAllTable, String> email;
    @FXML
    private TableColumn<UserAllTable, String> schoolClass;
    @FXML
    private TableColumn<UserAllTable, String> address;
    @FXML
    private TableColumn<UserAllTable, String> keys;
    @FXML
    private TableColumn<UserAllTable, String> cCA;
    @FXML
    private TableColumn<UserAllTable, String> teacherID;
    @FXML
    private TableColumn<UserAllTable, String> department;
    static ArrayList<String> logs = new ArrayList<String>();
    static boolean show = true;
    
	public void initialize() throws ClassNotFoundException, SQLException{
		User_Gender_M.setUserData('M');
		User_Gender_F.setUserData('F');
	    ArrayList<UserAllTable> uatArray = new ArrayList<UserAllTable>();
	    
	    DatabaseDAO dba = new DatabaseDAO(0);
	    for(UserAll ua:dba.getDatabaseUserAll()){
	    	uatArray.add(new UserAllTable(ua));
	    }
	    dba.close();
	    ObservableList<UserAllTable> userAllTableList = FXCollections.observableArrayList(uatArray);
	    
	    userID.setCellValueFactory(cellData -> cellData.getValue().getUserID());
	    username.setCellValueFactory(cellData -> cellData.getValue().getUsername());
	    password.setCellValueFactory(cellData -> cellData.getValue().getPassword());
	    salt.setCellValueFactory(cellData -> cellData.getValue().getSalt());
	    nRIC.setCellValueFactory(cellData -> cellData.getValue().getnRIC());
	    name.setCellValueFactory(cellData -> cellData.getValue().getName());
	    gender.setCellValueFactory(cellData -> cellData.getValue().getGender());
	    dOB.setCellValueFactory(cellData -> cellData.getValue().getdOB());
	    contactNo.setCellValueFactory(cellData -> cellData.getValue().getContactNo());
	    email.setCellValueFactory(cellData -> cellData.getValue().getEmail());
	    schoolClass.setCellValueFactory(cellData -> cellData.getValue().getSchoolClass());
	    address.setCellValueFactory(cellData -> cellData.getValue().getAddress());
	    keys.setCellValueFactory(cellData -> cellData.getValue().getKeys());
	    cCA.setCellValueFactory(cellData -> cellData.getValue().getcCA());
	    teacherID.setCellValueFactory(cellData -> cellData.getValue().getTeacherID());
	    department.setCellValueFactory(cellData -> cellData.getValue().getDepartment());
	    
	    Callback<TableColumn<UserAllTable, String>, TableCell<UserAllTable, String>> cellFactory = (TableColumn<UserAllTable, String> p) -> new EditingCell();
	    userID.setCellFactory(cellFactory);
	    username.setCellFactory(cellFactory);
	    password.setCellFactory(cellFactory);
	    salt.setCellFactory(cellFactory);
	    nRIC.setCellFactory(cellFactory);
	    name.setCellFactory(cellFactory);
	    gender.setCellFactory(cellFactory);
	    dOB.setCellFactory(cellFactory);
	    contactNo.setCellFactory(cellFactory);
	    email.setCellFactory(cellFactory);
	    schoolClass.setCellFactory(cellFactory);
	    address.setCellFactory(cellFactory);
	    keys.setCellFactory(cellFactory);
	    cCA.setCellFactory(cellFactory);
	    teacherID.setCellFactory(cellFactory);
	    department.setCellFactory(cellFactory);
	    
	    nRIC.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setnRIC(new SimpleStringProperty(newValue));
	                logs.add("UPDATE Student SET NRIC='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    username.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	String oldValue = t.getOldValue();
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setUsername(new SimpleStringProperty(newValue));
	                logs.add("UPDATE Login SET Username='" + newValue + "' WHERE 'Username'='" + oldValue + "';");
	        });
	    password.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	String username = ((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUsername().get();
	            	String newValue = t.getNewValue();
	            	HashPass HP = new HashPass();
	            	Base64.Decoder dnc = Base64.getDecoder();
					byte [] saltDecoded = dnc.decode(t.getTableView().getItems().get(t.getTablePosition().getRow()).getSalt().get());
	            	newValue = HP.getHashedPassword(newValue, saltDecoded);
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setPassword(new SimpleStringProperty(newValue));
	                logs.add("UPDATE Login SET Password='" + newValue + "' WHERE Username='" + username + "';");
	        });
	    name.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setName(new SimpleStringProperty(newValue));
	                logs.add("UPDATE User SET Name='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    gender.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setGender(new SimpleStringProperty(newValue));
	                logs.add("UPDATE User SET Gender='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    dOB.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setdOB(new SimpleStringProperty(newValue));
	                logs.add("UPDATE User SET DOB='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    contactNo.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setContactNo(new SimpleStringProperty(newValue));
	                logs.add("UPDATE User SET ContactNo='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    email.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setPassword(new SimpleStringProperty(newValue));
	                logs.add("UPDATE User SET Email='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    schoolClass.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setSchoolClass(new SimpleStringProperty(newValue));
	                logs.add("UPDATE User SET Class='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    address.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setAddress(new SimpleStringProperty(newValue));
	                logs.add("UPDATE User SET Address='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    cCA.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setcCA(new SimpleStringProperty(newValue));
	                logs.add("UPDATE Student SET CCA='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    teacherID.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setTeacherID(new SimpleStringProperty(newValue));
	                logs.add("UPDATE Teacher SET TeacherID='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    department.setOnEditCommit(
	            (CellEditEvent<UserAllTable, String> t) -> {
	            	int userID = Integer.parseInt(((UserAllTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).getUserID().get());
	            	String newValue = t.getNewValue();
	                ((UserAllTable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setDepartment(new SimpleStringProperty(newValue));
	                logs.add("UPDATE Teacher SET Department='" + newValue + "' WHERE UserID=" + userID + ";");
	        });
	    
	    dataTable.setItems(userAllTableList);
	    dataTable.setEditable(true);
	    
	    
	}
    
    @FXML
    void clear(ActionEvent event) {
    	clear();
    }

    @FXML
    void showCorrrectInput(ActionEvent event) {
    	if(show){
    		teacher_Input.setVisible(true);
    		student_Input.setVisible(false);
    		show = false;
    	}else{
    		teacher_Input.setVisible(false);
    		student_Input.setVisible(true);
    		show = true;
    	}
    }

    @FXML
    void submit(ActionEvent event) throws UnsupportedEncodingException {
    	String login = Login_Username.getText();
    	String password = Login_Password.getText();
    	String nRIC = User_NRIC.getText();
    	String name = User_Name.getText();
    	String contactNo = User_ContactNo.getText();
    	String gender = Gender.getSelectedToggle().getUserData().toString();
    	String email = User_Email.getText();
    	String schoolClass = User_Class.getText();
    	String dOB = User_DOB.getValue().toString();
    	String address = User_Address.getText();
    	String cCA = Student_CCA.getText();
    	String department = Teacher_Department.getText();
    	
    	HashPass HP = new HashPass();
    	byte [] newSalt = HP.createSalt();
    	Base64.Encoder enc = Base64.getEncoder();
    	String saltString = enc.encodeToString(newSalt);
		password = HP.getHashedPassword(password, newSalt);
        
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String keys = salt.toString();
	        
    	User user = new User(nRIC, name, gender, dOB, contactNo, email, schoolClass, address, keys, "nothing");
    	user.setUserID(dataTable.getItems().size() + 1);
    	Login l = new Login(login, password, saltString, user);
    	UserAll ua = new UserAll(user, l);
    	if(!show){
    		Teacher teacher = new Teacher(department, user);
    		Student student = new Student(null, user);
    		ua = new UserAll(user, l, student, teacher);
    		logs.add("INSERT INTO User (UserID, NRIC, Name, Gender, DOB, ContactNo, Email, Class, Address, User.Keys, folder) VALUES (" + user.getUserID() + ", '" + user.getnRIC() + "', '" + name + "', '" + gender + "', '" + dOB + "', '" + contactNo + "', '" + email + "', '" + schoolClass + "', '" + address +  "', '" + keys + "', '" + "nothing" + "');");
    		logs.add("INSERT INTO Login (Username, Password, Salt, UserID) VALUES ('" + nRIC + "', '" + password + "', '" + saltString + "', " + user.getUserID() + ");");
        	logs.add("INSERT INTO Teacher (Department, UserID) VALUES ('" + department + "', " + user.getUserID() + ");");
    	}
    	else{
    		Teacher teacher = new Teacher(null, user);
    		Student student = new Student(cCA, user);
    		ua = new UserAll(user, l, student, teacher);
    		logs.add("INSERT INTO User (UserID, NRIC, Name, Gender, DOB, ContactNo, Email, Class, Address, User.Keys, folder) VALUES (" + user.getUserID() + ", '" + user.getnRIC() + "', '" + name + "', '" + gender + "', '" + dOB + "', '" + contactNo + "', '" + email + "', '" + schoolClass + "', '" + address +  "', '" + keys + "', '" + "nothing" + "');");
    		logs.add("INSERT INTO Login (Username, Password, Salt, UserID) VALUES ('" + nRIC + "', '" + password + "', '" + saltString + "', " + user.getUserID() + ");");
    		logs.add("INSERT INTO Student (CCA, UserID) VALUES ('" + cCA + "', " + user.getUserID() + ");");
    	}
    	
    	UserAllTable uat = new UserAllTable(ua);
    	
    	dataTable.getItems().add(uat);
    	clear();
    }
    
    @FXML
    void applyChange(ActionEvent event) throws IOException {
    	@SuppressWarnings("rawtypes")
		Dialog dialog = new Dialog();
		Stage stage = (Stage)dialog.getDialogPane().getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../view/ApplyChangesPopup.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
    	for(String s:logs){
    		System.out.println(s);
    	}
    }
    
    public void clear(){
    	Login_Username.clear();
    	Login_Password.clear();
    	User_NRIC.clear();
    	User_Name.clear();
    	User_ContactNo.clear();
    	User_Gender_M.setSelected(true);
    	User_Email.clear();
    	User_Class.clear();
    	User_DOB.setValue(null);
    	User_Address.clear();
    	Student_CCA.clear();
    	Student_UserID.clear();
    	Teacher_TeacherID.clear();
    	Teacher_Department.clear();
    	Teacher_UserID.clear();
    }
	
	@FXML
    void generateNewSaltKeys(MouseEvent event) {
		if (event.getClickCount() == 2) //Checking double click
	    {
			System.out.println(event.getSource().getClass().getName().toString());
			System.out.println(dataTable.getSelectionModel().getSelectedItem().getSalt());
			System.out.println(dataTable.getSelectionModel().getSelectedItem().getKeys());
	    }
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
			backupItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(bScheduler)) {
			backupItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(bHIDS)) {
			backupItem.setStyle("-fx-background-color: #673AB7");
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
			backupItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(bScheduler)) {
			backupItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(bHIDS)) {
			backupItem.setStyle("-fx-background-color: #9575CD");
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
