package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import Room.Room;

public class ServerThread extends Servers implements Runnable {
	// one user has one this thread
	Socket socket;
	String socketName;
	//TODO is here has a player?  the same as corresponding socket


	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 设置该客户端的端点地址
			socketName = socket.getRemoteSocketAddress().toString();
			
			System.out.println("Client@" + socketName + "has join chatRoom");
//			print("Client@" + socketName + "has Join ChatRoom");
			boolean flag = true;
			while (flag) {
				// 阻塞，等待该客户端的输出流
				String line = reader.readLine();
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
				else if (line.contains("CreateRoom")) {
					rooms.add(new Room("1", "waiting")); // TODO : add Id everytime
					
				}
				else if (line .contains("AddRoom:")) {
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
					
				}
				else if (line.contains("Chat:")) {
					//TODO 
					String msg = "Client@" + socketName + ":" + line;
					System.out.println(msg);
					// 向在线客户端输出信息
					print(msg);
				}
				else if (line.contains("Description:")) {
					
				}
				else if (line.contains("ready")) {
					// TODO Room.ready
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
		PrintWriter out = null;
		synchronized (sockets) {
			for (Socket sc : sockets) {
				out = new PrintWriter(sc.getOutputStream());
				out.println(msg);
				out.flush();
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
}
