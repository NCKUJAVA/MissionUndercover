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
import java.util.List;
import java.util.Vector;

import Player.Player;
import Room.Room;

public class ServerThread extends Servers implements Runnable {
	// one user has one this thread
	Socket socket;
	String socketName;
	ObjectOutputStream objectOutputStream;
	Player player ;
	//TODO is here has a player?  the same as corresponding socket


	public ServerThread(Socket socket) {
		this.socket = socket;
		player = new Player("default", 0,0,0);
	}

	@Override
	public void run() {
		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			// 设置该客户端的端点地址
			socketName = socket.getRemoteSocketAddress().toString();
			OutputStream outputStream = socket.getOutputStream();
			objectOutputStream= new ObjectOutputStream(outputStream);
			
			System.out.println("Client@" + socketName + "has join chatRoom");
//			print("Client@" + socketName + "has Join ChatRoom");
			boolean flag = true;
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
				if (line.contains("login")) {
					//do sql to check if account and pwd correct
					// TODO : shangyuan read log in 

				}
				else if (line.contains("LeaveRoom")) {
					System.out.println("LeaveRoom");
					try {
						Player p = (Player) in.readObject();
						String rid = p.getRoomId();
						synchronized(rooms) {
							for(Room r : rooms) {
								if(p.getRoomId().equals(rid)) {
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
					
				}
				else if (line.contains("CreateRoom")) {
					Room newRoom = new Room(Integer.toString(roomID++), "waiting");
					rooms.add(newRoom); // TODO : add Id everytime
					try {
						Player p = (Player) in.readObject();
						newRoom.addPlayer(p);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Server CreateRoom" + rooms.size());
					for(Room r : rooms) {
						System.out.print(r.getId());
					}
					objectOutputStream.writeObject("roominfo/" + newRoom.getId());
				}
				else if (line .contains("AddRoom:")) {
					try {
						String s[] = line.split(":");
						Player newPlayer = (Player)in.readObject() ;
						rooms.get(Integer.parseInt(s[1])).addPlayer(newPlayer);
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					for (Room r : rooms) {
						if(r.getId().equals("1")) {
							r.addPlayer(null); // TODO: Add player
							break;
						}
					}
				}
				else if(line.contains("Buy:")) {
					//TODO : do SQL add items and use coin  shangyuan  chiatung
				}
				else if (line.contains("UseItem:")) {
					//TODO : do SQL minus the items and correct function the item;
				}
				else if (line.contains("GetRooms")) {
					//TODO : return RoomList
					System.out.println("GetRooms command");
//					PrintWriter out = new PrintWriter(socket.getOutputStream());
//					out.println("GetRooms");
//					out.flush();
					objectOutputStream.writeObject(new String("GetRooms"));
					objectOutputStream.flush();
					objectOutputStream.reset();
					System.out.println("GETROOMS-----------" + rooms.size());
					objectOutputStream.writeObject(rooms);
					objectOutputStream.flush();
					objectOutputStream.reset();
					
				}
				else if (line.contains("Chat/")) {
					//TODO
					String chat[] = line.split("/");
					//int rid = Integer.parseInt(chat[1]);//roomid
					
					try {
						line = (String) in.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String msg = "Chat:Client@" + socketName + ":" + line;
					
					System.out.println(msg);
					// 向在线客户端输出信息
					//print(msg);
					for(Room r : rooms) {
						System.out.println("rinfo : " + r.getId());
						if(r.getId().equals(chat[1])) {
							roomChat(r,msg);
						}
					}
					
				}
				else if (line.contains("Description:")) {
					
				}
				else if (line.contains("ready/")) {
					// TODO Room.ready
					String s[] = line.split("/");
					ArrayList<Player> ps = rooms.get(Integer.parseInt(s[1])).getPlayers();
					int readys = 0;
					for(Player p: ps) {
						if(p.getName().equals(s[2])) {
							p.ready();
						}
						if(p.getReady())
							readys ++;
					}
					if(readys == ps.size() && readys >=4 ) {
						rooms.get(Integer.parseInt(s[1])).startGame();
						for(Player p : ps) {
							sendMessageToPlayer(p, "question:" + p.getCard());
						}
					}
					
					
				}
				
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
				out = new ObjectOutputStream (sc.getOutputStream());
				out.writeObject(msg);
//				out = new PrintWriter(sc.getOutputStream());
//				out.println(msg);
				out.flush();
				out.reset();
			}
		}
	}

	/**
	 * 关闭该socket的连接
	 * 
	 * @throws IOException
	 */
	public void closeConnect() throws IOException {
		System.out.println("Client@" + socketName + "已退出聊天");
		print("Client@" + socketName + "已退出聊天");
		// 移除没连接上的客户端
		synchronized (sockets) {
			sockets.remove(socket);
		}
		socket.close();
	}
	
	public void roomChat(Room r, String msg) throws IOException {
		System.out.println("roomchat" + r. getId()+ "/"+ msg);
		ObjectOutputStream out = null;
		for(Player p : r.getPlayers()) {
			System.out.println(p.getName());
			out = new ObjectOutputStream(p.getSocket().getOutputStream());
			out.writeObject(msg);
			out.flush();
			out.reset();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
