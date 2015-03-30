
public class WithdrawableAccount {
	private int transactionLimit;
	private int surcharge;
	int numTransactions;
	
	public WithdrawableAccount() {
		//NICK, DATABASE
		numTransactions = null;
	}
	
	public int getTransactionLimit() {
		return transactionLimit;
	}
	
	public void applySurcharge() {
		if (numTransactions>transactionLimit) {
			//apply surcharge?
		}
	}
	
	public boolean withdraw(int n) {
		if (account.money > n) {
			//withdraw
			return true;
		}
		return false;
	}
}
