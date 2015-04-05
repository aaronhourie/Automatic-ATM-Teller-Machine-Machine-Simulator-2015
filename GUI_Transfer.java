import javax.swing.*;

import java.awt.*;
import java.util.regex.*;
/* This class handles transfers between accounts.
 */
public class GUI_Transfer extends GUI_AccountAccess {

	private JPanel container;
	private JTextField amountInput;
	private JTextField accountInput;
	private JTextField writeTo;
	private JLabel lbl_amount;
	private JLabel lbl_account;
	private final Color FOCUS = Color.blue;
	private final Color NORMAL = Color.black;
	
	public GUI_Transfer(String title, String error, GUI_Main ref, Account currentAccount) {
		super(title, error, ref, currentAccount);
		// TODO Auto-generated constructor stub
		
		container = new JPanel();
		add(container, BorderLayout.CENTER);
		
		
		lbl_amount = new JLabel("Please type an amount:");
		amountInput = new JTextField(20);
		lbl_account = new JLabel("Please type account to transfer to:");
		accountInput = new JTextField(10);
		// adding objects
		container.add(lbl_amount);
		container.add(amountInput);
		container.add(lbl_account);
		container.add(accountInput);
		// prevents editing with keyboard
		amountInput.setEditable(false);
		accountInput.setEditable(false);
		// sets default field to write to
		writeTo = amountInput;
		lbl_amount.setForeground(FOCUS);
		
	}
	
	/* Implementing the API.
	 */
	public void buttonPress(String button){
		
		if (button.equals("SELECT")){
			validateInput();
		}
		else if(button.equals("BACK")){
			back();
		}
		else if(button.equals("UP") ||button.equals("DOWN")){
			changeFocus();
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			// do nothing
		}
		else if (button.equals("DELETE")){
			backSpace();
		}
		else {
			type(button);
		}
	}
	
	/* This method handles typing and uses a regular expression to prevent typing 
	 * invalid values.
	 */
	public void type(String letter){
		// types the number into the textfield
		
		if (writeTo == amountInput){
			Pattern decimal = Pattern.compile("([0-9]{9}(\\.|))|(\\.[0-9]{3})|(\\.{2})|(\\.[0-9]*\\.)|(^\\.)");
			Matcher match = decimal.matcher(writeTo.getText() + letter);
			
			if (!match.find()){
				writeTo.setText(writeTo.getText() + letter);
				setError("");
			} 
			else {
				setError("Error: Invalid input!");
			}
		}
		else {
			
			writeTo.setText(writeTo.getText() + letter);
		}
	}
	
	/* Deletes the last letter in the currently focused field
	 */
	public void backSpace(){
		// curr holds the current string info
		String curr = writeTo.getText();
		// cuts off the last letter
		if (curr.length() > 0){
			writeTo.setText(curr.substring(0, curr.length() - 1));
		}
	}
	
	/* This handles validation and changing panels.
	 * Hooks into Account.transfer() for validation.
	 * Returns user to transfer on success or failure with according messages.
	 */
	public void validateInput(){
		
		double amount = Double.parseDouble(amountInput.getText());
		String account = accountInput.getText();
		
		if (currentAccount.transfer(account, amount)){
			ref.changeViewPort(new GUI_Transfer("Transfer from " + currentAccount + ":", "SUCCESS!", ref, currentAccount));	
		}
		else {
			// getLastEvent returns the appropriate error message to be displayed
			ref.changeViewPort(new GUI_Transfer("Transfer from "  + currentAccount + ":", currentAccount.getLastEvent(), ref, currentAccount));
		}
	}
	
	/* Changes the currently selected textfield for reading and writing.
	 */
	public void changeFocus(){
		// changes the focus of the "cursor" to the next field.
		if (writeTo == amountInput){
			lbl_amount.setForeground(NORMAL);
			lbl_account.setForeground(FOCUS); 
			writeTo = accountInput;
		}
		else {
			lbl_account.setForeground(NORMAL); 
			lbl_amount.setForeground(FOCUS);
			writeTo = amountInput;
		}
	}
	/* Returns user to account overview
	 */
	public void back(){
		
		// return to account overview
		ref.changeViewPort(new GUI_AccountOverview("Account Overview:", "", ref, currentAccount));
	}
}
