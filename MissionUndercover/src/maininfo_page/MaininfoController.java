package maininfo_page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class MaininfoController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private static Player player1;
    
    @FXML
    private Label AccountLabel;
    
    public MaininfoController() {
    	
    }
    
    /*public MaininfoController(Player player1) {
        this.player1 = player1;
    }*/
    public static void PassPlayer(Player player){
    	player1 = player;
    }

	public void display() {
        System.out.println("玩家資訊");
        //System.out.println(player1);
        //System.out.println("金幣數量：" + player1.getCoin());
        // 顯示其他玩家資訊的程式碼
        //AccountLabel.setText(String.valueOf(player1.getCoin()));
    }
    /*
	public void level(ActionEvent e) {
		System.out.println("level");
	}
	
	public void coin(ActionEvent e) {
		System.out.println("coin");
	}
	
	public void props(ActionEvent e) {
		System.out.println("props");
	}
	*/
	
	public void leave(ActionEvent e) throws IOException {
		System.out.println("leave");
		
		Parent root = FXMLLoader.load(getClass().getResource("/mainwindow_page/mainwindow.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to mainwindow");
        
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        AccountLabel.setText(String.valueOf(player1.getCoin()));

	}

}