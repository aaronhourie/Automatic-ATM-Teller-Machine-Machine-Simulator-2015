import java.util.ArrayList;

public class ChequingAccount extends WithdrawableAccount {
	private ArrayList<Bill> bills = new ArrayList<Bill>();
	
	public Bill addBill(String name, String account, int payment) {
		//DB stuff
		return new Bill(name, account, payment);
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
