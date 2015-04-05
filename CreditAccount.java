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

	public boolean transfer(String transferTo, double amount) {
		if ((Currency.parse(amount) - getBalance().getAmount()) >= creditLimit.getAmount()) {
			failedActivity("Cannot transfer specified funds; check your balance");
			return false;
		} else {
			return super.transfer(transferTo, amount);
		}
	}

	public boolean withdraw(double amount) {
		int balanceUpdate = Currency.parse(amount);

		//Withdrawal amount must not push the account over the credit limit
		if (balanceUpdate > 0 && -(getBalance().getAmount()) < creditLimit.getAmount()) {
			return super.withdraw(balanceUpdate);
		} else {
			withdrawFail();
			return false;
		}
	}
}
