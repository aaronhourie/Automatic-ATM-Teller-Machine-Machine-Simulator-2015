/* Account Abstract Class.
 * What follows is information common to all types of bank 
 * accounts within the system. All other Account types are 
 * inherited from this class.
 */
public abstract class Account {

	private String accountNumber;
	private Currency balance;
	private int withdrawLimit;
	private double interest;
	
	/* Constructor for use when creating new bank accounts.
	 * balance is initialized to 0.
	 */
	
	public Account(String accountNumber){
		
		this.accountNumber = accountNumber;
		// TODO: No-args constructor for currency should initialize value at 0.
		this.balance = new Currency();
	}
	
	/* Constructor for use with pulling data from the database.
	 * Allows balance to be specified (should be done with a temporary currency object)
	 */
	
	public Account(String accountNumber, Currency balance){
		
		this.accountNumber = accountNumber;
		// TODO: add a constructor to specify the amount in Currency.
		this.balance = new Currency();
	}
	
	/* public void transfer(Account transferTo, Currency amount)
	 * This method takes in an Account and transfers the amount in
	 * a currency object from it's balance and sends it to the
	 * transferTo account.
	 */
	public void transfer(Account transferTo, Currency amount){
		// error checking to prevent stealing funds.
		if (amount.getAmount() > 0){
			// subtracts from own funds.
			balance.subtract(amount);
			// adds to other account.
			transferTo.getBalance().add(amount);
		}
	}
	
	/* public String getRecentActivity
	 * This method hooks into the database to retrieve the recent transactions on
	 * the current account. Returns it in a multi line string.
	 */
	public String getRecentActivity(){
		// TODO: hook into database to retrieve recent activity.
		return null;
	}
	
	// getters and setters.
	public String getaccountNumber() {
		return accountNumber;
	}
	public void setaccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Currency getBalance() {
		return balance;
	}
	public void setBalance(Currency balance) {
		this.balance = balance;
	}
	public int getWithdrawLimit() {
		return withdrawLimit;
	}
	public void setWithdrawLimit(int withdrawLimit) {
		this.withdrawLimit = withdrawLimit;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	
	
	
}
