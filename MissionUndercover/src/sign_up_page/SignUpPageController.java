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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import start_page.StartPage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class SignUpPageController implements Initializable
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
    
    @FXML
    private AnchorPane signupPage;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	 signupPage.getStylesheets().add(getClass().getResource("SignUpPage.css").toExternalForm());
    }
    //private Parent root;

    int all_info_ok=1;
    Connection connection;
    //PreparedStatement prestatement;
    Statement statement;
    ResultSet resultSet;

    @FXML
    public void signup(ActionEvent e) throws Exception
	{
    	StartPage.player.resetNowString();
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
	    	//AccountCheckLabel.setText("Account不可為空");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("帳號不可為空");

            alert.showAndWait();
	    }
	    if(PasswordText.length()==0)
	    {
	    	all_info_ok=0;
	    	//PasswordCheckLabel.setText("Password不可為空");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("密碼不可為空");

            alert.showAndWait();
	    }
	    if(NameText.length()==0)
	    {
	    	all_info_ok=0;
	    	//NameCheckLabel.setText("Name不可為空");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("遊戲暱稱不可為空");

            alert.showAndWait();
	    }

	    if(all_info_ok==1)
	    {
	    	StartPage.player.sendMessage("SignUp:"+AccountText+"|"+PasswordText+"|"+NameText);
	    	while(StartPage.player.getNowString().contains("SignUp info")==false)
	    	{
	    		System.out.println("StartPage:"+StartPage.player.getNowString());
	    	}
	    	String now_string=StartPage.player.getNowString();
	    	System.out.println("StartPage:"+now_string);
	    	if(now_string.contains("SignUp info ok")==true)
	    	{
		    	//AccountCheckLabel.setText("OK!");
		    	//PasswordCheckLabel.setText("OK!");
		    	//NameCheckLabel.setText("OK!");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("註冊成功");
                alert.setHeaderText(null);
                alert.setContentText("請繼續進入安全性頁面");

                alert.showAndWait();
		        Parent root = FXMLLoader.load(getClass().getResource("/authentification_question_page/AuthentificationQuestionPageFXML.fxml"));
		        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		        scene = new Scene(root);
		        stage.setScene(scene);
		        stage.show();
		        System.out.println("switch to authentification question page");
	    	}else if(now_string.contains("SignUp info G:"))
	    	{
	    		String[] parts = now_string.split("[:]");
	    		String result=parts[1];
	    		if(result.compareTo("1")==0)
	    		{
	    			//AccountCheckLabel.setText("該帳號已存在，\n請註冊新帳戶\n或使用原有帳戶登入");
	                Alert alert = new Alert(AlertType.INFORMATION);
	                alert.setTitle("提示");
	                alert.setHeaderText(null);
	                alert.setContentText("該帳號已存在，請註冊新帳戶或使用原有帳戶登入");

	                alert.showAndWait();
	    		}else if(result.compareTo("2")==0)
	    		{
	    			//NameCheckLabel.setText("該暱稱已存在，\n請換一個新的暱稱");
	                Alert alert = new Alert(AlertType.INFORMATION);
	                alert.setTitle("提示");
	                alert.setHeaderText(null);
	                alert.setContentText("該暱稱已存在，請換一個新的暱稱");

	                alert.showAndWait();
	    		}else if(result.compareTo("3")==0)
	    		{
	    			//AccountCheckLabel.setText("該帳號已存在，請註冊新帳戶\n或使用原有帳戶登入");
	    			//NameCheckLabel.setText("該暱稱已存在，\n請換一個新的暱稱");
	                Alert alert = new Alert(AlertType.INFORMATION);
	                alert.setTitle("提示");
	                alert.setHeaderText(null);
	                alert.setContentText("該帳號已存在，請註冊新帳戶或使用原有帳戶登入\n該暱稱已存在，請換一個新的暱稱");

	                alert.showAndWait();
	    		}
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