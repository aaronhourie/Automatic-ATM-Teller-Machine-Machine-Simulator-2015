import javax.swing.*;
import java.awt.*;

/* This class holds the main method, and extends the JFrame that holds all panels for the application.
 * It uses a grid layout to place the ButtonPanel and whatever instance of ViewPort left and right.
 */
class GUI_Main extends JFrame{
	
	private GUI_ViewPort mainPanel;
	private GUI_ButtonPad buttonPad;
	private User currentUser;
	private Account currentAccount;
	private final int WINDOW_WIDTH = 900;
	private final int WINDOW_HEIGHT = 400;

	public static void main(String[] args){
		
		new GUI_Main();
	}
	
	public GUI_Main(){
		
		currentUser = null;
		setTitle("ATM Simulator 2015");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// divides the window in half
		setLayout(new GridLayout(1,2));
		
		mainPanel = new GUI_EnterPin("Enter PIN:","", this);	
		buttonPad = new GUI_ButtonPad(mainPanel);
		
		add(mainPanel);
		add(buttonPad);
		setVisible(true);
		// does not allow resizing
		setResizable(false);
	}
	
	/* This method gets called typically from a viewPort object, it removes both panels, changes the 
	 * current viewport panel, and adds them back on. This keeps the buttonPanel from shifting positions.
	 */
	public void changeViewPort(GUI_ViewPort newView){
		
		// This is to ensure the panes do not swap positions.
		remove(mainPanel);
		remove(buttonPad);
		// updates reference for main panel
		mainPanel = newView;
		// adds the new pane, and button pad as is.
		add(mainPanel);
		add(buttonPad);
		// cleaning up.
		revalidate();
		repaint();
		// cascades changes to buttonPad
		buttonPad.changeViewPort(newView);
	}
	/* This method unlinks the current User object and returns the user to the
	 * Enter pin screen.
	 * Logout is cascaded into User by first calling the User.logout() method
	 */
	public void logout(){
		
		currentUser.logout();
		currentUser = null;
		changeViewPort(new GUI_Logout("", "", this));
	}
	
	// some stray getters and setters
	public User getUser(){
		return currentUser;
	}
	public void setUser(User user){
		currentUser = user;
	}
}
