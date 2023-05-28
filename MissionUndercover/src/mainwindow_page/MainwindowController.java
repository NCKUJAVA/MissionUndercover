package mainwindow_page;

import java.io.IOException;

import Player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maininfo_page.MaininfoController;


public class MainwindowController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
	public void getRoom(ActionEvent e) throws IOException {
		System.out.println("room");
		
		Parent root = FXMLLoader.load(getClass().getResource("/Room/Room.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to room");
        
	}
	

	public void leave(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/start_page/StartPageFXML.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}

	public void getInfo(ActionEvent e) throws IOException {
		System.out.println("info");
		
		//Player player1 = new Player();
		//player1.setCoin(1000); 
		//MaininfoController.PassPlayer(player1);
		
		Parent root = FXMLLoader.load(getClass().getResource("/maininfo_page/maininfo.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to playerinfo");
        
	}
	public void getShop(ActionEvent e) throws IOException {
		System.out.println("shop");
		
		Parent root = FXMLLoader.load(getClass().getResource("/mainshop_page/mainshop.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to itemshop");
        
	}
	

	
}