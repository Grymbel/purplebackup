package login;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {
	@FXML
	private JFXTextField userID;
	
	@FXML
	private JFXPasswordField passID;
	
	@FXML
	private Label errorMessage;
	
	@FXML
	//Visibility = false
	private JFXButton btnLogin;
	
	@FXML
	//Visibility = false
	private JFXSpinner loginSpinner;
	
	@FXML
	public void initialize() {
		userID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            	errorMessage.setVisible(false);
            }
        });
		passID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            	errorMessage.setVisible(false);
            }
        });
	}
	
	@FXML
	public void goToHomePage(ActionEvent event) {
		String Username = userID.getText();
		String Password = passID.getText();
		
		if (Username.equals(null) || Username.equals("")) {
			errorMessage.setVisible(true);
			System.out.println("Username is null");
		}
		else if (Password.equals(null) || Password.equals("")) {
			errorMessage.setVisible(true);
			System.out.println("Password is null");
		}
		else {
			if (Username.equals("Admin") && Password.equals("Admin")) {
				System.out.println("Username and Password is correct");
				btnLogin.setDisable(true);
				btnLogin.setVisible(false);
				loginSpinner.setVisible(true);
				Timeline timeline = new Timeline();
				KeyFrame keyFrame = new KeyFrame(
					Duration.seconds(2), 
					first -> {
						Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
						Parent root = null;
						try {
							root = (Parent)FXMLLoader.load(getClass().getResource("../view/BackupMaker.fxml"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						Screen screen = Screen.getPrimary();
						Rectangle2D bounds = screen.getVisualBounds();
						stage.setX(bounds.getMinX());
						stage.setY(bounds.getMinY());
						stage.setWidth(bounds.getWidth());
						stage.setHeight(bounds.getHeight());
						stage.setMaximized(true);
						stage.setScene(new Scene(root));
				 	    stage.show();
					}
				);
		    	timeline.getKeyFrames().addAll(keyFrame);
				timeline.play();
			}
			else {
				errorMessage.setVisible(true);
			}
		}
	}
	
	@FXML
	public void checkEnter(KeyEvent event) {
		if (event.getCode().getName().equals("Enter")) {
			String Username = userID.getText();
			String Password = passID.getText();
			
			if (Username.equals(null) || Username.equals("")) {
				errorMessage.setVisible(true);
				errorMessage.setText("Please enter your username");
				System.out.println("Username is null");
			}
			else if (Password.equals(null) || Password.equals("")) {
				errorMessage.setVisible(true);
				errorMessage.setText("Please enter your password");
				System.out.println("Password is null");
			}
			else {
				if (Username.equals("Admin") && Password.equals("Admin")) {
					System.out.println("Username and Password is correct");
					btnLogin.setDisable(true);
					btnLogin.setVisible(false);
					loginSpinner.setVisible(true);
					Timeline timeline = new Timeline();
					KeyFrame keyFrame = new KeyFrame(
						Duration.seconds(2), 
						first -> {
							Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
							Parent root = null;
							try {
								root = (Parent)FXMLLoader.load(getClass().getResource("../view/BackupMaker.fxml"));
							} catch (IOException e) {
								e.printStackTrace();
							}
							Screen screen = Screen.getPrimary();
							Rectangle2D bounds = screen.getVisualBounds();
							stage.setX(bounds.getMinX());
							stage.setY(bounds.getMinY());
							stage.setWidth(bounds.getWidth());
							stage.setHeight(bounds.getHeight());
							stage.setMaximized(true);
							stage.setScene(new Scene(root));
					 	    stage.show();
						}
					);
			    	timeline.getKeyFrames().addAll(keyFrame);
					timeline.play();
				}
				else {
					errorMessage.setVisible(true);
					errorMessage.setText("Username or password is incorrect");
				}
			}
		}
	}
}
