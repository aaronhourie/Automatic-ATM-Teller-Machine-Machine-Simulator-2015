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
	private Currency balance;
	private int withdrawLimit;
	private double interest;
	private ArrayList<Activity> activities;
	
	/* Constructor for use with pulling data from the database.
	 * Allows balance to be specified (should be done with a temporary currency object)
	 */
	public Account(String accountNumber, Currency balance){
		
		this.accountNumber = accountNumber;
		// TODO: add a constructor to specify the amount in Currency.
		this.balance = new Currency(0);
		activities = new ArrayList<Activity>();
	}
	
	/**
	 * Deposit funds from ... I dunno?
	 * @return Returns true if transaction completed successfully
	 **/
	public boolean deposit(double amount) {
		//Convert amount argument to int, so it can be stored in DB
		int balanceUpdate = Currency.parse(amount);

		//Prepare query (PreparedStatement would require connection, so...)
		String query = "UPDATE Account SET balance=(balance + " + balanceUpdate 
						+ " ) WHERE account_number='" + getaccountNumber() + "'";
		String details = "Deposited $" + amount;

		//Call transaction(), attempt to update DB, report on success
		Activity action = transaction(query, details);
		
		//If transaction failed, update the Activity
		if (action.getEvent().equals("")) {
			action.setEvent("An error occurred; no funds were deposited");
			return false;
		} else {
			return true;
		}
	}

	/* public void transfer(Account transferTo, Currency amount)
	 * This method takes in an Account and transfers the amount in
	 * a currency object from it's balance and sends it to the
	 * transferTo account.
	 */
	public void transfer(Account transferTo, Currency amount){
		// error checking to prevent stealing funds.
		if (amount.getAmount() > 0){
			// subtracts from own funds.
			balance.subtract(amount);
			// adds to other account.
			transferTo.getBalance().add(amount);
		}
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
			try {
				//Disable autocommit to allow batch processing
				conn.setAutoCommit(false);
				
				//Create Activity information
				act = conn.prepareStatement("INSERT INTO Activity VALUES(?,?,?)");
				act.setString(1, accountNumber);
				act.setString(2, details);
				act.setTimestamp(3, time);

				//Set Activity object for return
				activity = new Activity(accountNumber, details, time);
				
				//Attempt DB push
				act.executeUpdate();
				query.executeUpdate();
				conn.commit();
			} catch (SQLException se) {
				/* 
				 * REMOVE THE STRACKTRACE FOR PRODUCTION!!!
				 */
				se.printStackTrace();
				activity = new Activity(accountNumber, null, time);
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
					if (act != null) {
						act.close();
					}
					if (query != null) {
						query.close();
					}
				} catch (SQLException se2) {
					//Do nothing
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
		//Prepare String for return
		String recent = "";

		//Connect to DB
		Connection conn = DB.connect();

		//On success, poll for recent activity
		if (conn != null) {
			try {
				//Query returns five most recent activities
				String sql = "SELECT * FROM Activity WHERE owner_account=? ORDER BY time DESC LIMIT 5";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, accountNumber);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					Date date = new Date(rs.getTimestamp("time").getTime());
					SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
					String event = rs.getString("details");
					recent += df.format(date) + " >> " + event;
				}
			} catch (SQLException se) {
				/**
					REMOVE BEFORE PRODUCTION!!!
				  **/
				se.printStackTrace();
				recent = "Error: could not retrieve recent activity";
			} finally {
				try {
					conn.close();
				} catch (SQLException se2) {
				}
			}
		}
		return recent;
	}
	
	// getters and setters.
	public String getaccountNumber() {
		return accountNumber;
	}
	public void setaccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Currency getBalance() {
		return balance;
	}
	public void setBalance(Currency balance) {
		this.balance = balance;
	}
	public int getWithdrawLimit() {
		return withdrawLimit;
	}
	public void setWithdrawLimit(int withdrawLimit) {
		this.withdrawLimit = withdrawLimit;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
}
