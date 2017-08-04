package settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import com.jfoenix.controls.JFXPasswordField;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import login.HashPass;
import login.LoginModel;

public class SettingsController {
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
	private HBox secureItem;
	@FXML
	private HBox logoutItem;
	@FXML
	private Label editPassLbl;
	@FXML
	private VBox editPass;
	@FXML
	private JFXPasswordField oldPass;
	@FXML
	private JFXPasswordField newPass;
	@FXML
	private JFXPasswordField confirmNewPass;
	@FXML
	private Label errorLbl;
	
	private boolean clicked = true;
	
	@FXML
	public void initialize() {
		
	}
	
	@FXML
	public void showEditPass(MouseEvent event) {
		if (clicked == true) {
			editPassLbl.setText("Click to close");
			editPass.setVisible(true);
			clicked = false;
		}
		else if (clicked == false) {
			editPassLbl.setText("Click to change password");
			editPass.setVisible(false);
			clicked = true;
		}
		
	}
	
	@FXML
	public void changePass(ActionEvent event) {
		Base64.Encoder enc = Base64.getEncoder();
		LoginModel loginModel = new LoginModel();
		String oldPassword = oldPass.getText();
		String newPassword = newPass.getText();
		String confirmPass = confirmNewPass.getText();
		String storedSalt = null;
		String storedPassword = null;
		HashPass HP = new HashPass();
		
		ArrayList<LoginModel> list = LoginModel.getAllData();
		for (LoginModel model : list) {
			storedSalt = model.getSalt();
			storedPassword = model.getPassword();
			loginModel = model;
		}
		
		byte [] decodedSalt = HP.getDecodedSalt(storedSalt);
		String hashedPassword = HP.getHashedPassword(oldPassword, decodedSalt);
		
		if (!oldPassword.equals(null) && !oldPassword.equals("") && !newPassword.equals(null) && !newPassword.equals("") && !confirmPass.equals(null) && !confirmPass.equals("")) {
			if (!hashedPassword.equals(storedPassword)) {
				errorLbl.setVisible(true);
				errorLbl.setText("Old password is wrong");
			}
			else if (newPassword.equals(oldPassword)) {
				errorLbl.setVisible(true);
				errorLbl.setText("New password cannot be the same as previous");
			}
			else if (!confirmPass.equals(newPassword)) {
				errorLbl.setVisible(true);
				errorLbl.setText("New password does not match");
			}
			else {
				byte [] newSalt = HP.createSalt();
				String newSaltStr = enc.encodeToString(newSalt);
				String newHashedPassword = HP.getHashedPassword(confirmPass, newSalt);
				
				loginModel.setSalt(newSaltStr);
				loginModel.setPassword(newHashedPassword);
				loginModel.updateAdmin();
			}
		}
		else {
			errorLbl.setVisible(true);
			errorLbl.setText("All inputs are required to be filled in");
		}
	}
	
	@FXML
	public void checkEnter(KeyEvent event) {
		if (event.getCode().getName().equals("Enter")) {
			Base64.Encoder enc = Base64.getEncoder();
			LoginModel loginModel = new LoginModel();
			String oldPassword = oldPass.getText();
			String newPassword = newPass.getText();
			String confirmPass = confirmNewPass.getText();
			String storedSalt = null;
			String storedPassword = null;
			HashPass HP = new HashPass();
			
			ArrayList<LoginModel> list = LoginModel.getAllData();
			for (LoginModel model : list) {
				storedSalt = model.getSalt();
				storedPassword = model.getPassword();
				loginModel = model;
			}
			
			byte [] decodedSalt = HP.getDecodedSalt(storedSalt);
			String hashedPassword = HP.getHashedPassword(oldPassword, decodedSalt);
			
			if (!oldPassword.equals(null) && !oldPassword.equals("") && !newPassword.equals(null) && !newPassword.equals("") && !confirmPass.equals(null) && !confirmPass.equals("")) {
				if (!hashedPassword.equals(storedPassword)) {
					errorLbl.setText("Old password is wrong");
				}
				else if (newPassword.equals(oldPassword)) {
					errorLbl.setText("New password cannot be the same as previous");
				}
				else if (!confirmPass.equals(newPassword)) {
					errorLbl.setText("New password does not match");
				}
				else {
					errorLbl.setText("Can change password");
					byte [] newSalt = HP.createSalt();
					String newSaltStr = enc.encodeToString(newSalt);
					String newHashedPassword = HP.getHashedPassword(confirmPass, newSalt);
					
					loginModel.setSalt(newSaltStr);
					loginModel.setPassword(newHashedPassword);
					loginModel.updateAdmin();
				}
			}
			else {
				errorLbl.setText("All inputs are required to be filled in");
			}
		}
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
		else if (event.getSource().equals(secureItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/Settings.fxml"));
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
