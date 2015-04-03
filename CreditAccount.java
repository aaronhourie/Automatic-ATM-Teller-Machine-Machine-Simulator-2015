public class CreditAccount extends WithdrawableAccount {
	private Currency creditLimit;
	
	public CreditAccount(String accountNumber, String accountType, Currency balance,
					double interest, int withdrawLimit, int creditLimit) {
		super(accountNumber, accountType, balance, interest, withdrawLimit);
		this.creditLimit = new Currency(creditLimit);
	}

	/**
	 * You cannot win.
	 * @return Always returns false.
	 **/
	public boolean applyForIncrease() {
		return false;
	}
	
	public Currency getCreditLimit() {
		return creditLimit;
	}
}
