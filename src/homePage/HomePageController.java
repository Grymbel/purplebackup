package homePage;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomePageController {
	@FXML
	private VBox navMenu;
	@FXML
	private VBox closeNavMenu;
	@FXML
	private VBox auditItem;
	@FXML
	private VBox userItem;
	@FXML
	private VBox settingsItem;
	@FXML
	private VBox backupItem;
	@FXML
	private TextFlow header;
	
	//private int clickCount = 0;
	
	@FXML
	public void changePage(MouseEvent event) throws IOException {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		Parent root = null;
		if (event.getSource().equals(auditItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/AuditLog.fxml"));
		}
		else if (event.getSource().equals(userItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/DatabaseTableView.fxml"));
		}
		else if (event.getSource().equals(settingsItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/"));
		}
		else if (event.getSource().equals(backupItem)) {
			root = FXMLLoader.load(getClass().getResource("../view/BackupMaker.fxml"));
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
		auditItem.setVisible(true);
		userItem.setVisible(true);
		settingsItem.setVisible(true);
		backupItem.setVisible(true);
		Timeline timeline = new Timeline();
		KeyValue keyValue1 = new KeyValue(navMenu.visibleProperty(), false);
		KeyValue keyValue2 = new KeyValue(navMenu.disableProperty(), true);
		KeyValue keyValue3 = new KeyValue(closeNavMenu.visibleProperty(), true);
		KeyValue keyValue4 = new KeyValue(closeNavMenu.disableProperty(), false);
		KeyValue keyValue5 = new KeyValue(navMenu.scaleXProperty(), 0.8);
		KeyValue keyValue6 = new KeyValue(navMenu.scaleYProperty(), 0.8);
		KeyValue keyValue7 = new KeyValue(closeNavMenu.scaleXProperty(), 0.8);
		KeyValue keyValue8 = new KeyValue(closeNavMenu.scaleYProperty(), 0.8);
		KeyValue auditValue1 = new KeyValue(auditItem.layoutXProperty(), 735.0);
		KeyValue auditValue2 = new KeyValue(auditItem.layoutYProperty(), 270.0);
		KeyValue userValue1 = new KeyValue(userItem.layoutXProperty(), 1070.0);
		KeyValue userValue2 = new KeyValue(userItem.layoutYProperty(), 270.0);
		KeyValue settingsValue1 = new KeyValue(settingsItem.layoutXProperty(), 1070.0);
		KeyValue settingsValue2 = new KeyValue(settingsItem.layoutYProperty(), 500.0);
		KeyValue backupValue1 = new KeyValue(backupItem.layoutXProperty(), 735.0);
		KeyValue backupValue2 = new KeyValue(backupItem.layoutYProperty(), 500.0);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue1, keyValue2, keyValue3, keyValue4, keyValue5, keyValue6, keyValue7, keyValue8, auditValue1, auditValue2, userValue1, userValue2, settingsValue1, settingsValue2, backupValue1, backupValue2);
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
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue1, keyValue2, keyValue3, keyValue4, keyValue5, keyValue6, keyValue7, keyValue8, auditValue1, auditValue2, auditValue3, userValue1, userValue2, userValue3, settingsValue1, settingsValue2, settingsValue3, backupValue1, backupValue2, backupValue3);
		timeline.getKeyFrames().addAll(keyFrame);
		timeline.play();
	}

}
