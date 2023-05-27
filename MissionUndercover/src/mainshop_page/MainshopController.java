package mainshop_page;

import java.io.IOException;

import Player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import start_page.StartPage;


public class MainshopController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    int[] itemCost = {30,20,10, 5};
    int[] quantity = {0, 0, 0, 0};
    
    //private int quantity = 0;  
    //private int itemHunterCost = 30;
    //private int itemTimeCost = 20;
    //private int itemExpCost = 10;
    //private int itemCoinCost = 5;
    
    @FXML
    private Label payTotal;
    
    @FXML
    private Label quantityHunter;
    
    @FXML
    private Label payHunter;
    
    @FXML
    private Label quantityTime;
    
    @FXML
    private Label payTime;
    
    @FXML
    private Label quantityExp;
    
    @FXML
    private Label payExp;
    
    @FXML
    private Label quantityCoin;
    
    @FXML
    private Label payCoin;
    
    
    
    @FXML
    private Button PurchaseButton;

    @FXML
    private Button increaseHunterButton;
    
    @FXML
    private Button decreaseHunterButton;
    
    @FXML
    private Button increaseTimeButton;
    
    @FXML
    private Button decreaseTimeButton;
    
    @FXML
    private Button increaseExpButton;
    
    @FXML
    private Button decreaseExpButton;
    
    @FXML
    private Button increaseCoinButton;
    
    @FXML
    private Button decreaseCoinButton;
    
  
    
    public void initialize() {
    	updateHunterQuantityLabel();
    	updateTimeQuantityLabel();
    	updateExpQuantityLabel();
    	updateCoinQuantityLabel();    	
    }
    
   
    public void increaseHunterQuantity(ActionEvent e) {
        quantity[0]++;
        updateHunterQuantityLabel();
    }
    
    public void increaseTimeQuantity(ActionEvent e) {
        quantity[1]++;
        updateTimeQuantityLabel();
    }
    
    public void increaseExpQuantity(ActionEvent e) {
        quantity[2]++;
        updateExpQuantityLabel();
    }
    
    public void increaseCoinQuantity(ActionEvent e) {
        quantity[3]++;
        updateCoinQuantityLabel();
    }
    
    
    public void decreaseHunterQuantity(ActionEvent e) {
        if (quantity[0] > 0) {
            quantity[0]--;
            updateHunterQuantityLabel();
        }
    }
    
    public void decreaseTimeQuantity(ActionEvent e) {
        if (quantity[1] > 0) {
            quantity[1]--;
            updateTimeQuantityLabel();
        }
    }
    
    public void decreaseExpQuantity(ActionEvent e) {
        if (quantity[2] > 0) {
            quantity[2]--;
            updateExpQuantityLabel();
        }
    }
    
    public void decreaseCoinQuantity(ActionEvent e) {
        if (quantity[3] > 0) {
            quantity[3]--;
            updateCoinQuantityLabel();
        }
    }
    
    private void updateHunterQuantityLabel() {
    	//quantityHunter.setText(String.valueOf(quantityHunter));
    	//quantity[0] = Integer.parseInt(quantity[0].getText());
        int hunterCost = itemCost[0] * quantity[0];
        
    	quantityHunter.setText(String.valueOf(quantity[0]));
    	payHunter.setText(String.valueOf(hunterCost));
        //System.out.println(quantity);
    	int totalCost = updatePayTotal();
    }
    
    
    private void updateTimeQuantityLabel() {
    	//quantity[1] = Integer.parseInt(quantityTime.getText());
        int timeCost = itemCost[1]* quantity[1];
    	
    	quantityTime.setText(String.valueOf(quantity[1]));
    	payTime.setText(String.valueOf(timeCost));
    	int totalCost = updatePayTotal();
    }
    
    private void updateExpQuantityLabel() {
    	//quantity[2] = Integer.parseInt(quantityExp.getText());
        int expCost = itemCost[2]* quantity[2];

        quantityExp.setText(String.valueOf(quantity[2]));
    	payExp.setText(String.valueOf(expCost));
    	int totalCost = updatePayTotal();
    }
    
    private void updateCoinQuantityLabel() {
    	//quantity[3] = Integer.parseInt(quantityCoin.getText());
        int coinCost = itemCost[3] * quantity[3];
        
    	quantityCoin.setText(String.valueOf(quantity[3]));
    	payCoin.setText(String.valueOf(coinCost));
    	int totalCost = updatePayTotal();
    }
    
    private int updatePayTotal() {
    	int hunterCost = itemCost[0] * quantity[0];
    	int timeCost = itemCost[1]* quantity[1];
    	int expCost = itemCost[2]* quantity[2];
    	int coinCost = itemCost[3] * quantity[3];
    	int totalCost = hunterCost+timeCost+expCost+coinCost ;
    	
    	payTotal.setText(String.valueOf(totalCost));
    	return totalCost;
    }
        
    public void purchase() {
    	int totalCost = updatePayTotal();

        if (StartPage.player.getCoin() >= totalCost) {
        	StartPage.player.addCoins(-totalCost); // 扣除金幣
            System.out.println("購買成功！");
            // Show a pop-up message with the purchase information
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Purchase Successful");
            alert.setHeaderText(null);
            alert.setContentText("購買成功!\n" +
                    "金幣餘額: " + StartPage.player.getCoin());

            alert.showAndWait();
            
            //System.out.println("金幣不足:"+ player1.getCoin());
        } else {
            System.out.println("金幣不足，無法購買。");
            Alert alert = new Alert(AlertType.INFORMATION);
            int inadequateCoin = -(StartPage.player.getCoin()-totalCost);
            alert.setTitle("Purchase Failed");
            alert.setHeaderText(null);
            alert.setContentText("購買失敗!\n" +
                    "金幣餘額: " + inadequateCoin);

            alert.showAndWait();
        }
    	
    }  
    
	public void leaveMainwindow(ActionEvent e) throws IOException {
		System.out.println("leave");
		
		Parent root = FXMLLoader.load(getClass().getResource("/mainwindow_page/mainwindow.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("switch to mainwindow");
        
	}

	
}