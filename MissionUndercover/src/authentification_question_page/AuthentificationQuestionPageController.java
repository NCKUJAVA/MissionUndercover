package authentification_question_page;
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
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.time.LocalDateTime;
import UserPackage.User;
public class AuthentificationQuestionPageController implements Initializable
{
	private static User user;
	
	@FXML
	private Label StatusLabel;
	
	@FXML
	private Label StatusLabel1;

	@FXML
	private ComboBox<String> QuestionComboBox;
	
	@FXML 
	TextField AnswerTextField;
	
	@FXML
	private Button FinishSignUpButton;
	
	@FXML
	private Button BackToSignUpPageButton;
	
	@FXML
	private Button BackToLogInPageButton;
	
    private Stage stage;
    
    private Scene scene;
    

	
    Connection connection;
    PreparedStatement prestatement;
    Statement statement;
    ResultSet resultSet;
	int find_account=0;
	String QuestionText="";
	String AnswerText="";


	public static void passUser(User user_object)
	{
		user=user_object;
	}

	

	
	@FXML
	public void finish_and_insert() throws Exception
	{
		StatusLabel.setText("安全性驗證問題設定");
		QuestionText=QuestionComboBox.getValue();
		AnswerText=AnswerTextField.getText();
		System.out.println(QuestionText);
		System.out.println(AnswerText);
		if(AnswerText.compareTo("")==0 || QuestionText == null)
		{
			StatusLabel.setText("安全驗證問題與答案皆不可為空");
		}else
		{
			user.setQuestion(QuestionText);
			user.setAnswer(AnswerText);
			connection = getConnection();
			String sql = "INSERT INTO user (account,password,name,question,answer,coin,exp,level,hunter,sec_bonus,exp_bonus,coin_bonus) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			prestatement = connection.prepareStatement(sql);
			prestatement.setString(1, user.getAccount());
			prestatement.setString(2, user.getPassword());
			prestatement.setString(3, user.getName());
			prestatement.setString(4, user.getQuestion());
			prestatement.setString(5, user.getAnswer());
			prestatement.setInt(6, user.getCoin());
			prestatement.setInt(7, user.getExp());
			prestatement.setInt(8, user.getLevel());
			prestatement.setInt(9, user.getHunter());
			prestatement.setInt(10, user.getSecBonus());
			prestatement.setInt(11, user.getExpBonus());
			prestatement.setInt(12, user.getCoinBonus());
			int rowsAffected = prestatement.executeUpdate();
			System.out.println("Row affected:"+rowsAffected);
			StatusLabel.setText("驗證成功，帳戶註冊完成\n歡迎"+user.getName()+"的加入!");
			StatusLabel1.setText("請返回登入頁面登入您的帳號");
			FinishSignUpButton.setDisable(true);
			prestatement.close();
			connection.close();

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
        System.out.println("switch to sign up page");
		
	}
	
	@FXML
	public void BackToLogInPage(ActionEvent event) throws IOException
	{
        Parent root = FXMLLoader.load(getClass().getResource("/start_page/StartPageFXML.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to log in page");
		
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub
		StatusLabel1.setText("");
		QuestionComboBox.setStyle("-fx-font-size: 16px;");
		QuestionComboBox.getItems().add("您的指導教授的名字是什麼?");
		QuestionComboBox.getItems().add("您的高中班導的名字是什麼?");
		QuestionComboBox.getItems().add("您的出生日期是什麼?");
		QuestionComboBox.getItems().add("您成長的城市是哪裡?");
		QuestionComboBox.getItems().add("您最喜歡的NBA球隊是哪支?");
		QuestionComboBox.getItems().add("您父親的星座為何?");
		QuestionComboBox.getItems().add("您的手機型號數字為何?");
		QuestionComboBox.getItems().add("您的身高為多少公分?");
		QuestionComboBox.getItems().add("您最喜歡的歌手/樂團?");
		QuestionComboBox.getItems().add("您的外祖母姓氏為何?");
		QuestionComboBox.getItems().add("您就讀的國小是哪間?");
		QuestionComboBox.getItems().add("您的JAVA期中考分數為何?");	
	}
}




