import java.util.ArrayList;
import java.sql.*;

public class User {
	private String id;
	private ArrayList<Account> accounts;
	
	public User(String id) {
		this.id = id;
		accounts = new ArrayList<Account>();

		//Attempt DB connection
		Connection conn = DB.connect();

		//If successful, populate the User Accounts
		PreparedStatement pstmt;
		if (conn != null) {
			try {
				String query = "SELECT * FROM Account WHERE owner=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					do {
						String accountNumber = rs.getString("account_number");
						Currency balance = new Currency(rs.getInt("balance"));
						double interest = (double)(rs.getInt("interest")) / 100.00;
						int withdrawLimit = rs.getInt("withdrawal_limit");
						int transactionLimit = rs.getInt("transaction_limit");
						int surcharge = rs.getInt("surcharge");
						int creditLimit = rs.getInt("credit_limit");
						int numTransactions = rs.getInt("num_transactions");
						String accountType;
						switch (rs.getInt("type")) {
							case 1: //Savings
								accountType = "Savings";
								accounts.add(new SavingsAccount(accountNumber, accountType, balance, interest));
								break;
							case 2: //Chequing
								accountType = "Chequing";
								accounts.add(new ChequingAccount(accountNumber, accountType, balance, interest, 
														transactionLimit, withdrawLimit, surcharge, numTransactions));
								break;
							case 3: //Credit
								accountType = "Credit";
								accounts.add(new CreditAccount(accountNumber, accountType, balance, interest, 
														withdrawLimit, creditLimit));
								break;
							default: break;
						}
					} while (rs.next());
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
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
		User pullAccount = null;
		PreparedStatement validate = null;
		if (conn != null) {
			try {
				String query = "SELECT * FROM User WHERE user_id=? AND pin=?";
				validate = conn.prepareStatement(query);
				validate.setString(1, uname);
				validate.setString(2, pword);
				ResultSet rs = validate.executeQuery();

				if (rs.next()) {
					String id = rs.getString("user_id");
					pullAccount = new User(id);
				}
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
		return pullAccount;
	}

	public String getId()
	{
		return id;
	}

	public Account getAccount(int index) {
		return accounts.get(index);
	}

	public Account[] getAllAccounts() {
		Account[] accArray = new Account[accounts.size()];
		accArray = accounts.toArray(accArray);
		return accArray;
	}
}
