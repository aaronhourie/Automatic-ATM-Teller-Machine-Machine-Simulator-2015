import java.util.ArrayList;
import java.sql.*;

public class User 
{
	private String id;
	private ArrayList<CreditAccount> creditAccounts; //Type 1
	private ArrayList<SavingsAccount> savingsAccounts; //Type 2
	private ArrayList<ChequingAccount> chequingAccount; //Type 3
	
	public User() {
		id = null;
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

	public static User login(String uname, String pword)
	{
		//If nothing supplied, auto-fail
		if (uname == "" || pword == "") {
			return null;
		}

		//Connect to DB
		Connection conn = DB.connect();

		//On connection success, poll for User
		boolean success = false;
		PreparedStatement validate = null;
		if (conn != null) {
			try {
				String query = "SELECT * FROM User WHERE user_id=? AND pin=?";
				validate = conn.prepareStatement(query);
				validate.setString(1, uname);
				validate.setString(2, pword);
				ResultSet rs = validate.executeQuery();

				success = (rs.next()) ? true : false;
			} catch (SQLException se) {
				se.printStackTrace();
			} finally {
				try {
					if (validate != null) {
						validate.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException se2) {
				}
			}
		}
		return success;
	}
}
