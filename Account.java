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
		Activity action = transaction(query, details, accountNumber);
		
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
	 * Streamlines failed transactions
	 **/
	public void failedActivity(String event) {
		Timestamp time = new Timestamp(new Date().getTime());
		activities.add(new Activity(accountNumber, event, time));
	}

	/**
	 * This method takes in an Account and transfers the amount in
	 * a double from its balance and sends it to the
	 * transferTo account.
	 * @return Returns true on transfer success
	 **/
	public boolean transfer(String transferTo, double amount) {
		//Convert amount argument to int, so it can be stored in DB
		int balanceUpdate = Currency.parse(amount);
		Currency amountForm = new Currency(balanceUpdate);

		//Prepare Activity for record-keeping
		Activity action = null;

		//Prevent negative values and values greater than account balance,
		//allow for Credit accounts
		if (balanceUpdate > 0 && (balanceUpdate <= balance.getAmount() ||
			accountType.equals("Credit"))) {
			//Connect to database
			Connection conn = DB.connect();

			if (conn != null) {
				//Check if target account exists
				String testQuery = "SELECT * FROM Account WHERE account_number=?";
				boolean targetExists = false;
				try (PreparedStatement accTest = conn.prepareStatement(testQuery)) {
					//Set query parameters
					accTest.setString(1, transferTo);

					//If ResultSet contains something, then account does indeed exist
					try (ResultSet rs = accTest.executeQuery()) {
						if (rs.next()) {
							targetExists = true;
						}
					}
				} catch (SQLException se) {
					System.out.println("Error while checking target account");
					se.printStackTrace();
				}

				//Close DB connection
				try {
					conn.close();
				} catch (SQLException se) { }

				//If the target account checks out, attempt transactions
				if (targetExists) {
					//Prepare query for removal from origin account 
					String query = "UPDATE Account SET balance=(balance - " + balanceUpdate
								+ " ) WHERE account_number='" + getAccountNumber() + "'";
					String details = "Transferred " + amountForm + " to ACCO#" + transferTo;
					
					//Call transaction(), attempt to update DB, report on success
					action = transaction(query, details, accountNumber);
			

					//On success, update balance and deposit into target account
					if (action.wasSuccessful()) {
						balance.subtract(new Currency(balanceUpdate));

						//Prepare query for addition to target account
						query = "UPDATE Account SET balance=(balance + " + balanceUpdate 
									+ " ) WHERE account_number='" + transferTo + "'";
						details = "Received " + amountForm + " from ACCO#:" + getAccountNumber();

						//Push to DB, hope for the best!
						transaction(query, details, transferTo);
					} else {
						action.setEvent("An error occurred; possible connection error");
					}
				} else {
					failedActivity("Could not complete transfer; target account doesn't exist");
					return false;
				}
			} else {
				failedActivity("Transaction not completed, there was a connection error");
			}

			//Return success and make record
			activities.add(action);
			return true;
		} else {
			failedActivity("Cannot transfer specified funds; check your balance");
			return false;
		}
	}
	
	 /** 
	  *	Should be called inside every transaction
	  * to ensure all affected DB data is properly
	  *	updated. Requires accountNumber for non-source
	  * transactions (ie transfers)
	  *	@return Activity record of the transaction
	  **/
	public Activity transaction(String query, String details, String accountNumber) {
		//Record time of transaction, prep Activity object
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		Activity activity = null;

		//If no query was supplied, there is nothing to do!
		if (query != null) {
			//Establish a DB connection
			Connection conn = DB.connect();

			if (conn != null) {
				//Increase number of transactions
				String transup = "UPDATE Account SET num_transactions=(num_transactions + 1) WHERE account_number=?";
				//Create activity information
				String actinfo = "INSERT INTO Activity VALUES(?,?,?)";

				//Update database
				try (PreparedStatement act = conn.prepareStatement(actinfo);
					PreparedStatement update = conn.prepareStatement(query);
					PreparedStatement addTrans = conn.prepareStatement(transup)) {
						//Disable autocommit to allow batch processing
						conn.setAutoCommit(false);
						
						//Increase number of transactions
						addTrans.setString(1, accountNumber);

						//Create Activity information
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
				}

				//Close DB connection
				try {
					conn.close();
				} catch (SQLException se) {	}
			} else {
				activity = new Activity(accountNumber, null, time);
			}
		} else {
			//Fail on no query
			activity = new Activity(accountNumber, null, time);
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
		String recentQuery = "SELECT * FROM Activity WHERE owner_account=? ORDER BY time DESC LIMIT 5";
		if (conn != null) {
			try (PreparedStatement getAct = conn.prepareStatement(recentQuery)) {
				//Query returns five most recent activities
				getAct.setString(1, accountNumber);

				//Format and add all results to return string
				try (ResultSet rs = getAct.executeQuery()) {
					while (rs.next()) {
						Date date = new Date(rs.getTimestamp("time").getTime());
						SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
						String event = rs.getString("details") + "\n";
						recent += df.format(date) + " >> " + event;
					}
				} catch (SQLException se2) {
					System.out.println("ResultSet was buggered");
					se2.printStackTrace();
					recent = "Error: could not retrieve recent activity";
				}
			} catch (SQLException se) {
				System.out.println("Error while attempting to read from database");
				se.printStackTrace();
				recent = "Error: could not retrieve recent activity";
			}

			//Close DB connection
			try {
				conn.close();
			} catch (SQLException se) { } 
		} else {
			recent = "Could not retrieve recent activity: there was a connection error";
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
