package mainwindow_page;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
	public void room(ActionEvent e) {
		System.out.println("room");
	}

	public void shop(ActionEvent e) {
		System.out.println("shop");
	}
	
	public void leave(ActionEvent e) {
		System.out.println("leave");
	}
	public void getinfo(ActionEvent e) throws IOException {
		System.out.println("info");
		
		Parent root = FXMLLoader.load(getClass().getResource("/maininfo_page/maininfo.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to playerinfo");
        
	}
	public void getshop(ActionEvent e) throws IOException {
		System.out.println("info");
		
		Parent root = FXMLLoader.load(getClass().getResource("/mainshop_page/mainshop.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to shop");
        
	}
	
}