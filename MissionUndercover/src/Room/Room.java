package Room;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import Player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Room implements Serializable {
	private int people = 1;
	private String id = "12345";
	private String status = "waiting";
	private ArrayList<String> description = new ArrayList<String>(6);

	static String a; // TODO : check where use this property

	private ArrayList<Player> players = new ArrayList<Player>(6);
	private int readys = 1;

	private int time = 0;

	Thread thread = null;

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
		/*
		 * new Thread(new Runnable(){ public void run() { while(players.size()>0) {
		 * if(players.size() == 6) { for(Player p : players) { if (p.getReady())
		 * 
		 * } StartGame(); } } }
		 * 
		 * }).start();
		 */

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

	public void removePlayer(Player player) {
		players.remove(player);
	}

	public void startGame() {
		randomQuestion(players, "雞蛋", "鴨蛋");

		return;
	}

	private void randomQuestion(ArrayList<Player> p, String undercoverWord, String civilianWord) {
		int undercover = 0;
		if (p.size() > 4) {
			undercover = 2;
		} else
			undercover = 1;
		int u1 = (int) (Math.random() * 10) % p.size();
		int u2 = -1;
		while (u2 < 0 || u1 == u1) {
			u2 = (int) (Math.random() * 10) % p.size();
		}
		for (int i = 0; i < p.size(); i++) {
			if (i != u1 && i != u2) {
				p.get(i).setCard(civilianWord);
			} else
				p.get(i).setCard(undercoverWord);
		}

	}

	public void leaveRoom(Player player) {
		players.remove(player);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setTime(int t) {
		time = t;
	}

	public int getTime() {
		return time;
	}

}
