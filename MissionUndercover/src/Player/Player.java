package Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
	private String account="";	
	private String name = "";
	private int level = 1;
	private int exp = 0;
	private int coin = 0;
	private int[] items = {0,0,0,0};
	private Boolean ready = false;

	private Socket socket;
	private String chatRoom = "";
	BufferedReader in;
	PrintWriter out;
	private String now_string="";
	public Player() {
		//System.out.println("player()");
		try {
			socket = new Socket("127.0.0.1", 8000);//TODO: check server ip     connect to server
			// out is used to send message to server;
			out = new PrintWriter(socket.getOutputStream());
			// in is used to get message from server
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							String s = in.readLine();
							System.out.println("player:"+s);
							if (s.contains("Chat:")) {
								//TODO: write message to chatRoom
//								s = s.substring(5);
								chatRoom = chatRoom + s + "\n";
								System.out.println("CHAT: " + chatRoom);
								
							}
							else if (s.contains("time:")) {
								
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
							}else if(s.contains("Auth question:OK:"))
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
					}
				}
			}).start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public Player(String name, int level, int exp, int coin){
		this.coin = coin;
		this.level = level;
		this.name = name;
		this.exp = exp;
	}

	public String getAccount()
	{
		return account;
	}
	
	public int getCoin() {
		return coin;
	}
	public String getName() {
		return name;
	}
	public int getlevel() {
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
    public void addCoins(int amount) {
        coin += amount;
    }
	
	public void setReady(Boolean b) {
		ready = b;
	}
	public void resetNowString()
	{
		this.now_string="";
	}
	public Boolean getReady() {
		return ready;
	}

	public void sendMessage(String s) 
	{
		out.println(s);
		out.flush();
	}
	
	public String getChatRoom() {
		return chatRoom;
	}
	public String getNowString()
	{
		return now_string;
	}
}
