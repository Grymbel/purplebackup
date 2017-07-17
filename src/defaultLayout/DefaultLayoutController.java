package defaultLayout;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DefaultLayoutController {
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
		
		//XZ's Feature
		
		else if (event.getSource().equals(backupItem)) {
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
