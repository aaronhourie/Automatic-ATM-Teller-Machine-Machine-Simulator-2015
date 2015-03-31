import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;

public class Activity {
	private Timestamp time;
	private String account;
	private String event;

	//Create an activity, send info to the DB
	public Activity(String account, String event) {
		Date date = new Date();
		time = new Timestamp(date.getTime());
		this.event = event;
		this.account = account;
		Connection conn = DB.connect();

		try {
			String sql = "INSERT INTO Activity VALUES(?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account);
			pstmt.setString(2, event);
			pstmt.setTimestamp(3, time);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public String toString() {
		return time + "\nACCO#" + account + "\n" + event;
	}
}

/*
How will the activity class work?
- It will be generated by basic Account functions
- It will be pulled from the DB whenever you access an Account
- It will be added to the DB whenever it is generated
- It will be read to the receipt when printed

:: The Date object should be initialized from a java.sql.Timestamp
*/