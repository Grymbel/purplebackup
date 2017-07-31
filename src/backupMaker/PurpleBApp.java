package backupMaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PurpleBApp extends Application {
	public Stage primarystage;
	private Stage primaryStage;

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/BackupMaker.fxml"));
			AnchorPane root = loader.load();

			Scene scene = new Scene(root, 1920, 1080);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Purple Backup System");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
