package userManagement.controller;

import javafx.fxml.FXML;

import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import userManagement.dao.DatabaseDAO;

public class ApplyChangesPopupController {
	@FXML
	private TextArea sqlTextArea;
	@FXML
	private JFXButton cancelBtn;
	@FXML
	private JFXButton confirmBtn;

	public void initialize(){
		for(String s:DatabaseTableViewController.logs){
			sqlTextArea.appendText(s + "\n");
		}
	}
	
	@FXML
	public void stageClose(ActionEvent event) {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void confirmSubmit(ActionEvent event) throws ClassNotFoundException, SQLException {
		DatabaseDAO dao = new DatabaseDAO(0);
		for(String s:DatabaseTableViewController.logs){
			dao.updateDatabaseData(s);
		}
		DatabaseTableViewController.logs.clear();
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
