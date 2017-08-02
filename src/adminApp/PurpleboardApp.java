 package adminApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PurpleboardApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		root = FXMLLoader.load(getClass().getResource("../view/AuditLog.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("/images/BasLogo.png"));
		primaryStage.setTitle("Purpleboard");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
