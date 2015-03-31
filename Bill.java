public class Bill {
	private String payeeName;
	private String payeeAccount;
	private Currency regularPayment;

	//NOTE! Constructor takes <int> for payment, because that is how
	//the payment is stored in the DB.
	public Bill(String name, String account, int payment) {
		payeeName = name;
		payeeAccount = account;
		regularPayment = new Currency(payment);
	}

	public String getPayeeName() {
		return name;
	}

	public String getPayeeAccount() {
		return account;
	}

	public Currency getRegularPayment() {
		return regularPayment;
	}

	public void setPayeeName(String name) {
		payeeName = name;
	}

	public void setPayeeAccount(String account) {
		payeeAccount = account;
	}

	public void setRegularPayment(Currency payment) {
		regularPayment = payment;
	}
}