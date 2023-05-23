package mainshop_page;

import java.io.IOException;

import Player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainshopController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private Player player1;
    
   public MainshopController() {
    	
    }
    
    public MainshopController(Player player1) {
        this.player1 = player1;
    }
	
    public void display() {
        System.out.println("道具商店");
        // 顯示道具列表的程式碼

        // 購買道具的程式碼
        int itemPrice = 10; // 假設道具價格為10金幣
        if (player1.getCoin() >= itemPrice) {
            player1.addCoins(-itemPrice); // 扣除金幣
            System.out.println("購買成功！");
            System.out.println("金幣不足:"+ player1.getCoin());
        } else {
            System.out.println("金幣不足，無法購買。");
        }
    }
    
    
    
	public void leave1(ActionEvent e) throws IOException {
		System.out.println("leave");
		
		Parent root = FXMLLoader.load(getClass().getResource("/mainwindow_page/mainwindow.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to mainwindow");
        
	}
	public void leave2(ActionEvent e) throws IOException {
		System.out.println("leave");
		
		Parent root = FXMLLoader.load(getClass().getResource("/maininfo_page/maininfo.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to playerinfo");
        
	}
	
	
	
	
}