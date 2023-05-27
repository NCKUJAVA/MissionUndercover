package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

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
    Connection connection;
    PreparedStatement prestatement;
    Statement statement;
    ResultSet resultSet;
	//TODO is here has a player?  the same as corresponding socket
    
    private String account="";
    private String password="";
    private String name="";
    String ForgotPageAccountText="";
    String ForgotPageQuestion="";
    String ForgotPageAnswerText="";
	String ForgotPageRealAnswer="";
	String ForgotPage_old_pwd="";
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
				if (line.contains("LogIn")) {
					//do sql to check if account and pwd correct
					// TODO : shangyuan read log in
					String[] parts = line.split("[:|]");

					String account = parts[1];
					String password = parts[2];
					Object[] player_value = LogInFunc(account,password);
					if(player_value[0]!=null)
					{
						String msg="LogIn successfully|";
						for (Object obj:player_value)
						{
							msg+=obj;
							msg+="|";
						}
						print_single("Server thread:"+msg);
						
					}else
					{
						String msg="LogIn failed";
						print_single("Server thread:"+msg);
					}
					
				}
				else if(line.contains("SignUp"))
				{
					String[] parts = line.split("[:|]");

					account = parts[1];
					password = parts[2];
					name = parts[3];
					int result=SignUpInfoCheck(account,password,name);
					if(result==0)
					{
						String msg="SignUp info ok";
						print_single(msg);
					}else if(result==1)
					{
						String msg="SignUp info G:1";
						print_single(msg);
					}else if(result==2)
					{
						String msg="SignUp info G:2";
						print_single(msg);
					}else if(result==3)
					{
						String msg="SignUp info G:3";
						print_single(msg);
					}
					
				}
				else if(line.contains("Auth question"))
				{
					String[] parts = line.split("[:|]");

					String question = parts[1];
					String answer = parts[2];
					SignUpWriteDB(account,password,name,question,answer,0,0,0,0,0,0,0);
					System.out.println("Server:"+line);
					print_single("Auth question:OK:"+name);
					
				}
				else if(line.contains("Forgot:"))
				{
					String[] parts = line.split("[:]");
					String account_tmp = parts[1];
					if(ForgotFunc(account_tmp)==true)
					{
						account=account_tmp;
						String msg="Forgot:OK"+":"+ForgotPageQuestion;
						print_single(msg);
					}else
					{
						String msg="Forgot:G";
						print_single(msg);
					}
				}else if(line.contains("Answer:"))
				{
					String[] parts = line.split("[:]");
					String answer = parts[1];
					if(answer.compareTo(ForgotPageRealAnswer)==0)
					{
						print_single("Answer:OK");
					}else
					{
						print_single("Answer:G");
					}
				}else if(line.contains("NewPassword:"))
				{
					String[] parts = line.split("[:]");
					String new_pwd = parts[1];
					if(new_pwd.compareTo(ForgotPage_old_pwd)==0)
					{
						print_single("NewPassword:G");
					}else
					{
						if(ChangePassword(account,new_pwd)==true)
						{
							print_single("NewPassword:OK");
						}else
						{
							print_single("NewPassword:Fail");
						}
							
					}
					
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
	
	private void print_single(String msg) throws IOException{
		PrintWriter out = null;
		out = new PrintWriter(socket.getOutputStream());
		out.println(msg);
		out.flush();
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
	public static Connection getConnection() throws Exception
	{
		try
		{
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/MissionUndercover_DB";
			String username = "root";
			String password = "F74086250";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connecting successfully!");
			return conn;
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return null;
	}
	private	Object[] LogInFunc(String AccountText,String PasswordText)
	{
		Object[] values = new Object[9];
	    try {
			connection = getConnection();
			String sql = "SELECT * FROM user";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				String user_account = resultSet.getString("account");

				if(AccountText.compareTo(user_account)==0)
				{
					
					
					String user_password = resultSet.getString("password");
					if(PasswordText.compareTo(user_password)==0)
					{
						String user_name = resultSet.getString("name");
//						String user_question = resultSet.getString("question");
//						String user_answer = resultSet.getString("answer");
						int user_coin=Integer.parseInt(resultSet.getString("coin"));
						int user_exp=Integer.parseInt(resultSet.getString("exp"));
						int user_level=Integer.parseInt(resultSet.getString("level"));
						int user_hunter=Integer.parseInt(resultSet.getString("hunter"));
						int user_sec_bonus=Integer.parseInt(resultSet.getString("sec_bonus"));
						int user_exp_bonus=Integer.parseInt(resultSet.getString("exp_bonus"));
						int user_coin_bonus=Integer.parseInt(resultSet.getString("coin_bonus"));	
						values[0]=AccountText;
						values[1]=user_name;
						values[2]=user_level;
						values[3]=user_exp;
						values[4]=user_coin;
						values[5]=user_hunter;
						values[6]=user_sec_bonus;
						values[7]=user_exp_bonus;
						values[8]=user_coin_bonus;
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
	private int SignUpInfoCheck(String account,String password,String name)
	{
		int rtn=0;
    	try {
    		connection = getConnection();
    		String sql = "SELECT * FROM user";
    		statement = connection.createStatement();
    		resultSet = statement.executeQuery(sql);
    		
    		while(resultSet.next())
    		{
    			String column1Value = resultSet.getString("account");
    			String column2Value = resultSet.getString("name");
    			if(account.compareTo(column1Value)==0)
    			{
    				rtn+=1;
    			}
    			if(name.compareTo(column2Value)==0)
    			{
    				rtn+=2;
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
	private void SignUpWriteDB(String account,String password,String name,String question,String answer,int level,int exp,int coin,int hunter,int sec_bonus,int exp_bonus,int coin_bonus)
	{
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
			System.out.println("Row affected:"+rowsAffected);
			//resultSet.close();
			prestatement.close();
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private boolean ForgotFunc(String account)
	{
		try {
			connection = getConnection();
			String sql = "SELECT * FROM user";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				String user_account = resultSet.getString("account");

				if(account.compareTo(user_account)==0)
				{
					ForgotPageQuestion=resultSet.getString("question");
					ForgotPageRealAnswer=resultSet.getString("answer");
					ForgotPage_old_pwd=resultSet.getString("password");
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
	
	private boolean ChangePassword(String account,String new_pwd)
	{
		
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
			System.out.println("Row affected:"+rowsAffected);
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
