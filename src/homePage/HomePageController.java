package homePage;

import java.io.IOException;
import backupHIDS.HIDSService;
import backupScheduler.TimerAccess;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import zipper.DBLocker;

public class HomePageController{
	@FXML
	private VBox navMenu;
	@FXML
	private VBox closeNavMenu;
	@FXML
	private VBox userItem;
	@FXML
	private VBox firewallItem;
	@FXML
	private VBox secureItem;
	@FXML
	private VBox auditItem;
	@FXML
	private VBox backupItem;
	@FXML
	private VBox logoutItem;
	@FXML
	private TextFlow header;
	
	//private int clickCount = 0;
	public void initialize(){
		DBLocker.unlockDB();
		doStartupServices();
	}
	
	public void doStartupServices(){
		HIDSService.doHIDS();
		TimerAccess.startTimer();
	}
	@FXML
	public void changePage(MouseEvent event) throws IOException {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		Parent root = null;
		if (event.getSource().equals(firewallItem)) {
			root = FXMLLoader.load(getClass().getResource("../basicFirewall/view/BasicFirewallView.fxml"));
		}
		else if (event.getSource().equals(userItem)) {
			root = FXMLLoader.load(getClass().getResource("../userManagement/view/DatabaseTableView.fxml"));
		}
		else if (event.getSource().equals(secureItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/"));
		}
		else if (event.getSource().equals(auditItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/AuditLog.fxml"));
		}
		else if (event.getSource().equals(backupItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/BackupMaker.fxml"));
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
	
	@FXML
	public void scaleUp(MouseEvent event) {
		Timeline timeline = new Timeline();
		KeyValue keyValue1 = new KeyValue(navMenu.scaleXProperty(), 1.2);
		KeyValue keyValue2 = new KeyValue(navMenu.scaleYProperty(), 1.2);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(100), keyValue1, keyValue2);
		timeline.getKeyFrames().addAll(keyFrame);
		timeline.play();
	}
	
	@FXML
	public void scaleDown(MouseEvent event) {
		Timeline timeline = new Timeline();
		KeyValue keyValue1 = new KeyValue(navMenu.scaleXProperty(), 1.0);
		KeyValue keyValue2 = new KeyValue(navMenu.scaleYProperty(), 1.0);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(100), keyValue1, keyValue2);
		timeline.getKeyFrames().addAll(keyFrame);
		timeline.play();
	}

	@FXML
	public void showItems(MouseEvent event) {
		/*
		clickCount += 1;
		if (clickCount % 2 != 0) {
			auditItem.setVisible(true);
			userItem.setVisible(true);
			settingsItem.setVisible(true);
			backupItem.setVisible(true);
			Timeline timeline = new Timeline();
			KeyValue keyValue1 = new KeyValue(navMenu.scaleXProperty(), 0.9);
			KeyValue keyValue2 = new KeyValue(navMenu.scaleYProperty(), 0.9);
			KeyValue auditValue1 = new KeyValue(auditItem.layoutXProperty(), 735.0);
			KeyValue auditValue2 = new KeyValue(auditItem.layoutYProperty(), 270.0);
			KeyValue userValue1 = new KeyValue(userItem.layoutXProperty(), 1070.0);
			KeyValue userValue2 = new KeyValue(userItem.layoutYProperty(), 270.0);
			KeyValue settingsValue1 = new KeyValue(settingsItem.layoutXProperty(), 1070.0);
			KeyValue settingsValue2 = new KeyValue(settingsItem.layoutYProperty(), 500.0);
			KeyValue backupValue1 = new KeyValue(backupItem.layoutXProperty(), 735.0);
			KeyValue backupValue2 = new KeyValue(backupItem.layoutYProperty(), 500.0);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300), auditValue1, auditValue2, userValue1, userValue2, settingsValue1, settingsValue2, backupValue1, backupValue2, keyValue1, keyValue2);
			timeline.getKeyFrames().addAll(keyFrame);
			timeline.play();
		}
		else {
			Timeline timeline = new Timeline();
			KeyValue auditValue1 = new KeyValue(auditItem.layoutXProperty(), 900.0);
			KeyValue auditValue2 = new KeyValue(auditItem.layoutYProperty(), 350.0);
			KeyValue auditValue3 = new KeyValue(auditItem.visibleProperty(), false);
			KeyValue userValue1 = new KeyValue(userItem.layoutXProperty(), 900.0);
			KeyValue userValue2 = new KeyValue(userItem.layoutYProperty(), 350.0);
			KeyValue userValue3 = new KeyValue(userItem.visibleProperty(), false);
			KeyValue settingsValue1 = new KeyValue(settingsItem.layoutXProperty(), 900.0);
			KeyValue settingsValue2 = new KeyValue(settingsItem.layoutYProperty(), 350.0);
			KeyValue settingsValue3 = new KeyValue(settingsItem.visibleProperty(), false);
			KeyValue backupValue1 = new KeyValue(backupItem.layoutXProperty(), 900.0);
			KeyValue backupValue2 = new KeyValue(backupItem.layoutYProperty(), 350.0);
			KeyValue backupValue3 = new KeyValue(backupItem.visibleProperty(), false);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300), auditValue1, auditValue2, auditValue3, userValue1, userValue2, userValue3, settingsValue1, settingsValue2, settingsValue3, backupValue1, backupValue2, backupValue3);
			timeline.getKeyFrames().addAll(keyFrame);
			timeline.play();
		}
		*/
		
		userItem.setVisible(true);
		firewallItem.setVisible(true);
		auditItem.setVisible(true);
		secureItem.setVisible(true);
		backupItem.setVisible(true);
		logoutItem.setVisible(true);
		Timeline timeline = new Timeline();
		KeyValue keyValue1 = new KeyValue(navMenu.visibleProperty(), false);
		KeyValue keyValue2 = new KeyValue(navMenu.disableProperty(), true);
		KeyValue keyValue3 = new KeyValue(closeNavMenu.visibleProperty(), true);
		KeyValue keyValue4 = new KeyValue(closeNavMenu.disableProperty(), false);
		KeyValue keyValue5 = new KeyValue(navMenu.scaleXProperty(), 0.8);
		KeyValue keyValue6 = new KeyValue(navMenu.scaleYProperty(), 0.8);
		KeyValue keyValue7 = new KeyValue(closeNavMenu.scaleXProperty(), 0.8);
		KeyValue keyValue8 = new KeyValue(closeNavMenu.scaleYProperty(), 0.8);
		KeyValue userValue1 = new KeyValue(userItem.layoutXProperty(), 750.0);
		KeyValue userValue2 = new KeyValue(userItem.layoutYProperty(), 260.0);
		KeyValue firewallValue1 = new KeyValue(firewallItem.layoutXProperty(), 900.0);
		KeyValue firewallValue2 = new KeyValue(firewallItem.layoutYProperty(), 170.0);
		KeyValue auditValue1 = new KeyValue(auditItem.layoutXProperty(), 750.0);
		KeyValue auditValue2 = new KeyValue(auditItem.layoutYProperty(), 490.0);
		KeyValue secureValue1 = new KeyValue(secureItem.layoutXProperty(), 1050.0);
		KeyValue secureValue2 = new KeyValue(secureItem.layoutYProperty(), 260.0);
		KeyValue backupValue1 = new KeyValue(backupItem.layoutXProperty(), 900.0);
		KeyValue backupValue2 = new KeyValue(backupItem.layoutYProperty(), 580.0);
		KeyValue logoutValue1 = new KeyValue(logoutItem.layoutXProperty(), 1050.0);
		KeyValue logoutValue2 = new KeyValue(logoutItem.layoutYProperty(), 490.0);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue1, keyValue2, keyValue3, keyValue4, keyValue5, keyValue6, keyValue7, keyValue8, userValue1, userValue2, firewallValue1, firewallValue2, secureValue1, secureValue2, auditValue1, auditValue2, backupValue1, backupValue2, logoutValue1, logoutValue2);
		timeline.getKeyFrames().addAll(keyFrame);
		timeline.play();
	}
	
	@FXML
	public void closeItems(MouseEvent event) {
		Timeline timeline = new Timeline();
		KeyValue keyValue1 = new KeyValue(navMenu.visibleProperty(), true);
		KeyValue keyValue2 = new KeyValue(navMenu.disableProperty(), false);
		KeyValue keyValue3 = new KeyValue(closeNavMenu.visibleProperty(), false);
		KeyValue keyValue4 = new KeyValue(closeNavMenu.disableProperty(), true);
		KeyValue keyValue5 = new KeyValue(closeNavMenu.scaleXProperty(), 1.0);
		KeyValue keyValue6 = new KeyValue(closeNavMenu.scaleYProperty(), 1.0);
		KeyValue keyValue7 = new KeyValue(navMenu.scaleXProperty(), 1.0);
		KeyValue keyValue8 = new KeyValue(navMenu.scaleYProperty(), 1.0);
		KeyValue userValue1 = new KeyValue(userItem.layoutXProperty(), 900.0);
		KeyValue userValue2 = new KeyValue(userItem.layoutYProperty(), 370.0);
		KeyValue userValue3 = new KeyValue(userItem.visibleProperty(), false);
		KeyValue firewallValue1 = new KeyValue(firewallItem.layoutXProperty(), 900.0);
		KeyValue firewallValue2 = new KeyValue(firewallItem.layoutYProperty(), 370.0);
		KeyValue firewallValue3 = new KeyValue(firewallItem.visibleProperty(), false);
		KeyValue auditValue1 = new KeyValue(auditItem.layoutXProperty(), 900.0);
		KeyValue auditValue2 = new KeyValue(auditItem.layoutYProperty(), 370.0);
		KeyValue auditValue3 = new KeyValue(auditItem.visibleProperty(), false);
		KeyValue secureValue1 = new KeyValue(secureItem.layoutXProperty(), 900.0);
		KeyValue secureValue2 = new KeyValue(secureItem.layoutYProperty(), 370.0);
		KeyValue secureValue3 = new KeyValue(secureItem.visibleProperty(), false);
		KeyValue backupValue1 = new KeyValue(backupItem.layoutXProperty(), 900.0);
		KeyValue backupValue2 = new KeyValue(backupItem.layoutYProperty(), 370.0);
		KeyValue backupValue3 = new KeyValue(backupItem.visibleProperty(), false);
		KeyValue logoutValue1 = new KeyValue(logoutItem.layoutXProperty(), 900.0);
		KeyValue logoutValue2 = new KeyValue(logoutItem.layoutYProperty(), 370.0);
		KeyValue logoutValue3 = new KeyValue(logoutItem.visibleProperty(), false);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue1, keyValue2, keyValue3, keyValue4, keyValue5, keyValue6, keyValue7, keyValue8, userValue1, userValue2, userValue3, firewallValue1, firewallValue2, firewallValue3, secureValue1, secureValue2, secureValue3, auditValue1, auditValue2, auditValue3, backupValue1, backupValue2, backupValue3, logoutValue1, logoutValue2, logoutValue3);
		timeline.getKeyFrames().addAll(keyFrame);
		timeline.play();
	}

}
