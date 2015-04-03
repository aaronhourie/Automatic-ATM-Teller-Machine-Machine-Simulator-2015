import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/* Account Abstract Class.
 * What follows is information common to all types of bank 
 * accounts within the system. All other Account types are 
 * inherited from this class.
 */
public abstract class Account {
	private String accountNumber;
	private String accountType;
	private Currency balance;
	private double interest;
	private ArrayList<Activity> activities;
	
	public Account(String accountNumber, String accountType, Currency balance,
					double interest) {
		
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.interest = interest;
		activities = new ArrayList<Activity>();
	}
	
	/**
	 * Deposit funds from ... I dunno?
	 * @return Returns true if transaction completed successfully
	 **/
	public boolean deposit(double amount) {
		//Convert amount argument to int, so it can be stored in DB
		int balanceUpdate = Currency.parse(amount);
		Currency amountForm = new Currency(balanceUpdate);

		//Prepare query (PreparedStatement would require connection, so...)
		String query = "UPDATE Account SET balance=(balance + " + balanceUpdate 
						+ " ) WHERE account_number='" + getAccountNumber() + "'";
		String details = "Deposited " + amountForm;

		//Call transaction(), attempt to update DB, report on success
		Activity action = transaction(query, details);
		
		//On success, update balance
		if (action.wasSuccessful()) {
			balance.add(new Currency(balanceUpdate));
		} else {
			action.setEvent("An error occurred; no funds were deposited");
		}

		//Return succeed and make record
		activities.add(action);
		return action.wasSuccessful();
	}

	/**
	 * This method takes in an Account and transfers the amount in
	 * a currency object from it's balance and sends it to the
	 * transferTo account.
	 * @return Returns true on transfer success
	 **/
	public boolean transfer(String transferTo, double amount) {
		//Convert amount argument to int, so it can be stored in DB
		int balanceUpdate = Currency.parse(amount);
		Currency amountForm = new Currency(balanceUpdate);

		//Prepare Activity for record-keeping
		Activity action = null;

		//Prevent negative values and values greater than account balance
		if (balanceUpdate > 0 && balanceUpdate <= balance.getAmount()) {
			//Prepare query for removal from origin account 
			String query = "UPDATE Account SET balance=(balance - " + balanceUpdate
						+ " ) WHERE account_number='" + getAccountNumber() + "'";
			String details = "Transferred " + amountForm + " to ACCO#" + transferTo;
			
			//Call transaction(), attempt to update DB, report on success
			action = transaction(query, details);

			//On success, update balance and deposit into target account
			if (action.wasSuccessful()) {
				balance.subtract(new Currency(balanceUpdate));

				//Prepare query for addition to target account
				query = "UPDATE Account SET balance=(balance + " + balanceUpdate 
							+ " ) WHERE account_number='" + transferTo + "'";
				details = "Received " + amountForm + " from ACCO#:" + getAccountNumber();

				//Push to DB, hope for the best!
				transaction(query, details);
			} else {
				action.setEvent("An error occurred; transfer was not completed");
			}
		} else {
			String event = "Could not complete transfer; invalid input";
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());

			action = new Activity(getAccountNumber(), event, time);
			getActivities().add(action);
			return false;
		}

		//Return succeed and make record
		activities.add(action);
		return true;
	}
	
	 /** 
	  *	Should be called inside every transaction
	  * to ensure all affected DB data is properly
	  *	updated.
	  *	@return Activity record of the transaction
	  **/
	public Activity transaction(String query, String details) {
		//Record time of transaction, prep Activity object
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		Activity activity = null;

		//Establish a DB connection
		Connection conn = DB.connect();

		//On success, attempt to update the account
		if (conn != null && query != null) {

			PreparedStatement act = null;
			PreparedStatement update = null;
			PreparedStatement addTrans = null;
			try {
				//Disable autocommit to allow batch processing
				conn.setAutoCommit(false);

				//Prepare query from base action
				update = conn.prepareStatement(query);
				
				//Increase number of transactions
				addTrans = conn.prepareStatement("UPDATE Account SET num_transactions=(num_transactions + 1) WHERE account_number=?");
				addTrans.setString(1, accountNumber);

				//Create Activity information
				act = conn.prepareStatement("INSERT INTO Activity VALUES(?,?,?)");
				act.setString(1, accountNumber);
				act.setString(2, details);
				act.setTimestamp(3, time);

				//Set Activity object for return
				activity = new Activity(accountNumber, details, time);
				
				//Attempt mega DB push
				act.executeUpdate();
				update.executeUpdate();
				addTrans.executeUpdate();
				conn.commit();
			} catch (SQLException se) {
				System.out.println("Error while attempting to write to database");
				se.printStackTrace();
				activity = new Activity(accountNumber, null, time);
			} finally {
				try {
					if (act != null) {
						act.close();
					}
					if (update != null) {
						update.close();
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
		return activity;
	}

	/** 
	 * This method hooks into the database to retrieve the recent transactions on
	 * the current account.
	 * @return String of compiled activities
	 **/
	public String getRecentActivity() {
		//Prepare return string
		String recent = "";

		//Connect to DB
		Connection conn = DB.connect();

		//On success, poll for recent activity
		PreparedStatement getAct = null;
		if (conn != null) {
			try {
				//Query returns five most recent activities
				String sql = "SELECT * FROM Activity WHERE owner_account=? ORDER BY time DESC LIMIT 5";
				getAct = conn.prepareStatement(sql);
				getAct.setString(1, accountNumber);
				ResultSet rs = getAct.executeQuery();

				//Format and add all results to return string
				while (rs.next()) {
					Date date = new Date(rs.getTimestamp("time").getTime());
					SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
					String event = rs.getString("details") + "\n";
					recent += df.format(date) + " >> " + event;
				}
			} catch (SQLException se) {
				System.out.println("Error while attempting to write to database");
				se.printStackTrace();
				recent = "Error: could not retrieve recent activity";
			} finally {
				try {
					if (getAct != null) {
						getAct.close();
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
		return recent;
	}
	
	// getters and setters.
	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public ArrayList<Activity> getActivities() {
		return activities;
	}

	public Currency getBalance() {
		return balance;
	}

	public double getInterest() {
		return interest;
	}

	public String getLastEvent() {
		int last = activities.size() - 1;
		return activities.get(last).getEvent();
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setBalance(Currency balance) {
		this.balance = balance;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public String toString() {
		return "ACCO#" + accountNumber + " Balance: " + balance;
	}
}
