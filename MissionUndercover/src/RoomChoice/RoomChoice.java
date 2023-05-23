package RoomChoice;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Player.Player;
import Room.Room;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import start_page.StartPage;

public class RoomChoice implements Initializable{

	@FXML
	private TableView<Room> tableView;
	@FXML
	private TableColumn<Room,Integer> people;
	@FXML
	private TableColumn<Room,String> RoomId;
	@FXML
	private TableColumn<Room,String> status;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		RoomId.setCellValueFactory(new PropertyValueFactory<Room, String>("id"));
		people.setCellValueFactory(new PropertyValueFactory<Room, Integer>("people"));
		status.setCellValueFactory(new PropertyValueFactory<Room, String>("status"));
		// TODO:Get Rooms from server
		//StartPage.player.sendMessage("GetRooms");
	}
	
	@FXML
	public void createRoom(ActionEvent e) throws IOException {
		
		//Room room = new Room("456","running"); // modify here to set Room status
		//ObservableList<Room> rooms = tableView.getItems();
		//room.addPlayer(new Player("Charles",  1, 1, 1));  // need to be modified
		//rooms.add(room);
		
		//tableView.setItems(rooms);
		StartPage.player.sendMessage("CreateRoom");
		Parent root = FXMLLoader.load(getClass().getResource("/Room/Room.fxml"));
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		System.out.println("switch to scene 2");
		System.out.println("HIHIHI");
	}
	
	@FXML
	public void addRoom(ActionEvent e) throws IOException{
		int selectID = tableView.getSelectionModel().getSelectedIndex();
		Room room = tableView.getItems().get(selectID);
		//room.addPlayer(new Player("Shang",2,2,2));
		tableView.refresh();
		
		Parent root = FXMLLoader.load(getClass().getResource("/Room/Room.fxml"));
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		System.out.println("switch to scene 2");
	}
	
	
	
	
	
}
