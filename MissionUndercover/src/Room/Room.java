package Room;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import Player.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Room implements Serializable {
	///private static final long serialVersionUID = 6529685098267757690L;
	private int people = 1;
	private String id = "12345";
	private String status = "waiting";
	private String gameStatus= "waiting";
	
	private ArrayList<String> description = new ArrayList<String>(6);

	static String a; // TODO : check where use this property

	private ArrayList<Player> players = new ArrayList<Player>();
	private int readys = 1;
	
	private int time = 0;
	private ArrayList<Boolean> alive = new ArrayList<Boolean>();
	private ArrayList<Integer> votes = new ArrayList<Integer>();
	private int undercover ;
	
	
	

	Thread thread = null;

	public Room() {
		// check this!
		id = "123456";
		String status = "waiting";
		for(int i =0; i<4 ;i++) {
			alive.add(true);
		}
		players = new ArrayList<Player>();
		// read message from socket
		System.out.println("ROOM cons");
		new Thread(new Runnable() {
			public void run()
			{
				try {
					while(true) {
					Thread.sleep(1000);
					if(time > 0) {
						time --;
						System.out.print(time);
						if(time == 0) {
							if(gameStatus.equals("description") ) {
								gameStatus = "Vote";
							}
							else if (gameStatus.equals("Vote") ) {
								/*int alives = 0;
								int undercoverCnt = 0;
								int civilianCnt = 0;
								for(int i = 0;i < alive.size();i++) {
									if (alive.get(i))
										alives++;
									if (players.get(i).getIsUnderCover()) {
										undercoverCnt++;
									}else
										civilianCnt++;
								}
								if(civilianCnt <=2 undercoverCnt >=1) {
									/*TODO undercover win*/
								/*	finishGame();
								}
								else if (undercoverCnt ==0){
									//civilian win
									 * finishGame();
								}
								else {
									gameStatus = "description";
								}*/
								
								
							}
						}
					}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();


	}

	public Room(String id, String status) {
		people = 1;
		this.id = id;
		this.status = status;
		players = new ArrayList<Player>();
		System.out.println("ROOM cons2");
		for(int i =0; i<4 ;i++) {
			alive.add(true);
		}
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
		for(Player p: players) {
			if(player.getName().equals(p.getName())) {
				players.remove(p);
			}
		}
	}

	public void startGame() {
		System.out.println("StartGame");
		randomQuestion(players, "雞蛋", "鴨蛋");
		System.out.println("leave random");
		for(int i =0 ;i < 4;i++) {
			//alive.add(true);
			votes.add(0);
		}

		return;
	}

	private void randomQuestion(ArrayList<Player> p, String undercoverWord, String civilianWord) {
		int u1 = (int) (Math.random() * 10) % 4;
		for (int i = 0; i < p.size(); i++) {
			if (i != u1) {
				p.get(i).setCard(civilianWord);
				p.get(i).setIsUnderCover(false);
			} else {
				p.get(i).setCard(undercoverWord);
				p.get(i).setIsUnderCover(true);
				undercover = u1;
			}
		}

	}
	public int getUndercover() {
		return undercover;
	}

	public void leaveRoom(Player player) {
		for(Player p : players) {
			if (p.getName().equals(player.getName())) {
				players.remove(p);
			}
		}

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
	
	public void addTime(int t) {
		time += t;
	}
	
	public void setGameStatus(String s) {
		gameStatus = s;
	}
	
	public String getGameStatus() {
		return gameStatus;
	}
	private void finishGame() {
		gameStatus = "waiting";
	}
	
	public void addVote(int k) {
		votes.set(k, votes.get(k) + 1);
	}
	public void voteReset() {
		for(int i = 0 ;i < votes.size();i++) {
			votes.set(i, 0);
		}
	}
	public int voteSum() {
		int sum =0 ;
		for(int i = 0 ; i < votes.size();i++) {
			sum += votes.get(i);
		}
		return sum;
	}
	public int getHighestVote() {
		int highest = 0;
		for(int i = 0 ; i < votes.size();i++) {
			if(votes.get(highest) < votes.get(i)) {
				highest = i;
			}
		}
		return highest;
	}
	
	public void setAlive(int k , Boolean b) {
		alive.set(k, b);
	}
	public int getAlivesNum() {
		int sum = 0;
		for(Boolean b : alive) {
			if(b==true) sum++;
		}
		return sum;
	}
	public ArrayList<Boolean> getAlive(){
		return alive;
	}
	public Boolean checkUnderCoverIsAlive() {
		if(alive.get(undercover))
			return true;
		else
			return false;
	}
}
