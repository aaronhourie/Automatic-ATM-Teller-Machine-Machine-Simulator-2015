import java.util.ArrayList;
public class ChequingAccount {
	private ArrayList<Bill> bills = new ArrayList<Bill>();
	
	public boolean payBill(Bill b) {
		if (account.funds > Bill.cost) {
			//pay bill?
			return true;
		}
		return false;
	}
	
	public Bill getBill(int n) {
		return bills(n);
	}
}
