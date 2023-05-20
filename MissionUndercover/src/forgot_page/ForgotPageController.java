package forgot_page;
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
import java.sql.Statement;
import java.util.Random;
import java.time.LocalDateTime;
import UserPackage.User;
public class ForgotPageController 
{
	@FXML
	private Label StatusLabel;
	@FXML
	private TextField AccountTextField;
	@FXML
	private Button SendVerificationCodeButton;
	@FXML
	private TextField VerificationCodeTextField;
	@FXML
	private Button CheckVerificationCodeButton;
	@FXML
	private Button BackToLogInPageButton;
    private Stage stage;
    
    private Scene scene;
    
	int randomNumber;
	String randomNumberString;
	
    Connection connection;
    PreparedStatement prestatement;
    Statement statement;
    ResultSet resultSet;
    
    @FXML
    public void SendVerificationCode(ActionEvent e) throws Exception
    {
    	connection = getConnection();
		String sql = "SELECT * FROM user";
		statement = connection.createStatement();
		resultSet = statement.executeQuery(sql);
		while(resultSet.next())
		{
			String column1Value = resultSet.getString("user_account");
			if(AccountText.compareTo(column1Value)==0)
			{
				AccountCheckLabel.setText("Account已存在，\n請註冊新帳戶\n或使用原有帳戶登入");
				all_info_ok=0;
			}
		}
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
