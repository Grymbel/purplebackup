package backupScheduler;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BackupSController {
	

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
	    private TableView<?> bsTable;

	    @FXML
	    private TableColumn<?, ?> colETA;

	    @FXML
	    private TableColumn<?, ?> colScheduled;

	    @FXML
	    private TableColumn<?, ?> colUser;

	    @FXML
	    private TableColumn<?, ?> colCloud;

	    @FXML
	    private TableColumn<?, ?> colWeb;

	    @FXML
	    private TableColumn<?, ?> colAudit;

	    @FXML
	    private TextField taDays;

	    @FXML
	    private TextField taHours;

	    @FXML
	    private TextField taMinutes;

	    @FXML
	    private JFXDatePicker datePicker;

	    @FXML
	    void doAddBackup(ActionEvent event) {

	    }

	    @FXML
	    void doSelAudit(ActionEvent event) {

	    }

	    @FXML
	    void doSelCloud(ActionEvent event) {

	    }

	    @FXML
	    void doSelUserData(ActionEvent event) {

	    }

	    @FXML
	    void doSelWeb(ActionEvent event) {

	    }
	    
	    @FXML 
	    void goBack(ActionEvent event){
	    	try {
	        	Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
	    		Parent root;
	    			root = FXMLLoader.load(getClass().getResource("../view/BackupMaker.fxml"));
	    		stage.setScene(new Scene(root,1920,1080));
	     	    stage.show();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	    }

	}

