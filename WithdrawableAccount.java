public class WithdrawableAccount extends Account {
	private int transactionLimit;
	private int surcharge;
	private int numTransactions;
	
	public WithdrawableAccount() {
		//NICK, DATABASE
		super(null);
		numTransactions = 0;
	}
	
	public int getTransactionLimit() {
		return transactionLimit;
	}
	
	public void applySurcharge() {
		if (numTransactions > transactionLimit) {
			//apply surcharge?
			//Update DB
			//numTransactions--;
		}
	}
	
	public boolean withdraw(int n) {
		if (getBalance().getAmount() > n) {
			//withdraw
			return true;
		}
		return false;
	}
}
