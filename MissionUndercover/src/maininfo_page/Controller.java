package maininfo_page;

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
	public void level(ActionEvent e) {
		System.out.println("level");
	}
	
	public void coin(ActionEvent e) {
		System.out.println("coin");
	}
	
	public void props(ActionEvent e) {
		System.out.println("props");
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