import javax.swing.*;
import java.awt.*;

public class GUI_Withdraw extends GUI_ViewPort {
	
	private JPanel container;
	private JTextField amountInput;
	private JLabel lbl_prompt;
	private int amount;
	
	public GUI_Withdraw(String title, String error, GUI_Main ref) {
		super(title, error, ref);
		// TODO Auto-generated constructor stub
		
		
		container = new JPanel();
		amountInput = new JTextField(20);
		lbl_prompt = new JLabel("Enter amount to withdraw:");
		
		amountInput.setText("$0.00");
		
		container.add(lbl_prompt);
		container.add(amountInput);
		add(container);
	}

	public void buttonPress(String button){
		
		if (button.equals("SELECT")){
			validate();
		}
		else if(button.equals("BACK")){
			// do nothing
		}
		else if(button.equals("UP") ||button.equals("DOWN")){
			scrollAmount(button);
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			// do nothing
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
	
	public void validate(){
		
		
	}
}
