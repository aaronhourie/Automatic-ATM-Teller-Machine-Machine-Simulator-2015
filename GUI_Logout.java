import javax.swing.*;

import java.awt.*;

/* Displays logout message
 * 
 */

public class GUI_Logout extends GUI_ViewPort{

	private JLabel display;
	private JPanel container;
	
	public GUI_Logout(String title, String error, GUI_Main ref) {
		super(title, error, ref);

		container = new JPanel();
		container.setLayout(new BoxLayout(container, 1));
		
		display = new JLabel("  Logged out! A receipt has been printed. Press any key");
		container.add(display);
		
		add(container);
	}

	public void buttonPress(String button){
		// returns to login screen on any button being pressed.
		ref.changeViewPort(new GUI_EnterPin("Enter PIN:", "", ref));
	}
	
}
