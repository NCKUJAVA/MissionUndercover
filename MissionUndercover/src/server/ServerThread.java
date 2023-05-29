package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import Player.Player;
import Room.Room;
import UserPackage.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Player.Player;

public class ServerThread extends Servers implements Runnable {
	// one user has one this thread
	Socket socket;
	String socketName;
	ObjectOutputStream objectOutputStream;
	ObjectInputStream in;
	Player player;
	Connection connection;
	PreparedStatement prestatement;
	Statement statement;
	ResultSet resultSet;
	// TODO is here has a player? the same as corresponding socket

	private String account = "";
	private String password = "";
	private String name = "";
	String ForgotPageAccountText = "";
	String ForgotPageQuestion = "";
	String ForgotPageAnswerText = "";
	String ForgotPageRealAnswer = "";
	String ForgotPage_old_pwd = "";

	public ServerThread(Socket socket) {
		this.socket = socket;
		player = new Player("Charles", 1, 1, 1);
		playerToSocket.put("Charles", socket);
		System.out.println(sockets);
	}

	@Override
	public void run() {
		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			in = new ObjectInputStream(socket.getInputStream());
			// 设置该客户端的端点地址
			socketName = socket.getRemoteSocketAddress().toString();
			OutputStream outputStream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);

			System.out.println("Client@" + socketName + "has join chatRoom");
//			print("Client@" + socketName + "has Join ChatRoom");
			boolean flag = true;
//			player = (Player) in.readObject();
			//System.out.println("player name = " + player.getName());
			//playerToSocket.put(player, socket);
			System.out.println(playerToSocket);
			while (flag) {
				// 阻塞，等待该客户端的输出流
//				String line = reader.readLine();
				String line = "";
				try {
					line = (String) in.readObject();
					System.out.println("get command = " + line);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 若客户端退出，则退出连接。
				if (line == null) {
					flag = false;
					continue;
				}
				// "Command:content"
				if (line.contains("LogIn")) {
					// do sql to check if account and pwd correct
					// TODO : shangyuan read log in
					String[] parts = line.split("[:|]");

					String account = parts[1];
					String password = parts[2];
					Object[] player_value = LogInFunc(account, password);
					if (player_value[0] != null) {
						String msg = "LogIn successfully|";
						for (Object obj : player_value) {
							msg += obj;
							msg += "|";
						}
						print_single("Server thread:" + msg);

					} else {
						String msg = "LogIn failed";
						print_single("Server thread:" + msg);
					}

				} else if (line.contains("SignUp")) {
					String[] parts = line.split("[:|]");

					account = parts[1];
					password = parts[2];
					name = parts[3];
					int result = SignUpInfoCheck(account, password, name);
					if (result == 0) {
						String msg = "SignUp info ok";
						print_single(msg);
					} else if (result == 1) {
						String msg = "SignUp info G:1";
						print_single(msg);
					} else if (result == 2) {
						String msg = "SignUp info G:2";
						print_single(msg);
					} else if (result == 3) {
						String msg = "SignUp info G:3";
						print_single(msg);
					}

				} else if (line.contains("Auth question")) {
					String[] parts = line.split("[:|]");

					String question = parts[1];
					String answer = parts[2];
					SignUpWriteDB(account, password, name, question, answer, 0, 0, 0, 0, 0, 0, 0);
					System.out.println("Server:" + line);
					print_single("Auth question:OK:" + name);

				} else if (line.contains("Forgot:")) {
					String[] parts = line.split("[:]");
					String account_tmp = parts[1];
					if (ForgotFunc(account_tmp) == true) {
						account = account_tmp;
						String msg = "Forgot:OK" + ":" + ForgotPageQuestion;
						print_single(msg);
					} else {
						String msg = "Forgot:G";
						print_single(msg);
					}
				} else if (line.contains("Answer:")) {
					String[] parts = line.split("[:]");
					String answer = parts[1];
					if (answer.compareTo(ForgotPageRealAnswer) == 0) {
						print_single("Answer:OK");
					} else {
						print_single("Answer:G");
					}
				} else if (line.contains("NewPassword:")) {
					String[] parts = line.split("[:]");
					String new_pwd = parts[1];
					if (new_pwd.compareTo(ForgotPage_old_pwd) == 0) {
						print_single("NewPassword:G");
					} else {
						if (ChangePassword(account, new_pwd) == true) {
							print_single("NewPassword:OK");
						} else {
							print_single("NewPassword:Fail");
						}

						// TODO : shangyuan read log in

					}
				} else if (line.contains("LeaveRoom")) {
					leaveRoom(line);

				} else if (line.contains("CreateRoom")) {
					createRoom();
				} else if (line.contains("AddRoom:")) {
					addRoom(line);
				} else if (line.contains("Buy:")) {
					// TODO : do SQL add items and use coin shangyuan chiatung
				} else if (line.contains("UseItem:")) {
					useItem();
					// TODO : do SQL minus the items and correct function the item;
				} else if (line.contains("GetRooms")) {
					getRooms();
				} else if (line.contains("Chat/")) {
					chat(line);
				} else if (line.contains("Description/")) {
					description(line);
				} else if (line.contains("ready/")) {
					ready(line);
				}
				/*else if (line.contains("timer:")) {
					timer(line);
				}*/

			}
			closeConnect();
		} catch (IOException e) {
			try {
				closeConnect();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
	}
	private void timer(String s) {
		
	}
	
	private void useItem(){
		
	}
	private void description(String line) {
		// description/roomid
		String chat[] = line.split("/");
		try {
			line = (String) in.readObject();
			String msg = "Desctiption:" + line;
			for(Room r : rooms) {
				if (r.getId().equals(chat[1])) {
					roomChat(r, msg);
				}
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void chat(String line) {
		// TODO
		String chat[] = line.split("/");
		// int rid = Integer.parseInt(chat[1]);//roomid

		try {
			line = (String) in.readObject();
			String msg = "Chat:Client@" + socketName + ":" + line;

			System.out.println(msg);
			// 向在线客户端输出信息
			// print(msg);
			for (Room r : rooms) {
				System.out.println("rinfo : " + r.getId());
				if (r.getId().equals(chat[1])) {
					roomChat(r, msg);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	private void ready(String line) {
		// TODO Room.ready
		String s[] = line.split("/");
		ArrayList<Player> ps = rooms.get(Integer.parseInt(s[1])).getPlayers();
		int readys = 0;
		for (Player p : ps) {
			if (p.getName().equals(s[2])) {
				p.ready();
			}
			if (p.getReady())
				readys++;
		}
		if (readys == ps.size() && readys >= 4) {
			rooms.get(Integer.parseInt(s[1])).startGame();
			for (Player p : ps) {
				sendMessageToPlayer(p, "question:" + p.getCard());
			}
		}
	}

	private void getRooms() {
		System.out.println("GetRooms command");
//		PrintWriter out = new PrintWriter(socket.getOutputStream());
//		out.println("GetRooms");
//		out.flush();
		try {
			objectOutputStream.writeObject(new String("GetRooms"));
			objectOutputStream.flush();
			objectOutputStream.reset();
			System.out.println("GETROOMS-----------" + rooms.size());
			objectOutputStream.writeObject(rooms);
			objectOutputStream.flush();
			objectOutputStream.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void addRoom(String line) {
		System.out.println("AddRoom func");
		try {
			String s[] = line.split(":");
			Player newPlayer = (Player) in.readObject();
			//rooms.get(Integer.parseInt(s[1])).addPlayer(newPlayer);
			for(Room r : rooms) {
				if (r.getId().equals(s[1])) {
					roomSendPlayer(r, newPlayer);
					r.addPlayer(newPlayer);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void leaveRoom(String s) {
		System.out.println("LeaveRoom");
		String rid = s.split("/")[1];
		try {
			Player p = (Player) in.readObject();
			//String rid = p.getRoomId();
			synchronized (rooms) {
				for (Room r : rooms) {
					if (p.getRoomId().equals(rid)) {
						r.removePlayer(p);
						if (r.getPlayers().size() == 0)
							rooms.remove(r);
						break;
					}
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createRoom() {
		Room newRoom = new Room(Integer.toString(roomID++), "waiting");
		rooms.add(newRoom); // TODO : add Id everytime
		try {
			Player p = (Player) in.readObject();
			newRoom.addPlayer(p);
			System.out.println("Server CreateRoom" + rooms.size());
			for (Room r : rooms) {
				System.out.print(r.getId());
			}
			objectOutputStream.writeObject("roominfo/" + newRoom.getId());
			objectOutputStream.flush();
			objectOutputStream.reset();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException eio) {
			eio.printStackTrace();
		}

	}

	/**
	 * 向所有在线客户端socket转发消息
	 * 
	 * @param msg
	 * @throws IOException
	 */
	private void print(String msg) throws IOException {
//		PrintWriter out = null;
		ObjectOutputStream out = null;
		synchronized (sockets) {
			for (Socket sc : sockets) {
				out = new ObjectOutputStream(sc.getOutputStream());
				out.writeObject(msg);
//				out = new PrintWriter(sc.getOutputStream());
//				out.println(msg);
				out.flush();
				out.reset();
			}
		}
	}

	private void print_single(String msg) throws IOException {
		objectOutputStream.writeObject(msg);
		objectOutputStream.flush();
		objectOutputStream.reset();
		//PrintWriter out = null;
		//out = new PrintWriter(socket.getOutputStream());
		//out.println(msg);
		//out.flush();
	}

	/**
	 * 关闭该socket的连接
	 * 
	 * @throws IOException
	 */
	public void closeConnect() throws IOException {
		System.out.println("Client@" + socketName + "disconnected");
		//print("Client@" + socketName + "已退出聊天");
		// 移除没连接上的客户端
		synchronized (sockets) {
			sockets.remove(socket);
		}
		socket.close();
	}

	public void roomChat(Room r, String msg) throws IOException {
		System.out.println("roomchat" + r.getId() + "/" + msg);
		ObjectOutputStream out = null;
		for (Player p : r.getPlayers()) {
			System.out.println(p.getName());
			Socket s = playerToSocket.get(p.getName());
			out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(msg);
			out.flush();
			out.reset();
		}
	}
	public void roomSendPlayer(Room r,Player p) {
		System.out.println("roomSendPlayer");
		
		ObjectOutputStream out = null;
		for (Player pl:r.getPlayers()) {
			Socket s = playerToSocket.get(pl.getName());
			try {
				out = new ObjectOutputStream(s.getOutputStream());
				out.writeObject("AddRoom:");
				out.flush();
				out.reset();
				out.writeObject(p);
				out.flush();
				out.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void sendMessageToPlayer(Player p, String msg) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(p.getSocket().getOutputStream());
			out.writeObject(msg);
			out.flush();
			out.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/MissionUndercover_DB";
			String username = "root";
			String password = "F74086250";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connecting successfully!");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	private Object[] LogInFunc(String AccountText, String PasswordText) {
		Object[] values = new Object[9];
		try {
			connection = getConnection();
			String sql = "SELECT * FROM user";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String user_account = resultSet.getString("account");

				if (AccountText.compareTo(user_account) == 0) {

					String user_password = resultSet.getString("password");
					if (PasswordText.compareTo(user_password) == 0) {
						String user_name = resultSet.getString("name");
//						String user_question = resultSet.getString("question");
//						String user_answer = resultSet.getString("answer");
						int user_coin = Integer.parseInt(resultSet.getString("coin"));
						int user_exp = Integer.parseInt(resultSet.getString("exp"));
						int user_level = Integer.parseInt(resultSet.getString("level"));
						int user_hunter = Integer.parseInt(resultSet.getString("hunter"));
						int user_sec_bonus = Integer.parseInt(resultSet.getString("sec_bonus"));
						int user_exp_bonus = Integer.parseInt(resultSet.getString("exp_bonus"));
						int user_coin_bonus = Integer.parseInt(resultSet.getString("coin_bonus"));
						values[0] = AccountText;
						values[1] = user_name;
						values[2] = user_level;
						values[3] = user_exp;
						values[4] = user_coin;
						values[5] = user_hunter;
						values[6] = user_sec_bonus;
						values[7] = user_exp_bonus;
						values[8] = user_coin_bonus;
						resultSet.close();
						statement.close();
						connection.close();
						return values;
					}
				}

			}
			resultSet.close();
			statement.close();
			connection.close();
			return values;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return values;
	}

	private int SignUpInfoCheck(String account, String password, String name) {
		int rtn = 0;
		try {
			connection = getConnection();
			String sql = "SELECT * FROM user";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				String column1Value = resultSet.getString("account");
				String column2Value = resultSet.getString("name");
				if (account.compareTo(column1Value) == 0) {
					rtn += 1;
				}
				if (name.compareTo(column2Value) == 0) {
					rtn += 2;
				}
			}
			resultSet.close();
			statement.close();
			connection.close();
			return rtn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtn;

	}

	private void SignUpWriteDB(String account, String password, String name, String question, String answer, int level,
			int exp, int coin, int hunter, int sec_bonus, int exp_bonus, int coin_bonus) {
		try {
			connection = getConnection();
			String sql = "INSERT INTO user (account,password,name,question,answer,level,exp,coin,hunter,sec_bonus,exp_bonus,coin_bonus) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			prestatement = connection.prepareStatement(sql);
			prestatement.setString(1, account);
			prestatement.setString(2, password);
			prestatement.setString(3, name);
			prestatement.setString(4, question);
			prestatement.setString(5, answer);
			prestatement.setInt(6, level);
			prestatement.setInt(7, exp);
			prestatement.setInt(8, coin);
			prestatement.setInt(9, hunter);
			prestatement.setInt(10, sec_bonus);
			prestatement.setInt(11, exp_bonus);
			prestatement.setInt(12, coin_bonus);
			int rowsAffected = prestatement.executeUpdate();
			System.out.println("Row affected:" + rowsAffected);
			// resultSet.close();
			prestatement.close();
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean ForgotFunc(String account) {
		try {
			connection = getConnection();
			String sql = "SELECT * FROM user";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String user_account = resultSet.getString("account");

				if (account.compareTo(user_account) == 0) {
					ForgotPageQuestion = resultSet.getString("question");
					ForgotPageRealAnswer = resultSet.getString("answer");
					ForgotPage_old_pwd = resultSet.getString("password");
					resultSet.close();
					statement.close();
					connection.close();
					return true;
				}
			}
			resultSet.close();
			statement.close();
			connection.close();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	private boolean ChangePassword(String account, String new_pwd) {

		try {
			try {
				connection = getConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sql = "UPDATE user SET password = ? WHERE account = ?";
			prestatement = connection.prepareStatement(sql);
			prestatement.setString(1, new_pwd);
			prestatement.setString(2, account);
			int rowsAffected = prestatement.executeUpdate();
			System.out.println("Row affected:" + rowsAffected);
			prestatement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
