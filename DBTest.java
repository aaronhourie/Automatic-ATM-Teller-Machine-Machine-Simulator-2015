import java.sql.*;

public class DBTest {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://192.168.2.25:3306/Cs2";

	static final String USER = "java";
	static final String PASS = "csci1101";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to db...");
			conn = DB.connect();

			System.out.println("Creating a statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Activity";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String col1 = rs.getString("details");
				System.out.println(col1);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se2) { 
			}
			try {
				if (conn != null) {
					conn.close();
				} 
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
	}	
}