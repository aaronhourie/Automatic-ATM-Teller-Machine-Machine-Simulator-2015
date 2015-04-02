public abstract class WithdrawableAccount extends Account {
	private int withdrawLimit;
	
	public WithdrawableAccount(String accountNumber, String accountType, Currency balance,
					double interest, int withdrawLimit) {
		super(accountNumber, accountType, balance, interest);
		this.withdrawLimit = withdrawLimit;
	}
	
	public boolean withdraw(int n) {
		if (getBalance().getAmount() > n) {
			//withdraw
			return true;
		}
		return false;
	}
}
