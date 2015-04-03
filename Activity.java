import java.sql.*;

public class Activity {
	private Timestamp time;
	private String account;
	private String event;
	private boolean success;

	public Activity(String account, String event, Timestamp time) {
		this.time = time;
		this.account = account;
		if (event == null) {
			this.event = "There was an error and this transaction was halted";
			success = false;
		} else {
			this.event = event;
			success = true;
		}
	}

	public String getEvent() {
		return event;
	}

	public boolean wasSuccessful() {
		return success;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String toString() {
		return time + "\nACCO#" + account + "\n" + event;
	}
}