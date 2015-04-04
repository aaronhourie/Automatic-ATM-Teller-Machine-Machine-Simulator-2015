import javax.swing.*;

import java.awt.*;

public class GUI_Withdraw extends GUI_AccountAccess {
	
	private JPanel container, promptContainer, instructContainer, inputContainer;
	private JTextField amountInput;
	private JLabel lbl_prompt, lbl_instruct;
	private int amount;
	
	public GUI_Withdraw(String title, String error, GUI_Main ref, Account currentAccount) {
		super(title, error, ref, currentAccount);
		// TODO Auto-generated constructor stub
		
		
		container = new JPanel();
		promptContainer = new JPanel();
		instructContainer = new JPanel();
		inputContainer = new JPanel();
		amountInput = new JTextField(20);
		lbl_prompt = new JLabel("Enter amount to withdraw in multiples of $20:");
		lbl_instruct = new JLabel("Use up and down to select amount.");
		
		amount = 0;
		
		amountInput.setText("$0.00");
		// nesting layouts
		container.setLayout(new BoxLayout(container, 1));
		promptContainer.add(lbl_prompt);
		instructContainer.add(lbl_instruct);
		inputContainer.add(amountInput);
		
		container.add(promptContainer);
		container.add(instructContainer);
		container.add(inputContainer);
		
		add(container);
	}

	public void buttonPress(String button){
		
		if (button.equals("SELECT")){
			validate();
		}
		else if(button.equals("BACK")){
			back();
		}
		else if(button.equals("UP") ||button.equals("DOWN")){
			scrollAmount(button);
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			// do nothing
		}
		else if (button.equals("DELETE")){
			clear();
		}
		else {
			// do nothing
		}
	}
	
	public void scrollAmount(String direction){
		
		if (direction.equals("UP")){
			//TODO: tie in to withdraw limit
			
			amount += 20;
		}
		else {
			
			if (amount > 0){
				
				amount -= 20;
			}
		}
		
		amountInput.setText("$" + amount + ".00");
	}
	public void clear(){
		
		amount = 0;
		amountInput.setText("$" + amount + ".00");
	}
	
	public void validate(){
		
		double withdrawAmount = (double)amount;
		
		if (currentAccount instanceof ChequingAccount){
			
			ChequingAccount withdrawFrom = (ChequingAccount)currentAccount;
			
			if (withdrawFrom.withdraw(withdrawAmount)){
				ref.changeViewPort(new GUI_Withdraw("Withdraw from"  + currentAccount + ":", "SUCCESS!", ref, currentAccount));	
			}
			else {
				// getLastEvent returns the appropriate error message to be displayed.
				ref.changeViewPort(new GUI_Withdraw("Withdraw from"  + currentAccount + ":", currentAccount.getLastEvent(), ref, currentAccount));	
			}
		}
		else if(currentAccount instanceof CreditAccount) {
			
			CreditAccount withdrawFrom = (CreditAccount)currentAccount;
			
			if (withdrawFrom.withdraw(withdrawAmount)){
				ref.changeViewPort(new GUI_Withdraw("Withdraw from"  + currentAccount + ":", "SUCCESS!", ref, currentAccount));	
			}
			else {
				// getLastEvent returns the appropriate error message to be displayed.
				ref.changeViewPort(new GUI_Withdraw("Withdraw from"  + currentAccount + ":", currentAccount.getLastEvent(), ref, currentAccount));	
			}
		}
		
	}
	public void back(){
		
		// return to account overview
		ref.changeViewPort(new GUI_AccountOverview("Account Overview:", "", ref, currentAccount));
	}
}
