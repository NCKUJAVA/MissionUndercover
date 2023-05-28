package change_password_page;
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
import start_page.StartPage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.time.LocalDateTime;
import UserPackage.User;
public class ChangePasswordPageController 
{
	@FXML
	private TextField NewPasswordTextField;
	@FXML
	private TextField CheckNewPasswordTextField;
	@FXML
	private Button ChangePasswordButton;
	@FXML
	private Button BackToLogInPageButton;
	@FXML
	private Label StatusLabel;
    private Stage stage;
    
    private Scene scene;
    Connection connection;
    PreparedStatement prestatement;
    Statement statement;
    ResultSet resultSet;
    private static String account;
    private static String old_pwd;
    String new_pwd="";
    String new_pwd_2="";
	public static void passUserAccount(String user_account,String user_old_pwd)
	{
		account=user_account;
		old_pwd=user_old_pwd;
		System.out.println(account+" "+old_pwd);
	}
	
	@FXML
	public void ChangePassword() throws Exception
	{
		StartPage.player.resetNowString();
		new_pwd=NewPasswordTextField.getText();
		new_pwd_2=CheckNewPasswordTextField.getText();
		if(new_pwd.compareTo("")==0)
		{
			StatusLabel.setText("新密碼不可為空");
		}else if(new_pwd.compareTo(new_pwd_2)!=0)
		{
			StatusLabel.setText("密碼與確認密碼不符");
		}else
		{
			StartPage.player.sendMessage("NewPassword:"+new_pwd);
			while(StartPage.player.getNowString().contains("NewPassword:")==false)
	    	{
	    		System.out.println("StartPage:"+StartPage.player.getNowString());
	    	}
			String s=StartPage.player.getNowString();
			if(s.contains("G"))
			{
				StatusLabel.setText("新密碼不可與舊密碼一致");
			}else if(s.contains("Fail"))
			{
				StatusLabel.setText("資料庫發生問題，密碼更改失敗");
				ChangePasswordButton.setDisable(true);
			}else if(s.contains("OK"))
			{
				StatusLabel.setText("密碼修改成功\n請返回登入頁面並以新密碼登入");
				ChangePasswordButton.setDisable(true);
			}
			
			
		}
		StartPage.player.resetNowString();
		
		
	}
	
    @FXML
	public void back(ActionEvent event) throws IOException
	{
		System.out.println("back");
        Parent root = FXMLLoader.load(getClass().getResource("/start_page/StartPageFXML.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to start page");
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

}
