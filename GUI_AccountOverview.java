import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI_AccountOverview extends GUI_AccountAccess{

	private JPanel container, buttonContainer, infoContainer, promptContainer;
	private JTextArea display;
	private JScrollPane activity;
	private ButtonGroup actions;
	private JLabel lbl_activity, lbl_info;
	private JRadioButton[] options;
	private int selected;
	
	public GUI_AccountOverview(String title, String error, GUI_Main ref, Account currentAccount) {
		super(title, error, ref, currentAccount);
		
		container = new JPanel();
		buttonContainer = new JPanel();
		infoContainer = new JPanel();
		promptContainer = new JPanel();
		display = new JTextArea(15, 30);
		activity = new JScrollPane(display);
		lbl_activity = new JLabel("Activity:");
		lbl_info = new JLabel(currentAccount.getAccountType() + " account");
		actions = new ButtonGroup();
		
		// setting recent activity
		display.setText(currentAccount.getRecentActivity());
		
		// adding nested containers
		infoContainer.add(lbl_info);
		promptContainer.add(lbl_activity);
		
		if (!currentAccount.getAccountType().equals("Savings")){
			// adds all options for chequing and credit accounts
			options = new JRadioButton[3];
			options[0] = new JRadioButton("Withdraw");
			options[1] = new JRadioButton("Transfer");
			options[2] = new JRadioButton("Deposit");
		}
		else {
			
			options = new JRadioButton[2];
			options[0] = new JRadioButton("Transfer");
			options[1] = new JRadioButton("Deposit");
		}
		// sets first option as selected.
		options[0].setSelected(true);
		
		// adds radios to necessary groups
		for (int i=0; i < options.length; i++){
			
			actions.add(options[i]);
			buttonContainer.add(options[i]);
		}
		
		promptContainer.add(activity);
		// nesting layouts
		container.setLayout(new BoxLayout(container, 1));
		container.add(buttonContainer);
		container.add(infoContainer);
		container.add(promptContainer);
		
		add(container);
	}

	public void buttonPress(String button){
	
		if (button.equals("SELECT")){
			select();
		}
		else if(button.equals("BACK")){
			back();
		}
		else if(button.equals("UP") ||button.equals("DOWN")){
			// do nothing
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			changeButton(button);
		}
		else if (button.equals("DELETE")){
			// do nothing
		}
		else {
			// do nothing
		}
	}
	
	public void changeButton(String direction){
		
		if (direction.equals("RIGHT")){
			// if it is not at the end of the list
			if (selected != options.length -1){
				// move cursor
				selected++;
			}
			else {
				// reset cursor
				selected = 0;
			}
		}	
		else {
			// if it is not at the start of the list
			if (selected != 0){
				// move cursor
				selected--;
			}
			else {
				// reset cursor
				selected = options.length -1;
			}
		}
		// go through each radio button and deselect currently selected
		for (int i=0; i < options.length; i++){
			
			if (i != selected){
				options[i].setSelected(false);
			}
			else {
				// select the current selection
				options[i].setSelected(true);
			}
		}
		
	}
	public void select(){
		
		if (options[selected].getText().equals("Withdraw")){
			ref.changeViewPort(new GUI_Withdraw("Withdraw from "  + currentAccount + ":", "", ref, currentAccount));
		}
		else if (options[selected].getText().equals("Transfer")){
			ref.changeViewPort(new GUI_Transfer("Transfer from " + currentAccount + ":", "", ref, currentAccount));
		}
		else {
			ref.changeViewPort(new GUI_Deposit("Deposit to " + currentAccount + ":", "", ref, currentAccount));	
		}
	}
	public void back(){
		
		// return to user overview
		ref.changeViewPort(new GUI_UserOverview("User Overview:", "", ref));
	}
}
