package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			MediaPlayer mp;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("border.fxml"));
			ScrollPane scroll = new ScrollPane(loader.load());
			scroll.setFitToHeight(true);
			scroll.setFitToWidth(true);
			VBox root = new VBox(scroll);
			Scene scene = new Scene(root, 1200, 795);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			Image icon=new Image("file:images/icon.png");
			mp =new MediaPlayer(new Media(getClass().getClassLoader().getResource("start.mp3").toString()));
			mp.setAutoPlay(true);
			mp.setVolume(0.3);
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("MineSweeper");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
