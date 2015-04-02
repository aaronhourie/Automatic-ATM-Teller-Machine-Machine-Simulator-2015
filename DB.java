import java.sql.*;
public class DB {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://142.167.136.228:3306/Cs2";

	static final String USER = "java";
	static final String PASS = "csci1101";

	public static Connection connect() {
		Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to db...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}	
}