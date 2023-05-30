package server;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import Player.Player;
import Room.Room;

import java.io.*;

public class Servers {

	protected static List<Socket> sockets = new Vector<>();
	public static ArrayList<Room> rooms = new ArrayList<Room>();
	public static int roomID = 1;
	//public static Map<String, Socket> playerToSocket = new HashMap<>();
	public static Map<String, ServerThread> playerToThread = new HashMap<>();
	
	public static void main(String[] args) throws IOException {
		// 创建服务端
		ServerSocket server = new ServerSocket(8000);
		boolean flag = true;
		// 接受客户端请求
		while (flag) {
			try {
				// 阻塞等待客户端的连接
				Socket accept = server.accept();
				synchronized (sockets) {
					sockets.add(accept);
				}
				// 多个服务器线程进行对客户端的响应
				ServerThread serverThread = new ServerThread(accept);
				Thread thread = new Thread(serverThread);
				//playerToThread.put("Charles",serverThread);
				//Thread thread = new Thread(new ServerThread(accept));
				thread.start();
				// 捕获异常。
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			}
		}
		// 关闭服务器
		server.close();
	}

}
