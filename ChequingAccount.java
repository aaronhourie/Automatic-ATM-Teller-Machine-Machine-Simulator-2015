import java.util.ArrayList;
import java.sql.*;

public class ChequingAccount extends WithdrawableAccount {
	private ArrayList<Bill> bills;
	private int transactionLimit;
	private int numTransactions;
	private int surcharge;
	
	/** public Bill addBill(String name, String account, int payment)
	 ** Creates a Bill object, stores it in the user's DB, and adds
	 ** to open Bill arraylist.
	 ** @return Bill object
	 **/

	public ChequingAccount(String accountNumber, String accountType, Currency balance,
					double interest, int transactionLimit, int withdrawLimit, int surcharge,
					int numTransactions) {
		super(accountNumber, accountType, balance, interest, withdrawLimit);
		this.transactionLimit = transactionLimit;
		this.numTransactions = numTransactions;
		this.surcharge = surcharge;
		bills = new ArrayList<Bill>();
	}

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
				/**
					REMOVE STRACK TRACE!
				**/
				se.printStackTrace();
			} finally {
			}
		}
		return bill;
	}

	public void applySurcharge() {
		if (numTransactions > transactionLimit) {
			//apply surcharge?
			//Update DB
			//numTransactions--;
		}
	}

	public boolean payBill(int i) {
		if (getBalance().getAmount() > bills.get(i).getRegularPayment().getAmount()) {
			//pay bill?
			return true;
		}
		return false;
	}
	
	public Bill getBill(int n) {
		return bills.get(n);
	}
}

/*
Add Bill
Remove Bill
Update Bill
Read Bill
*/