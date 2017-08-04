package auditLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	private HBox secureItem;
	@FXML
	private HBox auditItem;
	@FXML
	private HBox backupItem;
	@FXML
	private HBox logoutItem;
	@FXML
	private JFXListView<HBox> logListView;
	@FXML
	private JFXComboBox<String> cherStud;
	@FXML
	private JFXComboBox<String> normSusp;
	
	//private boolean openClose = false;
	

	@FXML
	public void initialize() {
		ArrayList<AuditLogModel> dataList = AuditLogModel.getAllData();
		cherStud.getItems().addAll("All", "Teachers", "Students");
		cherStud.setValue("All");
		normSusp.getItems().addAll("All", "Normal", "Suspicious");
		normSusp.setValue("All");
		
		Collections.reverse(dataList);
		for (AuditLogModel aLM : dataList) {
			
			HBox hWrap = new HBox();
			Label dateTimeLbl = new Label(aLM.getDateTime());
			VBox vWrap = new VBox();
			Label ipLbl = new Label(aLM.getIpAddress());
			Label userActLbl = new Label(aLM.getUsername() + " " + aLM.getActivity());
			
			dateTimeLbl.setPrefWidth(600);
			dateTimeLbl.setPrefHeight(10);
			dateTimeLbl.setAlignment(Pos.CENTER);
			dateTimeLbl.setFont(new Font("System", 18));
			ipLbl.setPrefWidth(660);
			ipLbl.setPrefHeight(10);
			ipLbl.setAlignment(Pos.CENTER);
			ipLbl.setFont(new Font("System", 18));
			userActLbl.setPrefWidth(660);
			userActLbl.setPrefHeight(10);
			userActLbl.setAlignment(Pos.CENTER);
			userActLbl.setFont(new Font("System", 18));
			//hWrap.setStyle("-fx-border-color: black");
			HBox.setMargin(dateTimeLbl, new Insets(15,0,0,0));
			
			vWrap.getChildren().add(userActLbl);
			vWrap.getChildren().add(ipLbl);
			hWrap.getChildren().add(dateTimeLbl);
			hWrap.getChildren().add(vWrap);
			logListView.getItems().add(hWrap);
			
			if (aLM.getActivity().equals("Attempted cross-site scripting")) {
				//hWrap.setStyle("-fx-border-color: red");
				dateTimeLbl.setStyle("-fx-text-fill: red");
				ipLbl.setStyle("-fx-text-fill: red");
				userActLbl.setStyle("-fx-text-fill: red");
			}
		}
	}
	
	@FXML
	public void comboSelect(ActionEvent event) {
		ArrayList<String> suspTimeList = new ArrayList<String>();
		suspTimeList.add("14:49:30");
		suspTimeList.add("14:49:45");
		String suspStr = null;
		
		ArrayList<AuditLogModel> dataList = AuditLogModel.getAllData();
		logListView.getItems().clear();
		Collections.reverse(dataList);
		for (AuditLogModel aLM : dataList) {
			
			if (event.getSource().equals(cherStud)) {
				if (cherStud.getValue().equals("All")) {
					if (normSusp.getValue().equals("All")) {
						System.out.println("cherStud: Selected All and All");
						
					}
					else if (normSusp.getValue().equals("Normal")) {
						System.out.println("cherStud: Selected All and Normal");
						if (!aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else if (normSusp.getValue().equals("Suspicious")) {
						System.out.println("cherStud: Selected All and Suspicious");
						for (String str : suspTimeList) {
							suspStr = str;
							if (aLM.getActivity().equals("Attempted cross-site scripting") || aLM.getDateTime().contains(suspStr)) {
								
							}
						}
						/*
						if (aLM.getActivity().equals("Attempted cross-site scripting")) {
						}
						*/
					}
					else {
						System.out.println("cherStud: All - Else");
					}
				}
				else if (cherStud.getValue().equals("Teachers")) {
					if (normSusp.getValue().equals("All")) {
						System.out.println("cherStud: Selected Teachers and All");
						if (aLM.getUsername().equals("Teacher")) {
							
						}
					}
					else if (normSusp.getValue().equals("Normal")) {
						System.out.println("cherStud: Selected Teachers and Normal");
						if (aLM.getUsername().equals("Teacher") && !aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else if (normSusp.getValue().equals("Suspicious")) {
						System.out.println("cherStud: Selected Teachers and Suspicious");
						if (aLM.getUsername().equals("Teacher") && aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else {
						System.out.println("cherStud: Teachers - Else");
					}
				}
				else if (cherStud.getValue().equals("Students")) {
					if (normSusp.getValue().equals("All")) {
						System.out.println("cherStud: Selected Students and All");
						if (aLM.getUsername().equals("Student")) {
							
						}
					}
					else if (normSusp.getValue().equals("Normal")) {
						System.out.println("cherStud: Selected Students and Normal");
						if (aLM.getUsername().equals("Student") && !aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else if (normSusp.getValue().equals("Suspicious")) {
						System.out.println("cherStud: Selected Students and Suspicious");
						if (aLM.getUsername().equals("Student") && aLM.getActivity().equals("Attempted cross-site scripting")) {
							
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
						
					}
					else if (cherStud.getValue().equals("Teachers")) {
						System.out.println("normSusp: Selected All and Teachers");
						if (aLM.getUsername().equals("Teacher")) {
							
						}
					}
					else if (cherStud.getValue().equals("Students")) {
						System.out.println("normSusp: Selected All and Students");
						if (aLM.getUsername().equals("Student")) {
							
						}
					}
					else {
						System.out.println("normSusp: All - Else");
					}
				}
				else if (normSusp.getValue().equals("Normal")) {
					if (cherStud.getValue().equals("All")) {
						System.out.println("normSusp: Selected Normal and All");
						if (!aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else if (cherStud.getValue().equals("Teachers")) {
						System.out.println("normSusp: Selected Normal and Teachers");
						if (aLM.getUsername().equals("Teacher") && !aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else if (cherStud.getValue().equals("Students")) {
						System.out.println("normSusp: Selected Normal and Students");
						if (aLM.getUsername().equals("Student") && !aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else {
						System.out.println("normSusp: Normal - Else");
					}
				}
				else if (normSusp.getValue().equals("Suspicious")) {
					if (cherStud.getValue().equals("All")) {
						System.out.println("normSusp: Selected Suspicious and All");
						if (aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else if (cherStud.getValue().equals("Teachers")) {
						System.out.println("normSusp: Selected Suspicious and Teachers");
						if (aLM.getUsername().equals("Teacher") && aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else if (cherStud.getValue().equals("Students")) {
						System.out.println("normSusp: Selected Suspicious and Students");
						if (aLM.getUsername().equals("Student") && aLM.getActivity().equals("Attempted cross-site scripting")) {
							
						}
					}
					else {
						System.out.println("normSusp: Suspicious - Else");
					}
				}
			}
		}
	}

	@FXML
	public void showSidebar(MouseEvent event) {
		/*
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
		*/
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
		else if (event.getSource().equals(secureItem)) {
			secureItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(auditItem)) {
			auditItem.setStyle("-fx-background-color: #673AB7");
		}
		else if (event.getSource().equals(backupItem)) {
			backupItem.setStyle("-fx-background-color: #673AB7");
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
		else if (event.getSource().equals(secureItem)) {
			secureItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(auditItem)) {
			auditItem.setStyle("-fx-background-color: #9575CD");
		}
		else if (event.getSource().equals(backupItem)) {
			backupItem.setStyle("-fx-background-color: #9575CD");
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
			root = FXMLLoader.load(getClass().getResource("../view/"));
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
}
