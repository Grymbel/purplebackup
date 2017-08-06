package auditLog;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import backupScheduler.TimerAccess;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import userManagement.dao.DatabaseDAO;
import zipper.DBLocker;

public class AuditLogController {
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
	private JFXListView<HBox> logListView;
	@FXML
	private JFXComboBox<String> cherStud;
	@FXML
	private JFXComboBox<String> normSusp;
	
	@FXML
	public void initialize() throws ParseException {
		ArrayList<AuditLogModel> dataList = AuditLogModel.getAllData();
		cherStud.getItems().addAll("All", "Teachers", "Students");
		cherStud.setValue("All");
		normSusp.getItems().addAll("All", "Normal", "Suspicious");
		normSusp.setValue("All");
		
		String startTimeStr = "01:00 AM";
		Date startTime = new SimpleDateFormat("hh:mm a").parse(startTimeStr);
		Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(startTime);
	    Date a = calendar1.getTime();
	    
		String endTimeStr = "06:00 AM";
		Date endTime = new SimpleDateFormat("hh:mm a").parse(endTimeStr);
		Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(endTime);
	    Date b = calendar2.getTime();
		
		Collections.reverse(dataList);
		String timeStr = null;
		for (AuditLogModel aLM : dataList) {
			aLM.getDateTime();
			timeStr = aLM.getDateTime().substring(11);
			Date d = new SimpleDateFormat("hh:mm a").parse(timeStr);
			Calendar c = Calendar.getInstance();
		    c.setTime(d);
		    Date y = c.getTime();
		    
			HBox hWrap = new HBox();
			Label dateTimeLbl = new Label(aLM.getDateTime());
			VBox vWrap1 = new VBox();
			VBox vWrap2 = new VBox();
			Label ipLbl = new Label(aLM.getIpAddress());
			Label userActLbl = new Label(aLM.getUsername() + " " + aLM.getActivity());
			Label locationLbl = new Label(aLM.getLocation());
			
			dateTimeLbl.setPrefWidth(600);
			dateTimeLbl.setPrefHeight(10);
			dateTimeLbl.setAlignment(Pos.CENTER);
			dateTimeLbl.setFont(new Font("System", 18));
			userActLbl.setPrefWidth(600);
			userActLbl.setPrefHeight(10);
			userActLbl.setAlignment(Pos.CENTER);
			userActLbl.setFont(new Font("System", 18));
			ipLbl.setPrefWidth(660);
			ipLbl.setPrefHeight(10);
			ipLbl.setAlignment(Pos.CENTER);
			ipLbl.setFont(new Font("System", 18));
			locationLbl.setPrefWidth(660);
			locationLbl.setPrefHeight(10);
			locationLbl.setFont(new Font("System", 18));
			locationLbl.setAlignment(Pos.CENTER);
			//HBox.setMargin(dateTimeLbl, new Insets(15,0,0,0));
			//HBox.setMargin(locationLbl, new Insets(15,0,0,0));
			
			vWrap1.getChildren().add(dateTimeLbl);
			vWrap1.getChildren().add(userActLbl);
			vWrap2.getChildren().add(ipLbl);
			vWrap2.getChildren().add(locationLbl);
			hWrap.getChildren().add(vWrap1);
			hWrap.getChildren().add(vWrap2);
			logListView.getItems().add(hWrap);
			
			if (aLM.getActivity().equals("Attempted cross-site scripting")) {
				dateTimeLbl.setStyle("-fx-text-fill: red");
				ipLbl.setStyle("-fx-text-fill: red");
				userActLbl.setStyle("-fx-text-fill: red");
				locationLbl.setStyle("-fx-text-fill: red");
				Tooltip tooltip = new Tooltip("Attempted cross-site scripting");
				aLM.hackTooltipStartTiming(tooltip);
				dateTimeLbl.setTooltip(tooltip);
				ipLbl.setTooltip(tooltip);
				userActLbl.setTooltip(tooltip);
				locationLbl.setTooltip(tooltip);
			}
			else if (y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully")) {
				dateTimeLbl.setStyle("-fx-text-fill: red");
				ipLbl.setStyle("-fx-text-fill: red");
				userActLbl.setStyle("-fx-text-fill: red");
				locationLbl.setStyle("-fx-text-fill: red");
				Tooltip tooltip = new Tooltip("Suspicious logging time");
				aLM.hackTooltipStartTiming(tooltip);
				dateTimeLbl.setTooltip(tooltip);
				ipLbl.setTooltip(tooltip);
				userActLbl.setTooltip(tooltip);
				locationLbl.setTooltip(tooltip);
			}
		}
	}
	
	@FXML
	public void comboSelect(ActionEvent event) throws ParseException, SQLException, ClassNotFoundException {
		String startTimeStr = "01:00 AM";
		Date startTime = new SimpleDateFormat("hh:mm a").parse(startTimeStr);
		Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(startTime);
	    Date a = calendar1.getTime();
	    
		String endTimeStr = "06:00 AM";
		Date endTime = new SimpleDateFormat("hh:mm a").parse(endTimeStr);
		Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(endTime);
	    Date b = calendar2.getTime();
		
		ArrayList<AuditLogModel> dataList = AuditLogModel.getAllData();
		logListView.getItems().clear();
		Collections.reverse(dataList);
		String timeStr = null;
		DatabaseDAO dba = new DatabaseDAO(1);
		for (AuditLogModel aLM : dataList) {
			String sqlline = "SELECT UserID FROM Login WHERE Username = ?;";
			ResultSet login = dba.getDatabaseData(sqlline, aLM.getUsername());
			
			aLM.getDateTime();
			timeStr = aLM.getDateTime().substring(11);
			Date d = new SimpleDateFormat("hh:mm a").parse(timeStr);
			Calendar c = Calendar.getInstance();
		    c.setTime(d);
		    Date y = c.getTime();
		    
			if (event.getSource().equals(cherStud)) {
				if (cherStud.getValue().equals("All")) {
					if (normSusp.getValue().equals("All")) {
						System.out.println("cherStud: Selected All and All");
						createListItems(aLM, a, b, y);
					}
					else if (normSusp.getValue().equals("Normal")) {
						System.out.println("cherStud: Selected All and Normal");
						if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully")) == false) {
							createListItems(aLM, a, b, y);
						}
					}
					else if (normSusp.getValue().equals("Suspicious")) {
						System.out.println("cherStud: Selected All and Suspicious");
						if (aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully")) {
							createListItems(aLM, a, b, y);
						}
					}
					else {
						System.out.println("cherStud: All - Else");
					}
				}
				else if (cherStud.getValue().equals("Teachers")) {
					if (normSusp.getValue().equals("All")) {
						System.out.println("cherStud: Selected Teachers and All");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) > 10) {
								createListItems(aLM, a, b, y);
							}
						}
					}
					else if (normSusp.getValue().equals("Normal")) {
						System.out.println("cherStud: Selected Teachers and Normal");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) > 10) {
								if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully")) == false) {
									createListItems(aLM, a, b, y);
								}
							}
						}
					}
					else if (normSusp.getValue().equals("Suspicious")) {
						System.out.println("cherStud: Selected Teachers and Suspicious");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) > 10) {
								if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully"))) {
									createListItems(aLM, a, b, y);
								}
							}
						}
					}
					else {
						System.out.println("cherStud: Teachers - Else");
					}
				}
				else if (cherStud.getValue().equals("Students")) {
					if (normSusp.getValue().equals("All")) {
						System.out.println("cherStud: Selected Students and All");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) <= 10) {
								createListItems(aLM, a, b, y);
							}
						}
						
					}
					else if (normSusp.getValue().equals("Normal")) {
						System.out.println("cherStud: Selected Students and Normal");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) <= 10) {
								if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully")) == false) {
									createListItems(aLM, a, b, y);
								}
							}
						}
					}
					else if (normSusp.getValue().equals("Suspicious")) {
						System.out.println("cherStud: Selected Students and Suspicious");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) <= 10) {
								if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully"))) {
									createListItems(aLM, a, b, y);
								}
							}
						}
					}
					else {
						System.out.println("cherStud: Students - Else");
					}
				}
			}
			else if (event.getSource().equals(normSusp)) {
				if (normSusp.getValue().equals("All")) {
					if (cherStud.getValue().equals("All")) {
						System.out.println("normSusp: Selected All and All");
						createListItems(aLM, a, b, y);
					}
					else if (cherStud.getValue().equals("Teachers")) {
						System.out.println("normSusp: Selected All and Teachers");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) > 10) {
								createListItems(aLM, a, b, y);
							}
						}
					}
					else if (cherStud.getValue().equals("Students")) {
						System.out.println("normSusp: Selected All and Students");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) <= 10) {
								createListItems(aLM, a, b, y);
							}
						}
					}
					else {
						System.out.println("normSusp: All - Else");
					}
				}
				else if (normSusp.getValue().equals("Normal")) {
					if (cherStud.getValue().equals("All")) {
						System.out.println("normSusp: Selected Normal and All");
						if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully")) == false) {
							createListItems(aLM, a, b, y);
						}
					}
					else if (cherStud.getValue().equals("Teachers")) {
						System.out.println("normSusp: Selected Normal and Teachers");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) > 10) {
								if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully")) == false) {
									createListItems(aLM, a, b, y);
								}
							}
						}
					}
					else if (cherStud.getValue().equals("Students")) {
						System.out.println("normSusp: Selected Normal and Students");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) <= 10) {
								if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully")) == false) {
									createListItems(aLM, a, b, y);
								}
							}
						}
					}
					else {
						System.out.println("normSusp: Normal - Else");
					}
				}
				else if (normSusp.getValue().equals("Suspicious")) {
					if (cherStud.getValue().equals("All")) {
						System.out.println("normSusp: Selected Suspicious and All");
						if (aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b)) {
							createListItems(aLM, a, b, y);
						}
					}
					else if (cherStud.getValue().equals("Teachers")) {
						System.out.println("normSusp: Selected Suspicious and Teachers");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) > 10) {
								if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully"))) {
									createListItems(aLM, a, b, y);
								}
							}
						}
					}
					else if (cherStud.getValue().equals("Students")) {
						System.out.println("normSusp: Selected Suspicious and Students");
						if (login.next()) {
							if (Integer.parseInt(login.getString("UserID")) <= 10) {
								if ((aLM.getActivity().equals("Attempted cross-site scripting") || y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully"))) {
									createListItems(aLM, a, b, y);
								}
							}
						}
					}
					else {
						System.out.println("normSusp: Suspicious - Else");
					}
				}
			}
		}
	}
	
	public void createListItems(AuditLogModel aLM, Date a, Date b, Date y) {
		HBox hWrap = new HBox();
		Label dateTimeLbl = new Label(aLM.getDateTime());
		VBox vWrap1 = new VBox();
		VBox vWrap2 = new VBox();
		Label ipLbl = new Label(aLM.getIpAddress());
		Label userActLbl = new Label(aLM.getUsername() + " " + aLM.getActivity());
		Label locationLbl = new Label(aLM.getLocation());
		
		dateTimeLbl.setPrefWidth(600);
		dateTimeLbl.setPrefHeight(10);
		dateTimeLbl.setAlignment(Pos.CENTER);
		dateTimeLbl.setFont(new Font("System", 18));
		userActLbl.setPrefWidth(600);
		userActLbl.setPrefHeight(10);
		userActLbl.setAlignment(Pos.CENTER);
		userActLbl.setFont(new Font("System", 18));
		ipLbl.setPrefWidth(660);
		ipLbl.setPrefHeight(10);
		ipLbl.setAlignment(Pos.CENTER);
		ipLbl.setFont(new Font("System", 18));
		locationLbl.setPrefWidth(660);
		locationLbl.setPrefHeight(10);
		locationLbl.setFont(new Font("System", 18));
		locationLbl.setAlignment(Pos.CENTER);
		//HBox.setMargin(dateTimeLbl, new Insets(15,0,0,0));
		//HBox.setMargin(locationLbl, new Insets(15,0,0,0));
		
		vWrap1.getChildren().add(dateTimeLbl);
		vWrap1.getChildren().add(userActLbl);
		vWrap2.getChildren().add(ipLbl);
		vWrap2.getChildren().add(locationLbl);
		hWrap.getChildren().add(vWrap1);
		hWrap.getChildren().add(vWrap2);
		logListView.getItems().add(hWrap);
		
		if (aLM.getActivity().equals("Attempted cross-site scripting")) {
			dateTimeLbl.setStyle("-fx-text-fill: red");
			ipLbl.setStyle("-fx-text-fill: red");
			userActLbl.setStyle("-fx-text-fill: red");
			locationLbl.setStyle("-fx-text-fill: red");
			Tooltip tooltip = new Tooltip("Attempted cross-site scripting");
			aLM.hackTooltipStartTiming(tooltip);
			dateTimeLbl.setTooltip(tooltip);
			ipLbl.setTooltip(tooltip);
			userActLbl.setTooltip(tooltip);
			locationLbl.setTooltip(tooltip);
		}
		else if (y.after(a) && y.before(b) && aLM.getActivity().equals("logged in successfully")) {
			dateTimeLbl.setStyle("-fx-text-fill: red");
			ipLbl.setStyle("-fx-text-fill: red");
			userActLbl.setStyle("-fx-text-fill: red");
			locationLbl.setStyle("-fx-text-fill: red");
			Tooltip tooltip = new Tooltip("Suspicious logging time");
			aLM.hackTooltipStartTiming(tooltip);
			dateTimeLbl.setTooltip(tooltip);
			ipLbl.setTooltip(tooltip);
			userActLbl.setTooltip(tooltip);
			locationLbl.setTooltip(tooltip);
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
