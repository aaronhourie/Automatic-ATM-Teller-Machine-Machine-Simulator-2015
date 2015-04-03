import javax.swing.*;

import java.awt.*;

public abstract class GUI_ViewPort extends JPanel {

	private String title, mode, error;
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
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public abstract void buttonPress(String button);
}
