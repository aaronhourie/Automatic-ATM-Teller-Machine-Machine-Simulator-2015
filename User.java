import java.util.ArrayList;
//this class hold the users id and pin
//as well as an array list of their accounts
public class User 
{
	private String id;
	private String pin;
	private ArrayList <Account> accounts;
	
	public User()
	{
		id = null;
		pin = null;
		accounts = new ArrayList <Account>();
	}
	public String getId()
	{
		return id;
	}
	public Account getAccount(Account a)
	{
		//find their account and return?
		//change arguments?
		return a;
	}
	public boolean login(String uname, String pword)
	{
		//user authentication?
		return false;
	}
}
