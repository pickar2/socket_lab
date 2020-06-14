package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/root.fxml"));

		primaryStage.setTitle("Socket lab");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	@Override
	public void stop() {
		Controller.INSTANCE.inputClient.running = false;
		Controller.INSTANCE.calcClient.running = false;
		Controller.INSTANCE.calcServer.running = false;
		Controller.INSTANCE.dataServer.running = false;
		Controller.INSTANCE.threadPool.shutdown();
	}
}