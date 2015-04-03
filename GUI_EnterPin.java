import javax.swing.*;
import java.awt.*;

public class GUI_EnterPin extends GUI_ViewPort{

	private JPanel container;
	private JTextField pinInput;
	private JTextField userInput;
	private JTextField writeTo;
	private JLabel lbl_pin;
	private JLabel lbl_user;
	private final Color FOCUS = Color.blue;
	private final Color NORMAL = Color.black;
	
	public GUI_EnterPin(String title, String error, GUI_Main ref) {
		
		super(title, error, ref);
		
		container = new JPanel();
		add(container, BorderLayout.CENTER);
		
		lbl_user = new JLabel("Please type your User Number:");
		userInput = new JTextField(20);
		lbl_pin = new JLabel("Please type your PIN:");
		pinInput = new JTextField(10);
		// adding objects
		container.add(lbl_user);
		container.add(userInput);
		container.add(lbl_pin);
		container.add(pinInput);
		// prevents editing with keyboard
		//userInput.setEditable(false);
		//pinInput.setEditable(false);
		// sets default field to write to
		writeTo = userInput;
		lbl_user.setForeground(FOCUS);
	}
	public void buttonPress(String button){
	
		if (button.equals("SELECT")){
			validate();
		}
		else if(button.equals("BACK")){
			// do nothing
		}
		else if(button.equals("UP") ||button.equals("DOWN")){
			changeFocus();
		}
		else if (button.equals("LEFT")||button.equals("RIGHT")){
			// do nothing.
		}
		else if (button.equals("DELETE")){
			backSpace();
		}
		else {
			type(button);
		}
	}
	public void type(String letter){
		// types the number into the textfield
		writeTo.setText(writeTo.getText() + letter);
	}
	public void backSpace(){
		// curr holds the current string info
		String curr = writeTo.getText();
		// cuts off the last letter
		writeTo.setText(curr.substring(0, curr.length() - 1));
	}
	public void validate(){
		
		// This handles validation and changing panels.
		// The value should be read from the pinInput as is.
		
		String userId = userInput.getText();
		String pin = pinInput.getText();
		
		User  currentUser = User.login(userId, pin);
		
		if (currentUser != null) {
		
			ref.setUser(currentUser);
			ref.changeViewPort(new GUI_UserOverview("User Overview:", "", ref));
			
		}
		else {
			ref.changeViewPort(new GUI_EnterPin("Enter PIN:", "Error: Invalid username or PIN.", ref));
		}
	}
	public void changeFocus(){
		// changes the focus of the "cursor" to the next field.
		if (writeTo == userInput){
			lbl_user.setForeground(NORMAL);
			lbl_pin.setForeground(FOCUS); 
			writeTo = pinInput;
		}
		else {
			lbl_user.setForeground(FOCUS);
			lbl_pin.setForeground(NORMAL); 
			writeTo = userInput;
		}
	}
}
