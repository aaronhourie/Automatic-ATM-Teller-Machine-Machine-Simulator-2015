/* An abstract class that handles any GUI pane that needs access to the current Account
*/

public abstract class GUI_AccountAccess extends GUI_ViewPort {

	protected Account currentAccount;
	
	public GUI_AccountAccess(String title, String error, GUI_Main ref, Account currentAccount) {
		super(title, error, ref);
		
		this.currentAccount = currentAccount;
	}
}
