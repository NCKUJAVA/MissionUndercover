package maininfo_page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Player.Player;
import UserPackage.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import start_page.StartPage;


public class MaininfoController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    //private static Player player;
    
    @FXML
    private Label AccountLabel;
    @FXML
    private Label LevelLabel;
    @FXML
    private Label NameLabel;
    @FXML
    private Label CoinLabel;
    
    @FXML
    private Label PackHunterLabel;
    @FXML
    private Label PackTimeLabel;
    @FXML
    private Label PackExpLabel;
    @FXML
    private Label PackCoinLabel;
    @FXML
    public void display() {
    	AccountLabel.setText(StartPage.player.getAccount());
    	LevelLabel.setText(String.valueOf(StartPage.player.getLevel()));
    	NameLabel.setText(StartPage.player.getName());
    	CoinLabel.setText(String.valueOf(StartPage.player.getCoin()));
    	
    	PackHunterLabel.setText(String.valueOf(StartPage.player.getItems()[0]));
    	PackTimeLabel.setText(String.valueOf(StartPage.player.getItems()[1]));
    	PackExpLabel.setText(String.valueOf(StartPage.player.getItems()[2]));
    	PackCoinLabel.setText(String.valueOf(StartPage.player.getItems()[3]));
    	//System.out.println("display");
    }
    
	public void initialize(URL arg0, ResourceBundle arg1) {
		display();
		//System.out.println("display");
	}
	
	public void leave(ActionEvent e) throws IOException {
		System.out.println("leave");
		
		Parent root = FXMLLoader.load(getClass().getResource("/mainwindow_page/mainwindow.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to mainwindow");
        
	}

}