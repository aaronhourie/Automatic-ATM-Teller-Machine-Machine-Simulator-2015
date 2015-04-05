import javax.swing.*;
import java.awt.*;

/* This was never implemented.
 * 
 */

public class GUI_PayBill extends GUI_ViewPort{

	public GUI_PayBill(String title, String error, GUI_Main ref) {
		super(title, error, ref);
		// TODO Auto-generated constructor stub
	}

	public void buttonPress(String button){
		
		if (button.equals("SELECT")){
			// do nothing
		}
		else if(button.equals("BACK")){
			// do nothing
		}
		else if(button.equals("UP") ||button.equals("DOWN")){
			
			//do nothing
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			// do nothing
		}
		else {
			// do nothing
		}
	}
	
}
