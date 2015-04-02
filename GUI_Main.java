import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
class GUI_Main extends JFrame{
	
	private GUI_ViewPort mainPanel;
	private GUI_ButtonPad buttonPad;
	//private User currentUser = new User();
	private final int WINDOW_WIDTH = 900;
	private final int WINDOW_HEIGHT = 400;
	
	
	public static void main(String[] args){
		
		new GUI_Main();
	}
	
	public GUI_Main(){
		
		setTitle("ATM Simulator 2015");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// divides the window in half
		setLayout(new GridLayout(1,2));
		buildPanels();
		add(mainPanel);
		add(buttonPad);
		setVisible(true);
		// does not allow resizing
		setResizable(false);
	}
	private void buildPanels(){
		
		mainPanel = new GUI_EnterPin("Enter PIN:","", this);
		mainPanel.setSize(WINDOW_WIDTH/2, WINDOW_HEIGHT);
		
			
		buttonPad = new GUI_ButtonPad(mainPanel);
		buttonPad.setSize(WINDOW_WIDTH/2, WINDOW_HEIGHT);
	}
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
}
