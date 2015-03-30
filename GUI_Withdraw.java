import javax.swing.*;
import java.awt.*;

public class GUI_Withdraw extends GUI_ViewPort {
	
	
	public GUI_Withdraw(String title, String error, GUI_Main ref) {
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
