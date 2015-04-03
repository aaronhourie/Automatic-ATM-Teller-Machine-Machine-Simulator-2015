import java.util.ArrayList;
import java.sql.*;

public class User {
	private String id;
	private ArrayList<Account> accounts;
	
	/**
	 * User objects are only constructed inside the User::login method.
	 * All accounts found related to the User will be reconstructed
	 * as the appropriate type of Account subclass.
	 * @param id User's ID number, as stored in DB
	 **/
	public User(String id) {
		this.id = id;
		accounts = new ArrayList<Account>();

		//Attempt DB connection
		Connection conn = DB.connect();

		//If successful, populate the User Accounts
		PreparedStatement construct = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				String query = "SELECT * FROM Account WHERE owner=?";
				construct = conn.prepareStatement(query);
				construct.setString(1, id);

				//Gather all linked accounts
				rs = construct.executeQuery();

				//For every row returned, populate an account with the corresponding data
				if (rs.next()) {
					//If ResultSet not empty, at least one account must exist
					do {
						String accountNumber = rs.getString("account_number");
						Currency balance = new Currency(rs.getInt("balance"));
						double interest = (double)(rs.getInt("interest")) / 100.00;
						int withdrawLimit = rs.getInt("withdrawal_limit");
						int transactionLimit = rs.getInt("transaction_limit");
						int surcharge = rs.getInt("surcharge");
						int creditLimit = rs.getInt("credit_limit");
						int numTransactions = rs.getInt("num_transactions");

						//Determine account type and construct the new object as that type
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
							default: //Something is wrong
								break;
						}
					} while (rs.next());
				}
			} catch (SQLException se) {
				System.out.println("Error while attemping to populate User object");
				se.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (construct != null) {
						construct.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException se2) {
					System.out.println("Error while attempting to close MySQL objects");
					se2.printStackTrace();
				}
			}
		}
	}

	/**
	 * Attempt to validate user by comparing supplied credentials
	 * with those stored in the DB.
	 * @return Fully populated User object, or NULL on fail
	 **/
	public static User login(String uname, String pword)
	{
		//If no credentials supplied, auto-fail
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
				System.out.println("Error while attempting to write to the database");
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
					System.out.println("Error while attempting to close MySQL objects");
					se2.printStackTrace();
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

	/**
	 * @return All accounts as an Account[] array.
	 * Accounts must be up-casted before using
	 **/
	public Account[] getAllAccounts() {
		Account[] accArray = new Account[accounts.size()];
		accArray = accounts.toArray(accArray);
		return accArray;
	}
}
