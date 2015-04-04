import java.util.ArrayList;
import java.sql.*;
import java.util.Date;

public class ChequingAccount extends WithdrawableAccount {
	private ArrayList<Bill> bills;
	private int transactionLimit;
	private int numTransactions;
	private int surcharge;

	public ChequingAccount(String accountNumber, String accountType, Currency balance,
					double interest, int withdrawLimit, int transactionLimit, int surcharge,
					int numTransactions) {
		super(accountNumber, accountType, balance, interest, withdrawLimit);
		this.transactionLimit = transactionLimit;
		this.numTransactions = numTransactions;
		this.surcharge = surcharge;
		bills = new ArrayList<Bill>();
	}

	/** 
	 * Creates a Bill object, stores it in the user's DB, and adds
	 * to open Bill arraylist.
	 * @return Bill object
	 **/
	public Bill addBill(String name, String account, int payment) {
		//Prep Bill object for return
		Bill bill = null;

		//Establish DB connection
		Connection conn = DB.connect();

		//On success, attempt to add bill to DB
		if (conn != null) {
			PreparedStatement add = null;
			try {
				//Set up query: payee_name, payee_account, payment, owner_account
				add = conn.prepareStatement("INSERT INTO Bill VALUES(?,?,?,?)");
				add.setString(1, name);
				add.setString(2, account);
				add.setInt(3, payment);
				add.setString(4, getAccountNumber());

				//Push to DB
				add.executeUpdate();

				//Add to current Bills
				bill = new Bill(name, account, payment);
			} catch (SQLException se) {
				System.out.println("Error while attempting to write Bill to database");
				se.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
					if (add != null) {
						add.close();
					}
				} catch (SQLException se2) {
					System.out.println("Error while attempting to close MySQL objects");
					se2.printStackTrace();
				}
			}
		}
		return bill;
	}

	/**
	 * Checks if a surcharge is necessary
	 * @return Returns surcharge or 0 if no surcharge required
	 **/
	private double addSurcharge() {
		if (numTransactions >= transactionLimit) {
			return surcharge / 100.00;
		} else {
			return 0.0;
		}
	}

	/**
	 * Specialized for Chequing accounts, as they have
	 * a surcharge after a max number of transactions
	 * are completed.
	 * @return Success of withdrawal action
	 **/
	public boolean withdraw(double amount) {
		//Adds surcharge if necessary
		amount += addSurcharge();

		//Convert amount argument to int, so it can be stored in DB
		int balanceUpdate = Currency.parse(amount);

		//Only allow if funds are available, and amount does not exceed withdraw limit
		if (balanceUpdate > 0 && getBalance().getAmount() >= balanceUpdate
				&& balanceUpdate <= getWithdrawLimit().getAmount()) {

			//Normal withdraw, only increase transactions on success
			if (super.withdraw(balanceUpdate)) {
				numTransactions++;
				return true;
			} else {
				return false;
			}
		} else {
			withdrawFail();
			return false;
		}
	}

	/**
	 * Not implemented as Bills were not used
	public boolean payBill(int i) {
		if (getBalance().getAmount() > bills.get(i).getRegularPayment().getAmount()) {
			//pay bill?
			return true;
		}
		return false;
	}
	 **/
	
	public Bill getBill(int index) {
		return bills.get(index);
	}
}