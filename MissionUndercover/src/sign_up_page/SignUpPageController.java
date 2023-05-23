package sign_up_page;
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
import authentification_question_page.AuthentificationQuestionPageController;

public class SignUpPageController
{
    @FXML
    private TextField AccountTextField;
    
    @FXML
    private TextField PasswordTextField;
    
    @FXML
    private TextField NameTextField;
    
    @FXML
    private TextField EmailTextField;

    @FXML
    private Label AccountCheckLabel;
    
    @FXML
    private Label PasswordCheckLabel;
    
    @FXML
    private Label NameCheckLabel;
    

    
    @FXML
    private Button SignUpButton;
    
    @FXML
    private Button BackButton;
    
    private Stage stage;
    
    private Scene scene;
    
    //private Parent root;

    int all_info_ok=1;
    Connection connection;
    //PreparedStatement prestatement;
    Statement statement;
    ResultSet resultSet;

    @FXML
    public void signup(ActionEvent e) throws Exception
	{
		System.out.println("signup");
		all_info_ok=1;
		
		AccountCheckLabel.setText("");
		PasswordCheckLabel.setText("");
		NameCheckLabel.setText("");

        
		String AccountText = AccountTextField.getText();
        System.out.println("Account:"+AccountText);
        

	    String PasswordText = PasswordTextField.getText();
	    System.out.println("Password:"+PasswordText);
	    
	    String NameText = NameTextField.getText();
	    System.out.println("Name:"+NameText);

	    
	    
	    if(AccountText.length()==0)
	    {
	    	all_info_ok=0;
	    	AccountCheckLabel.setText("Account不可為空");
	    }
	    if(PasswordText.length()==0)
	    {
	    	all_info_ok=0;
	    	PasswordCheckLabel.setText("Password不可為空");
	    }
	    if(NameText.length()==0)
	    {
	    	all_info_ok=0;
	    	NameCheckLabel.setText("Name不可為空");
	    }

	    if(all_info_ok==1)
	    {
	    	connection = getConnection();
			String sql = "SELECT * FROM user";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				String column1Value = resultSet.getString("account");
				if(AccountText.compareTo(column1Value)==0)
				{
					AccountCheckLabel.setText("Account已存在，\n請註冊新帳戶\n或使用原有帳戶登入");
					all_info_ok=0;
				}
			}
			
	    	
	    }
	    
	    if(all_info_ok==1)
	    {
	    	AccountCheckLabel.setText("OK!");
	    	PasswordCheckLabel.setText("OK!");
	    	NameCheckLabel.setText("OK!");

//	    	long currentSeconds = LocalDateTime.now().getSecond();
//
//	        // 使用当前秒数作为种子创建Random对象
//	    	Random random = new Random();
//	        int randomNumber = random.nextInt(900000) + 100000;
//	        String user_id_text=Integer.toString(randomNumber);
	        User user = new User(AccountText,PasswordText,NameText,"","",0,0,0,0,0,0,0);
	        AuthentificationQuestionPageController.passUser(user);
	        statement.close();
	        resultSet.close();
	        connection.close();
	        Parent root = FXMLLoader.load(getClass().getResource("/authentification_question_page/AuthentificationQuestionPageFXML.fxml"));
	        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	        scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	        System.out.println("switch to authentification question page");

	    }
	    
	    
	    
	    
	    


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
}