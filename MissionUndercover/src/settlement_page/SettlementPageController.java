package settlement_page;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import start_page.StartPage;
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
public class SettlementPageController implements Initializable
{
	@FXML 
	Label NameLabel;
	@FXML 
	Label LevelLabel;

	@FXML 
	Label CoinLabel;

	
	@FXML
	Button BackToRoomPageButton;

    private Stage stage;
    
    private Scene scene;
    
    @FXML
    private AnchorPane settlementPage;
    
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
   	 	settlementPage.getStylesheets().add(getClass().getResource("SettlementPage.css").toExternalForm());

		
		// TODO Auto-generated method stub
		int exp_reward=1700;
		int coin_reward=100;
		NameLabel.setText("玩家暱稱:"+StartPage.player.getName());
		String BeforeLevel = String.valueOf(StartPage.player.getLevel());
		String BeforeCoin = String.valueOf(StartPage.player.getCoin());
		int rest_exp=exp_reward;
		int level_up_exp=StartPage.player.getLevel()*100-StartPage.player.getExp();
		int level_up=0;
		while(true)
		{
			if(rest_exp>=level_up_exp)
			{
				level_up++;
				StartPage.player.setlevel(StartPage.player.getLevel()+1);
				rest_exp-=level_up_exp;
				StartPage.player.setExp(0);
				level_up_exp=StartPage.player.getLevel()*100;
			}else
			{
				StartPage.player.setExp(StartPage.player.getExp()+rest_exp);
				break;
			}
		}
		
		StartPage.player.setCoin(StartPage.player.getCoin()+coin_reward);
		
		LevelLabel.setText("LV. "+BeforeLevel+" →LV. "+StartPage.player.getLevel()+" (+"+exp_reward+" Exp) ");
		CoinLabel.setText("金幣: "+BeforeCoin+" →"+StartPage.player.getCoin()+" (+"+coin_reward+" coin) ");

		System.out.println(StartPage.player.getExp());
		String msg="GameOver:"+StartPage.player.getAccount()+"|";
		msg+=(StartPage.player.getLevel()+"|");
		msg+=(StartPage.player.getExp()+"|");
		msg+=(StartPage.player.getCoin());
		StartPage.player.sendMessage(msg);
		
		
	}
	
}
