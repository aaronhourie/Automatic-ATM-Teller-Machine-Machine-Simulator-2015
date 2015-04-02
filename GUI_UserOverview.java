import javax.swing.*;
import java.awt.*;

public class GUI_UserOverview extends GUI_ViewPort{

	private JPanel container;
	private JList accountList;
	private JLabel lbl_account;
	//TODO: we need to pull these from user.
	private String[] data = {"Test","Test2","Test3"};
	private int index;
	
	public GUI_UserOverview(String title, String u_id, GUI_Main ref) {
		super(title, u_id, ref);
		
		
		index = 0;
		
		container = new JPanel();
		container.setLayout(new GridLayout(1, 2));
		accountList = new JList(data);
		// set focus to the first object
		accountList.setSelectedIndex(index);
		lbl_account = new JLabel("Accounts:");
		// adds items
		container.add(lbl_account);
		container.add(accountList);
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
			// scrolls account list
			scroll(button);
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			// do nothing
		}
		else {
			// do nothing
		}
	}
	public void scroll(String direction){
		// either adds or subtracts to the variable index, then
		// sets the current index to focus on as index.
		if (direction.equals("UP") && index != 0){
			
			index --;
		}
		else if (index < data.length) {
			
			index ++;
		}
		accountList.setSelectedIndex(index);
	}
}
