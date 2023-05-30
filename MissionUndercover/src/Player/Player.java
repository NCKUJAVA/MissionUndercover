package Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import Room.Room;
import Room.RoomUIController;
import start_page.StartPage;

public class Player implements Serializable{
	private int coin = 0;
	private String account="";	
	private String name = "";
	private int level = 1;
	private int exp = 0;
	private int[] items = {0,0,0,0};
	private Boolean ready = false;
	private Boolean isVote = false;
	private transient Socket socket ;
	private String chatRoom = "";
	private String roomId  = "";
	private String card = "";
	private Boolean isUnderCover = false;
//	BufferedReader in;
	private String description = "";
	transient ObjectInputStream in;
	private Boolean alive = true;
	
//	PrintWriter out;
	transient ObjectOutputStream out;
	private String now_string="";
	public Player(Object[] player_value)
	{
		this.account=(String) player_value[0];
		this.name=(String) player_value[1];
		this.level= (int) player_value[2];
		this.exp= (int) player_value[3];
		this.coin= (int) player_value[4];
		for(int i=0;i<items.length;i++)
		{
			items[i] = (int) player_value[i+5];
		}
//		items[0] = (int) player_value[5];
//		items[1] = (int) player_value[6];
//		items[2] = (int) player_value[7];
//		items[3] = (int) player_value[8];
		
				
	}
	public Player() {
		//System.out.println("player()");
		try {
			System.out.println("Player constructor");
			socket = new Socket("127.0.0.1", 8000);//TODO: check server ip     connect to server
			// out is used to send message to server;
//			out = new PrintWriter(socket.getOutputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			// in is used to get message from server
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			in = new ObjectInputStream(socket.getInputStream());
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("new THREAD---- ----------------;");
						while (true) {
							System.out.println("thread waiting message");
//							String s = in.readLine();
							String s = (String) in.readObject();
							System.out.println("get command-======== :" + s);
							//String s = in.readLine();
							//System.out.println("player:"+s);
							if (s.contains("Chat/")) {
								//TODO: write message to chatRoom
//								s = s.substring(5);
								chatRoom = chatRoom + s.split("/")[1] + "\n";
								System.out.println("CHAT: " + chatRoom);
							}
							else if (s.contains("gamestatus:")){
								StartPage.room.setGameStatus(s.split(":")[1]);
							}
							else if (s.contains("Undercover WIN")) {
								undercoverWIN();
							}
							else if (s.contains("Civilian WIN")) {
								civilianWIN();
							}
							else if (s.contains("returnRoom:")) {
								//TODO : setitems to theroom instead of room = in.readobject()
								returnRoom();
								
							}
							else if (s.contains("getout:")) {
								getout(s);
							}
							else if (s.contains("AddRoom:")) {
								addRoom();
								//StartPage.room.addPlayer(StartPage.player);
							}
							else if (s.contains("roominfo")){
								StartPage.room.setId(s.split("/")[1]);
							}
							else if (s.contains("AddTime:5")) {
								StartPage.room.addTime(5);
							}
							else if (s.contains("setTime:")) {
								String[] tempS = s.split(":");
								StartPage.room.setTime(Integer.parseInt(tempS[1]));
							}
							else if (s.contains("next Round")) {
								for(Player p : StartPage.room.getPlayers()) {
									p.setDescription("");
								}
								StartPage.player.setIsVote(false);
								StartPage.player.setDescription("");
								StartPage.room.setGameStatus("Description");
							}
							
							else if (s.contains("GetRooms")){
								getRooms();
							}
							/*else if (s.contains("playerAddroom/")) {
								playerAddRoom(s);
							}*/
							else if (s.contains("question:")) {
								String[] tempS = s.split(":");
								StartPage.player.setCard(tempS[1]);
								
							}
							else if(s.contains("Description:")) {
								// Description/Name/Des
								String[] msg = s.split(":");
								for(Player p: StartPage.room.getPlayers()) {
									if(p.getName().equals(msg[1])) {
										p.setDescription(msg[2]);
									}
								}
							}
							else if (s.contains("You OUT")) {
								out();
							}
							else if (s.contains("LogIn successfully"))
							{
								now_string=s;
								System.out.println("player:"+"K"+" "+s);
							}else if(s.contains("LogIn failed"))
							{
								now_string=s;
								System.out.println("player:"+"G"+" "+s);
							}else if(s.contains("SignUp info"))
							{
								now_string=s;
								System.out.println("Player: " +now_string);
							}
							else if(s.contains("Auth:OK"))
							{
								now_string=s;
								System.out.println("player:"+now_string);
							}else if(s.contains("Forgot:"))
							{
								if(s.contains("OK"))
								{
									now_string=s;
								}else if(s.contains("G"))
								{
									now_string=s;
								}
							}else if(s.contains("Answer:"))
							{
								if(s.contains("OK"))
								{
									now_string=s;
								}else if(s.contains("G"))
								{
									now_string=s;
								}
							}else if(s.contains("NewPassword:"))
							{
								now_string=s;
							}
							//else if (s.contains("command:content"))
							else 
							{
								System.out.println("ERROR MESSAGE OR Check spelling");
							}
						}
					} catch (IOException e) {
						System.out.println("ERROR HERE");
						e.printStackTrace();
					}
					catch(Exception ee) {
						System.out.println("errror rrrr");
						ee.printStackTrace();
					}
				}
			}).start();
		} catch (IOException e1) {
			
			// TODO Auto-generated catch block
			try {
				out.close();
				in.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			e1.printStackTrace();
		}

	}
	public Player(String name, int level, int exp, int coin){
		this.coin = coin;
		this.level = level;
		this.name = name;
		this.exp = exp;
	}
	
	private void getout(String s) {
		ArrayList<Player> players = StartPage.room.getPlayers();
		for(int i = 0 ; i < players.size();i++) {
			if(players.get(i).getName().equals(s.split(":")[1])) {
				StartPage.room.setAlive(i, false);
				break;
			}
		}
	}
	private void undercoverWIN() {
		StartPage.room.setGameStatus("Undercover WIN");
	}
	private void civilianWIN() {
		StartPage.room.setGameStatus("Civilian WIN");
	}
	private void out() {
		StartPage.player.setAlive(false);
	}
	private void returnRoom() {
		try {
			Room r = (Room) in.readObject();
			StartPage.room.setId(r.getId());
			StartPage.room.setStatus(r.getStatus());
			StartPage.room.setGameStatus(r.getGameStatus());
			for(Player p : r.getPlayers()) {
				StartPage.room.addPlayer(p);
			}
			StartPage.room.addPlayer(StartPage.player);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void addRoom() {
		try {
			System.out.println("someone addRoom");
			Player newPlayer = (Player)in.readObject();
			StartPage.room.addPlayer(newPlayer);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void playerAddRoom(String s) {
		try {
			Player p = (Player) in.readObject();
			StartPage.room.addPlayer(p);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void getRooms() {
//		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//		StartPage.rooms = (ArrayList<Room>) objectInputStream.readObject();
		StartPage.rooms = new ArrayList<Room>();
		synchronized(StartPage.rooms){
			try {
				StartPage.rooms = (ArrayList<Room>) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("NEWSgetRooms Finish  " + StartPage.rooms.size());
	}


	public String getAccount() {

		return account;
	}
	
	public int getCoin() {
		return coin;
	}
	public String getName() {
		return name;
		
	}
	public int getLevel() {
		return level;
	}
	public int getExp() {
		return exp;
	}
	public int[] getItems() {
		return items;
	}
	
	public void setAccount(String account)
	{
		this.account=account;
	}
	
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setlevel(int level) {
		this.level = level;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public void setItems(int[] items) {
		for(int i = 0; i < items.length; i ++) {
			this.items[i] = items[i];
		}
	}
	public void addItem(String s, int k) {
		if(s.equals("hunter"))
			items[0]+=k;
		else if(s.equals("time"))
			items[1]+=k;
		else if(s.equals("exp"))
			items[2]+=k;
		else if(s.equals("coin"))
			items[3]+=k;
	}
	public void useItem(String s) {
		if(s.equals("hunter"))
			items[0]--;
		else if(s.equals("time"))
			items[1]--;
		else if(s.equals("exp"))
			items[2]--;
		else if(s.equals("coin"))
			items[3]--;
	}
    public void addCoins(int amount) {
        coin += amount;
    }
	
	public void ready() {
		ready = !ready;
	}
	public void resetNowString()
	{
		this.now_string="";
	}
	public Boolean getReady() {
		return ready;
	}
	public void sendMessage(String s) {
		
		try {
			out.writeObject(s);
			out.flush();
			out.reset();
			if (s.contains("LeaveRoom")) {
				out.writeObject(StartPage.player);
				out.flush();
				out.reset();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		out.println(s);
//		out.flush();
	}
	
	public void sendMessage(Player p) {
		try {
			out.writeObject(p);
			out.flush();
			out.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		out.println(s);
//		out.flush();
	}
	public String getChatRoom() {
		return chatRoom;
	}
	public void resetChatRoom() {
		chatRoom = "";
	}

	public Socket getSocket() {
		return socket;
	}
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String id) {

		roomId = id;
	}
	public void setCard(String s) {
		card = s;
	}
	public String getCard() {
		return card;
	}
	
	public void setDescription(String s) {
		description = s;
	}
	
	public String getDescription () {
		return description;
	}
	

	public String getNowString()
	{
		return this.now_string;
	}
	public void setReady(Boolean b) {
		ready = b;
	}
	public void setIsUnderCover(Boolean b) {
		isUnderCover = b;
	}
	public Boolean getIsUnderCover() {
		return isUnderCover;
	}
	public void setAlive(Boolean b) {
		alive = b;
	}
	
	public Boolean getAlive() {
		return alive;
	}
	public Boolean getIsVote() {
		return isVote;
	}
	public void setIsVote(Boolean b) {
		isVote = b;
	}
}
