package start_page;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EventObject;
import java.util.Random;
import java.time.LocalDateTime;
import UserPackage.User;

public class StartPageController
{
    @FXML
    private TextField AccountTextField;
    
    @FXML
    private TextField PasswordTextField;

    
    @FXML
    private Label LogInStatusLabel;
    
    @FXML
    private Button LogInButton;
    
    @FXML
    private Button SignUpButton;
    
    @FXML
    private Button ForgotButton;
    
    private Stage stage;
    
    private Scene scene;
    
    private Parent root;
    
    Connection connection;
    //PreparedStatement prestatement;
    Statement statement;
    ResultSet resultSet;
    
    int check_user_info=0;
    @FXML
    public void login(ActionEvent e) throws Exception
	{
    	check_user_info=0;
		System.out.println("login");
        String AccountText = AccountTextField.getText();
        System.out.println("Account:"+AccountText);
        

	    String PasswordText = PasswordTextField.getText();
	    System.out.println("Password:"+PasswordText);
	    
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
					String user_question = resultSet.getString("question");
					String user_answer = resultSet.getString("answer");
					int user_coin=Integer.parseInt(resultSet.getString("coin"));
					int user_exp=Integer.parseInt(resultSet.getString("exp"));
					int user_level=Integer.parseInt(resultSet.getString("level"));
					int user_hunter=Integer.parseInt(resultSet.getString("hunter"));
					int user_sec_bonus=Integer.parseInt(resultSet.getString("sec_bonus"));
					int user_exp_bonus=Integer.parseInt(resultSet.getString("exp_bonus"));
					int user_coin_bonus=Integer.parseInt(resultSet.getString("coin_bonus"));
					
					
					User user = new User(user_account,
										user_password,
										user_name,
										user_question,
										user_answer,
										user_coin,
										user_exp,
										user_level,
										user_hunter,
										user_sec_bonus,
										user_exp_bonus,
										user_coin_bonus);
					LogInStatusLabel.setText("帳號密碼正確\n歡迎"+user.getName());
					check_user_info=1;
					
					Parent root = FXMLLoader.load(getClass().getResource("/mainwindow_page/mainwindow.fxml"));
			        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			        scene = new Scene(root);
			        stage.setScene(scene);
			        stage.show();
			        System.out.println("switch to mainwindow");
					
				}
				
			}
			
			
		}
		if(check_user_info==0)
		{
			LogInStatusLabel.setText("帳號或密碼有誤!");
		}


	}
    
    @FXML
	public void signup(ActionEvent event) throws IOException
	{
		System.out.println("signup");
        Parent root = FXMLLoader.load(getClass().getResource("/sign_up_page/SignUpPageFXML.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to sign up page");
        
	    
	    
	}
    
    @FXML
	public void forgot(ActionEvent e) throws Exception
	{
		System.out.println("forgot");
        Parent root = FXMLLoader.load(getClass().getResource("/forgot_page/ForgotPageFXML.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to forgot page");
	    
	}
    
	public static Connection getConnection() throws Exception
	{
		try
		{
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/MissionUndercover_DB";
			String username = "root";
			String password = "24081333";
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
	
	public void goMainwindow(EventObject e) throws IOException {
		System.out.println("leave");
		
		Parent root = FXMLLoader.load(getClass().getResource("/mainwindow_page/mainwindow.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to mainwindow");
        
	}

}
