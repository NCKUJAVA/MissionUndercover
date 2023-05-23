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
import change_password_page.ChangePasswordPageController;
public class ForgotPageController 
{
	@FXML
	private Label AccountLabel;
	@FXML
	private Label QuestionLabel;
	@FXML
	private Label AnswerLabel;
	@FXML
	private TextField AccountTextField;
	@FXML
	private TextField AnswerTextField;
	@FXML
	private Button ShowQuestionButton;
	@FXML
	private Button CheckAnswerButton;
	@FXML
	private Button BackToLogInPageButton;
    private Stage stage;
    
    private Scene scene;
    String AccountText="";
    String AnswerText="";
	String RealAnswer="";
	String old_pwd="";
    Connection connection;
    PreparedStatement prestatement;
    Statement statement;
    ResultSet resultSet;
    int AccountCheck=0;
    @FXML
    public void ShowQuestion(ActionEvent e) throws Exception
    {
    	AccountText=AccountTextField.getText();
    	connection = getConnection();
		String sql = "SELECT * FROM user";
		statement = connection.createStatement();
		resultSet = statement.executeQuery(sql);
		while(resultSet.next())
		{
			String column1Value = resultSet.getString("account");
			if(AccountText.compareTo(column1Value)==0)
			{
				AccountCheck=1;
				AccountLabel.setText("帳號正確，請回答安全驗證問題");
				String question=resultSet.getString("question");
				QuestionLabel.setText(question);
				RealAnswer=resultSet.getString("answer");
				old_pwd=resultSet.getString("password");
				ShowQuestionButton.setDisable(true);
				break;
				
			}
		}
		if(AccountCheck==0)
		{
			AccountLabel.setText("帳號錯誤，請重新輸入");
		}
		statement.close();
		resultSet.close();
		connection.close();
    }
    
    public void CheckANswer(ActionEvent e) throws Exception
    {
    	AnswerText=AnswerTextField.getText();
    	if(AnswerText.compareTo(RealAnswer)!=0)
    	{
    		AnswerLabel.setText("答案錯誤，請重新輸入");
    	}else
    	{
    		AnswerLabel.setText("答案正確");
    		System.out.println("change password");
    		ChangePasswordPageController.passUserAccount(AccountText,old_pwd);
            Parent root = FXMLLoader.load(getClass().getResource("/change_password_page/ChangePasswordPageFXML.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("switch to change password page");
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
}


