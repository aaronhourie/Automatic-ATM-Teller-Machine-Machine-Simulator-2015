import javax.swing.*;
import java.awt.*;

/* GUI_Overview shows all accounts belonging to a user, and lets the user
 * navigate to the account they wish to use.
 * Backing out of this panel causes the user to log out.
 */
public class GUI_UserOverview extends GUI_ViewPort{

	private JPanel container;
	private JList accountList;
	private JLabel lbl_account;
	private Account[] accounts; 
	private int index;
	
	public GUI_UserOverview(String title, String u_id, GUI_Main ref) {
		super(title, u_id, ref);
		
		// gets accounts from user
		accounts = ref.getUser().getAllAccounts();
		// sets scroll index to top.
		index = 0;
		container = new JPanel();
		container.setLayout(new GridLayout(1, 2));
		accountList = new JList(accounts);
		// set focus to the first object
		accountList.setSelectedIndex(index);
		lbl_account = new JLabel("Accounts:");
		// adds items
		container.add(lbl_account);
		container.add(accountList);
		add(container);
	}

	/* Implementing the API.
	 */
	public void buttonPress(String button){
	
		if (button.equals("SELECT")){
			select();
		}
		else if(button.equals("BACK")){
			ref.logout();
		}
		else if(button.equals("UP") ||button.equals("DOWN")){
			// scrolls account list
			scroll(button);
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			// do nothing
		}
		else if (button.equals("DELETE")){
			// do nothing
		}
		else {
			// do nothing
		}
	}
	
	/* Provides scrolling functionality to the list of accounts.
	 */
	public void scroll(String direction){
		
		// either adds or subtracts to the variable index, then
		// sets the current index to focus on as index.
		if (direction.equals("UP") && index != 0){
			
			index --;
		}
		else if (direction.equals("DOWN") && index < accounts.length -1) {
			
			index ++;
		}
		// move "cursor"
		accountList.setSelectedIndex(index);
	}
	
	/* This moves the user to the currently selected account.
	 */
	public void select(){
		// passes a reference to the chosen account and sets the title to account number and balance.
		ref.changeViewPort(new GUI_AccountOverview(accounts[index].toString(), "", ref, accounts[index]));
	}
}
