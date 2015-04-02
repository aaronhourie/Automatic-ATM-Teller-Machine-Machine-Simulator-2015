import java.util.ArrayList;
import java.sql.*;

public class ChequingAccount extends WithdrawableAccount {
	private ArrayList<Bill> bills = new ArrayList<Bill>();
	
	/** public Bill addBill(String name, String account, int payment)
	 ** Creates a Bill object, stores it in the user's DB, and adds
	 ** to open Bill arraylist.
	 ** @return Bill object
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
				add.setString(4, getaccountNumber());

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
				try {
					if (add != null) {
						add.close();
					}
					if (conn != null) {
						conn.close();
					}
				}
			}
		}
		return bill;
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