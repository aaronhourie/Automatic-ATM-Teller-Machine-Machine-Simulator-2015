import javax.swing.*;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* This panel deals with deposits and has a reference to the current account being operated on.
 */

public class GUI_Deposit extends GUI_AccountAccess {
	
	private JPanel container;
	private JTextField amountInput;
	private JLabel lbl_amount;
	private Pattern decimal;
	private Matcher match;

	public GUI_Deposit(String title, String error, GUI_Main ref,  Account currentAccount) {
		super(title, error, ref, currentAccount);
		// TODO Auto-generated constructor stub
		
		container = new JPanel();
		add(container, BorderLayout.CENTER);
		
		
		lbl_amount = new JLabel("Please type an amount:");
		amountInput = new JTextField(20);
		// adding objects
		container.add(lbl_amount);
		container.add(amountInput);
		// prevents editing with keyboard
		amountInput.setEditable(false);
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
		// pattern that matches a lot of invalid cases.
		Pattern decimal = Pattern.compile("([0-9]{9}(\\.|))|(\\.[0-9]{3})|(\\.{2})|(\\.[0-9]*\\.)|(^\\.)");
		Matcher match = decimal.matcher(amountInput.getText() + letter);
		
		if (!match.find()){
			// if no error is found, start typing.
			amountInput.setText(amountInput.getText() + letter);
			// reset the error message.
			setError("");
		}
		else {
			// set the error message
			setError("Error: Invalid input!");
		}
	}
	/* Deletes the last letter in the currently focused field
	 */
	public void backSpace(){
		// curr holds the current string info
		String curr = amountInput.getText();
		// cuts off the last letter if string is not empty
		if (curr.length() > 0){
			amountInput.setText(curr.substring(0, curr.length() - 1));
		}
	}
	
	/* This method hooks into the Account class's deposit validation
	 * functionality. 
	 */
	public void validateInput(){
		
		// grabs deposit amount from text field
		Double deposit = Double.parseDouble(amountInput.getText());
		
		// attempts to deposit
		if (currentAccount.deposit(deposit)){
			ref.changeViewPort(new GUI_Deposit("Deposit to"  + currentAccount + ":", "SUCCESS!", ref, currentAccount));	
		}
		else {
			// getLastEvent returns the appropriate error message to be displayed
			ref.changeViewPort(new GUI_Deposit("Deposit to"  + currentAccount + ":", currentAccount.getLastEvent(), ref, currentAccount));
		}
	}
	
	/* Returns user to account overview
	 */
	public void back(){
		
		// return to account overview
		ref.changeViewPort(new GUI_AccountOverview("Account Overview:", "", ref, currentAccount));
		
	}

}
