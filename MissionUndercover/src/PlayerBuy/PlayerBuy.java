package PlayerBuy;

import Player.Player;
import maininfo_page.MaininfoController;
import mainshop_page.MainshopController;

public class PlayerBuy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player player1 = new Player();
		player1.setCoin(1000); 
		
		MaininfoController playerInfo = new MaininfoController(player1);
		MainshopController itemShop = new MainshopController(player1);

		
		System.out.println("Welcome Game");
		//System.out.println(player1.getCoin());
		//System.out.println(player1.getReady());
		playerInfo.display();
		itemShop.display();
	}
	
	
}
