package Room;

import java.io.IOException;
import java.util.ArrayList;

import Player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Room {
	private int people = 1;
	private String id = "12345";
	private String status = "waiting";
	
	private ArrayList<Player> players;
	
	public Room(String id, String status) {
		people = 1;
		this.id = id; 
		this.status = status;
		players = new ArrayList<Player>();
	}
	
	public String getId() { return id;}
	public String getStatus() {return status;}
	public int getPeople() {return people;}
	
	public void setId(String id ) {this.id = id;}
	public void setStatus(String status) {this.status = status;}
	
	public boolean addPlayer(Player player) {
		// if add successfully then return true 
		// else return false
		if (this.players.size() <6) {
			this.players.add(player);
			people = this.players.size();
			System.out.println("add Room Sucessfully");
			
			return true;
		}
		else {
			System.out.println("the room is full");
			return false;
		}
	}
	
	public void goBackScene(ActionEvent event) throws IOException {
		/*for(Player i : players) {
			if(i.getName().equals("Charles")) {
				players.remove(i);  // check if it is removed correctly	
				break;
			}
		}*/
		System.out.println("go back");
		
		people = players.size();
		Parent root = FXMLLoader.load(getClass().getResource("/RoomChoice/RoomChoice.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		System.out.println("switch to scene RoomList");
	}
	
	
	private void startGame() {
		
		//game start
		System.out.println("StartGame");
		return;
	}
	
	public void ready(ActionEvent e) {
		return;
	}
}
