import java.sql.*;
import java.util.Date;

public abstract class WithdrawableAccount extends Account {
	private Currency withdrawLimit;
	
	public WithdrawableAccount(String accountNumber, String accountType, Currency balance,
					double interest, int withdrawLimit) {
		super(accountNumber, accountType, balance, interest);
		this.withdrawLimit = new Currency(withdrawLimit);
	}

	/**
	 * Withdraw funds from account
	 * @return Returns true if transaction completed successfully
	 **/
	public boolean withdraw(int balanceUpdate) {
		//Convert amount argument to int, so it can be stored in DB
		Currency amountForm = new Currency(balanceUpdate);

		//Prepare query (PreparedStatement would require connection, so...)
		String query = "UPDATE Account SET balance=(balance - " + balanceUpdate
						+ " ) WHERE account_number='" + getAccountNumber() + "'";
		String details = "Withdrew " + amountForm;

		//Call transaction(), attempt to update DB, report on success
		Activity action = transaction(query, details);
		
		//On success, update balance
		if (action.wasSuccessful()) {
			getBalance().subtract(new Currency(balanceUpdate));
		} else {
			action.setEvent("An error occurred; no funds were withdrawn");
		}

		//Return succeed and make record
		getActivities().add(action);
		return action.wasSuccessful();
	}

	public void withdrawFail() {
			//If input was invalid, create fail activity
			String event = "Could not complete withdrawal; invalid input";
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());

			Activity action = new Activity(getAccountNumber(), event, time);
			getActivities().add(action);
	}

	public Currency getWithdrawLimit() {
		return withdrawLimit;
	}
}
