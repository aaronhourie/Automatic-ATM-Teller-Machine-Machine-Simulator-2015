import javax.swing.*;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public void buttonPress(String button){
		
		if (button.equals("SELECT")){
			validate();
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
	public void type(String letter){
		// types the number into the textfield
		
		Pattern decimal = Pattern.compile("([0-9]{9}(\\.|))|(\\.[0-9]{3})");
		Matcher match = decimal.matcher(amountInput.getText() + letter);
		
		if (!match.find()){
			amountInput.setText(amountInput.getText() + letter);
		}
	}
	public void backSpace(){
		// curr holds the current string info
		String curr = amountInput.getText();
		// cuts off the last letter
		amountInput.setText(curr.substring(0, curr.length() - 1));
	}
	public void validate(){
		
		// This handles validation and changing panels.
		// The value should be read from the pinInput as is.
		
		Double deposit = Double.parseDouble(amountInput.getText());
		if (currentAccount.deposit(deposit)){
			ref.changeViewPort(new GUI_Deposit("Deposit to"  + currentAccount + ":", "SUCCESS!", ref, currentAccount));	
		}
		else {
			// getLastEvent returns the appropriate error message to be displayed
			ref.changeViewPort(new GUI_Deposit("Deposit to"  + currentAccount + ":", currentAccount.getLastEvent(), ref, currentAccount));
		}
	}

	public void back(){
		
		// return to account overview
		ref.changeViewPort(new GUI_AccountOverview("Account Overview:", "", ref, currentAccount));
		
	}

}
