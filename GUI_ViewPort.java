import javax.swing.*;
import java.awt.*;

public abstract class GUI_ViewPort extends JPanel {

	private String title, mode, u_id;
	private JLabel lbl_title;
	private JLabel lbl_uid;
	private JPanel filler;
	protected BorderLayout layout;
	protected GUI_Main ref;
	private final int GAP = 5;
	
	public GUI_ViewPort(String title, String error, GUI_Main ref) {
		super();
		
		this.title = title;
		this.u_id = error;
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
		lbl_uid = new JLabel(error);
		add(lbl_uid, BorderLayout.SOUTH);
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
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public abstract void buttonPress(String button);
}
