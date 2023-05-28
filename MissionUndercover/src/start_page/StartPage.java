package start_page;
	
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import Player.Player;
import Room.Room;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class StartPage extends Application {
	public static Player player = new Player();
	public static ArrayList<Room> rooms = new ArrayList<Room>();
	public static Room room = new Room();
	@Override
	public void start(Stage primaryStage) {
		// use player.sendMessage to send command to server
		try {
//			player.setName("Charles");
//			player.setCoin(1);
//			player.setExp(1);
//			player.setlevel(1);
//			System.out.println("finish");
			System.out.println("start");
			Parent root = FXMLLoader.load(getClass().getResource("/RoomChoice/RoomChoice.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("/RoomChoice/RoomChoice.fxml"));
			Scene scene = new Scene(root,600,600);
			scene.getStylesheets().add(getClass().getResource("StartPage.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		launch(args);
	}
}
