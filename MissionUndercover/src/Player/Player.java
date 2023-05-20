package Player;

public class Player {
	private int coin = 0;
	private String name = "";
	private int level = 1;;
	private int exp = 0;
	private int[] items = {0,0,0,0,0,0};
	private Boolean ready = false;
	
	public Player(String name, int level, int exp, int coin){
		this.coin = coin;
		this.level = level;
		this.name = name;
		this.exp = exp;
	}
	
	public int getCoin() {
		return coin;
	}
	public String getName() {
		return name;
	}
	public int getlevel() {
		return level;
	}
	public int getExp() {
		return exp;
	}
	public int[] getItems() {
		return items;
	}
	
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setlevel(int level) {
		this.level = level;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public void setItems(int[] items) {
		for(int i = 0; i < items.length; i ++) {
			this.items[i] = items[i];
		}
	}
	
	public void setReady(Boolean b) {
		ready = b;
	}
	
	public Boolean getReady() {
		return ready;
	}
}
