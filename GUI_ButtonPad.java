import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class GUI_ButtonPad extends JPanel{

	private GUI_ViewPort viewPort;
	private GridLayout layout;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private final int NUM_BUTTONS = 18;
	private final int NUM_DIGITS = 10;
	private final int HGAP = 10;
	private final int VGAP = 3;
	
	public GUI_ButtonPad(GUI_ViewPort viewPort) {
		super();
		
		this.viewPort = viewPort;
		
		// lays out a grid 3x the max number of buttons over 3
		layout = new GridLayout(NUM_BUTTONS/3, 3);
		layout.setHgap(HGAP);
		layout.setVgap(VGAP);
		setLayout(layout);
		
		// creates number buttons
		for (int i=1; i < NUM_DIGITS; i++){
			
			buttons.add(new JButton(""+i));
		}
		// creates control buttons
		buttons.add(new JButton("*"));
		buttons.add(new JButton("0"));
		buttons.add(new JButton("#"));
		buttons.add(new JButton("SELECT"));
		buttons.add(new JButton("UP"));
		buttons.add(new JButton("BACK"));
		buttons.add(new JButton("LEFT"));
		buttons.add(new JButton("DOWN"));
		buttons.add(new JButton("RIGHT"));
		
		for (int i=0; i < NUM_BUTTONS; i++){
			
			buttons.get(i).addActionListener(new ButtonListener());
			add(buttons.get(i));	
		}
	}
	
	public void changeViewPort(GUI_ViewPort viewPort){
	
		this.viewPort = viewPort;
	}
	public class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			viewPort.buttonPress(e.getActionCommand());
		}
	}
}
