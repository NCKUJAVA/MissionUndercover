package UserPackage;


public class User 
{
	private String account;
	private String password;
	private String name;
	private String email;
	private int coin;
	private int exp;
	private int level;
	private int hunter;
	private int sec_bonus;
	private int exp_bonus;
	private int coin_bonus;
	
	public User(String user_account,String user_password,String user_name,String user_email,int user_coin,int user_exp,int user_level,int user_hunter,int user_sec_bonus,int user_exp_bonus,int user_coin_bonus)
	{
		account=user_account;
		password=user_password;
		name=user_name;
		email=user_email;
		coin=user_coin;
		exp=user_exp;
		level=user_level;
		hunter=user_hunter;
		sec_bonus=user_sec_bonus;
		exp_bonus=user_exp_bonus;
		coin_bonus=user_coin_bonus;
		
	}
	public String getAccount()
	{
		return this.account;
	}
	public String getPassword()
	{
		return this.password;
	}
	public String getName()
	{
		return this.name;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public int getCoin()
	{
		return this.coin;
	}
	
	public int getExp()
	{
		return this.exp;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	public int getHunter()
	{
		return this.hunter;
	}
	
	public int getSecBonus()
	{
		return this.sec_bonus;
	}
	

	public int getExpBonus()
	{
		return this.exp_bonus;
	}
	
	public int getCoinBonus()
	{
		return this.coin_bonus;
	}
	
}
