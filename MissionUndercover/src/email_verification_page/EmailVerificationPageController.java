package email_verification_page;
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
public class EmailVerificationPageController 
{
	private static User user;
	
	@FXML
	private Label StatusLabel;
	
	@FXML
	private TextField VerificationCodeTextField;
	
	@FXML
	private Button SendVerificationCodeButton;
	
	@FXML
	private Button CheckVerificationCodeButton;
	
	@FXML
	private Button BackToSignUpPageButton;
	
	@FXML
	private Button BackToLogInPageButton;
	
    private Stage stage;
    
    private Scene scene;
    
	int randomNumber;
	String randomNumberString="";
	
    Connection connection;
    PreparedStatement prestatement;
    Statement statement;
    ResultSet resultSet;
	int find_account=0;
	public static void passUser(User user_object)
	{
		user=user_object;
	}
	
	@FXML
	public void SendVerificationCode()
	{
		Random random = new Random();
        randomNumber = random.nextInt(900000) + 100000;
        randomNumberString=Integer.toString(randomNumber);
        System.out.println(randomNumberString);
	}
	
	@FXML
	public void verify_and_insert() throws Exception
	{
		if(randomNumberString.compareTo("")==0)
		{
			StatusLabel.setText("請先按按鈕寄送驗證碼");
		}else
		{
			String VerificationCodeText = VerificationCodeTextField.getText();
			if(VerificationCodeText.compareTo(randomNumberString)==0)
			{
				
				connection = getConnection();
				String sql = "INSERT INTO user (account,password,name,email,coin,exp,level,hunter,sec_bonus,exp_bonus,coin_bonus) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
				prestatement = connection.prepareStatement(sql);
				prestatement.setString(1, user.getAccount());
				prestatement.setString(2, user.getPassword());
				prestatement.setString(3, user.getName());
				prestatement.setString(4, user.getEmail());
				prestatement.setInt(5, user.getCoin());
				prestatement.setInt(6, user.getExp());
				prestatement.setInt(7, user.getLevel());
				prestatement.setInt(8, user.getHunter());
				prestatement.setInt(9, user.getSecBonus());
				prestatement.setInt(10, user.getExpBonus());
				prestatement.setInt(11, user.getCoinBonus());
				int rowsAffected = prestatement.executeUpdate();
				System.out.println("Row affected:"+rowsAffected);
				StatusLabel.setText("驗證成功，帳戶註冊完成，歡迎"+user.getName()+"的加入!");
				prestatement.close();
				connection.close();
			}else
			{
				StatusLabel.setText("驗證碼錯誤，請再試一次");
			}
		}


	}
	
	@FXML
	public void BackToSignUpPage(ActionEvent event) throws IOException
	{
        Parent root = FXMLLoader.load(getClass().getResource("/sign_up_page/SIgnUpPageFXML.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to sign ups page");
		
	}
	
	@FXML
	public void BackToLogInPage(ActionEvent event) throws IOException
	{
        Parent root = FXMLLoader.load(getClass().getResource("/start_page/StartPageFXML.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to email verification page");
		
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
