import java.sql.*;
public class DB {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://142.167.136.228:3306/Cs2";

	static final String USER = "java";
	static final String PASS = "csci1101";

	/**
	 * A safe way to establish DB connections anywheres.
	 * Remember to close the connection!
	 * @return Connection object to MySQL server
	 **/
	public static Connection connect() {
		Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Established connection with DB");
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}	
}