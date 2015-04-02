import javax.swing.*;

import java.awt.*;
import java.util.regex.*;

public class GUI_Transfer extends GUI_ViewPort {

	private JPanel container;
	private JTextField amountInput;
	private JTextField accountInput;
	private JTextField writeTo;
	private JLabel lbl_amount;
	private JLabel lbl_account;
	private Pattern decimal;
	private Matcher match;
	
	public GUI_Transfer(String title, String error, GUI_Main ref) {
		super(title, error, ref);
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
		lbl_amount.setForeground(Color.red);
		
	}
	
	public void buttonPress(String button){
		
		if (button.equals("SELECT")){
			validate();
		}
		else if(button.equals("BACK")){
			backSpace();
		}
		else if(button.equals("UP") ||button.equals("DOWN")){
			changeFocus();
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			// do nothing
		}
		else {
			type(button);
		}
	}
	public void type(String letter){
		// types the number into the textfield
		
		Pattern decimal = Pattern.compile("\\.[0-9]{3}");
		Matcher match = decimal.matcher(writeTo.getText() + letter);
		
		if (!match.find()){
			writeTo.setText(writeTo.getText() + letter);
		}
	}
	public void backSpace(){
		// curr holds the current string info
		String curr = writeTo.getText();
		// cuts off the last letter
		writeTo.setText(curr.substring(0, curr.length() - 1));
	}
	public void validate(){
		
		// This handles validation and changing panels.
		// The value should be read from the pinInput as is.
		
		// TODO: pull from database here.
		if (!amountInput.getText().equals("1234")) {
		
			ref.changeViewPort(new GUI_UserOverview("User Overview:", "", ref));
		}
		else {
			ref.changeViewPort(new GUI_EnterPin("Enter PIN:", "Error: Invalid username or PIN.", ref));
		}
	}
	public void changeFocus(){
		// changes the focus of the "cursor" to the next field.
		if (writeTo == amountInput){
			lbl_amount.setForeground(Color.black);
			lbl_account.setForeground(Color.red); 
			writeTo = accountInput;
		}
		else {
			lbl_account.setForeground(Color.black); 
			lbl_amount.setForeground(Color.red);
			writeTo = amountInput;
		}
	}
}
