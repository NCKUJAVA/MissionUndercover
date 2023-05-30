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
    
    @FXML
    public void login(ActionEvent e) throws Exception
	{
		System.out.println("login");
        String AccountText = AccountTextField.getText();
        System.out.println("Account:"+AccountText);
        

	    String PasswordText = PasswordTextField.getText();
	    System.out.println("Password:"+PasswordText);

	    if(AccountText.compareTo("")==0 || PasswordText.compareTo("")==0)
	    {
	    	LogInStatusLabel.setText("帳號或密碼不可為空");
	    }else
	    {
	    	StartPage.player.sendMessage("LogIn:"+AccountText+"|"+PasswordText);
	    	while(StartPage.player.getNowString().contains("LogIn")==false)
	    	{
	    		System.out.println("StartPage:"+StartPage.player.getNowString());
	    	}
	    	String now_string=StartPage.player.getNowString();
	    	System.out.println("StartPage:"+now_string);
	    	if(now_string.contains("successful"))
	    	{
				String[] parts = now_string.split("[|]");
				String account = parts[1];
				String name = parts[2];
				String level = parts[3];
				String exp = parts[4];
				String coin = parts[5];
				//String coin = "100";
				String hunter = parts[6];
				String sec_bonus = parts[7];
				String exp_bonus = parts[8];
				String coin_bonus = parts[9]; //"1000"
				StartPage.player.setAccount(account);
				StartPage.player.setName(name);
				StartPage.player.setlevel(Integer.valueOf(level));
				StartPage.player.setExp(Integer.valueOf(exp));
				StartPage.player.setCoin(Integer.valueOf(coin));
				int[] Items = {Integer.valueOf(hunter),Integer.valueOf(sec_bonus),Integer.valueOf(exp_bonus),Integer.valueOf(coin_bonus)};
				StartPage.player.setItems(Items);
				System.out.println("StartPage:"+StartPage.player.getAccount());
				System.out.println("StartPage:"+StartPage.player.getName());
				System.out.println("StartPage:"+StartPage.player.getLevel());
				System.out.println("StartPage:"+StartPage.player.getExp());
				System.out.println("StartPage:"+StartPage.player.getCoin());
				Items=StartPage.player.getItems();
				for(int i = 0; i < Items.length; i ++) {
					System.out.println("StartPage:"+Items[i]);
				}
				StartPage.player.resetNowString();
				//Parent root = FXMLLoader.load(getClass().getResource("/mainwindow_page/mainwindow.fxml"));
				//Parent root = FXMLLoader.load(getClass().getResource("/settlement_page/SettlementPageFXML.fxml"));
				Parent root = FXMLLoader.load(getClass().getResource("/mainwindow_page/mainwindow.fxml"));

		        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		        scene = new Scene(root);
		        stage.setScene(scene);
		        stage.show();
		        stage.setTitle("user :" + StartPage.player.getName());
		        System.out.println("switch to mainwindow");
	    	}else
	    	{
	    		StartPage.player.resetNowString();
	    		System.out.println(now_string);
	    		LogInStatusLabel.setText("帳號或密碼有誤!");
	    	}

			
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
