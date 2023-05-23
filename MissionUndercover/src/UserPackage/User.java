package UserPackage;


public class User 
{
	private String account;
	private String password;
	private String name;
	private String question;
	private String answer;
	private int coin;
	private int exp;
	private int level;
	private int hunter;
	private int sec_bonus;
	private int exp_bonus;
	private int coin_bonus;
	
	public User(String user_account,String user_password,String user_name,String user_question,String user_answer,int user_coin,int user_exp,int user_level,int user_hunter,int user_sec_bonus,int user_exp_bonus,int user_coin_bonus)
	{
		account=user_account;
		password=user_password;
		name=user_name;
		question=user_question;
		answer=user_answer;
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
	
	public String getQuestion()
	{
		return this.question;
	}
	
	public String getAnswer()
	{
		return this.answer;
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
	
	public void setQuestion(String user_question)
	{
		this.question=user_question;
	}
	
	public void setAnswer(String user_answer)
	{
		this.answer=user_answer;
	}
	
}
