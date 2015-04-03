import javax.swing.*;
import java.awt.*;

/* This abstract class sets the standard for the children viewports. It uses a BorderLayout manager.
 * It contains all the necessary components that are common to viewPort elements.
 */

public abstract class GUI_ViewPort extends JPanel {

	private String title, error;
	private JLabel lbl_title, lbl_error;
	private JPanel filler;
	protected BorderLayout layout;
	protected GUI_Main ref;
	private final int GAP = 5;
	private final Color ERROR = Color.red;
	private final Color SUCCESS = Color.green;
	
	public GUI_ViewPort(String title, String error, GUI_Main ref) {
		super();
		
		this.title = title;
		this.error = error;
		this.ref = ref;
		// adds new layout
		layout = new BorderLayout();
		setLayout(layout);
		// sets gap
		layout.setHgap(GAP);
		layout.setVgap(GAP);
		// adds title and U_ID to layout.
		lbl_title = new JLabel(title);
		add(lbl_title, BorderLayout.NORTH);
		lbl_error = new JLabel(error);
		
		// API: putting the word "SUCCESS!" through the error causes it to change colors to green.
		if (error.equals("SUCCESS!")){
			lbl_error.setForeground(SUCCESS);
		}
		else {
			lbl_error.setForeground(ERROR);
		}
		
		add(lbl_error, BorderLayout.SOUTH);
		// adds filler to the sides.
		filler = new JPanel();
		add(filler, BorderLayout.WEST);
		add(filler, BorderLayout.EAST);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getError() {
		return error;
	}
	// This also refreshes the error label.
	public void setError(String error) {
		this.error = error;
		if (error.equals("SUCCESS!")){
			lbl_error.setForeground(SUCCESS);
		}
		else {
			lbl_error.setForeground(ERROR);
		}
		lbl_error.setText(error);
		
	}
	
	
	/* API: All children must implement buttonPress
	 * buttonPress receives a string value which contains
	 * the text from the buttons in the GUI_ButtonPad class.
	 * 
	 * API: It's important to provide an If tree for all the named buttons
	 * with only a function in it! One else if for each named button then the final
	 * else for the action to take when numbers are pressed.
	 */
	
	public abstract void buttonPress(String button);
}
