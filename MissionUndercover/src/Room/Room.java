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

	static String a;

	private ArrayList<Player> players;
	private int readys = 1;



	public Room() {
		// check this!
		id = "123456";
		String status = "waiting";

		players = new ArrayList<Player>();
		// read message from socket
		System.out.println("ROOM cons");

	}

	public Room(String id, String status) {
		people = 1;
		this.id = id;
		this.status = status;
		players = new ArrayList<Player>();
		System.out.println("ROOM cons2");

	}

	public String getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public int getPeople() {
		return people;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean addPlayer(Player player) {
		// if add successfully then return true
		// else return false
		if (this.players.size() < 6) {
			this.players.add(player);
			people = this.players.size();
			System.out.println("add Room Sucessfully");

			return true;
		} else {
			System.out.println("the room is full");
			return false;
		}
	}


	private void startGame() {
		System.out.println("StartGame");
		return;
	}

	public void ready(ActionEvent e) {
		/*Player player = new Player(id, people, people, people);
		if (player.getReady())
			player.setReady(false);
		else
			player.setReady(true);
		return;*/
	}

	

}
