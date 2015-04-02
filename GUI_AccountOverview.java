import javax.swing.*;
import java.awt.*;

public class GUI_AccountOverview extends GUI_ViewPort{

	private JPanel container;
	private JTextArea display;
	private JScrollPane activity;
	private ButtonGroup actions;
	private JRadioButton withdraw;
	private JRadioButton deposit;
	private JRadioButton transfer; 
	private JLabel lbl_activity;
	private JRadioButton[] options;
	private int selected;
	
	public GUI_AccountOverview(String title, String u_id, GUI_Main ref) {
		super(title, u_id, ref);
		
		container = new JPanel();
		display = new JTextArea(15, 30);
		activity = new JScrollPane(display);
		actions = new ButtonGroup();
		withdraw = new JRadioButton("Withdraw");
		deposit = new JRadioButton("Deposit");
		transfer =  new JRadioButton("Transfer");
		// sorry ;)
		lbl_activity = new JLabel("Activity:                                                                                                ");
		options = new JRadioButton[3];
		selected = 0;
		
		actions.add(withdraw);
		actions.add(deposit);
		actions.add(transfer);
	
		options[0] = withdraw;
		options[1] = deposit;
		options[2] = transfer;
		
		container.add(withdraw);
		container.add(deposit);
		container.add(transfer);
		container.add(lbl_activity);
		container.add(activity);
		
		withdraw.setSelected(true);
		
		add(container);
	}

	public void buttonPress(String button){
	
		if (button.equals("SELECT")){
			// do nothing
		}
		else if(button.equals("BACK")){
			// do nothing
		}
		else if(button.equals("UP") ||button.equals("DOWN")){
			// do nothing
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			changeButton(button);
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
	
}
