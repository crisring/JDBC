package day0830;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {

	// Volatile keyword ensures visibility of changes to variables across threads
	private static volatile DbConnection dbc;

	// Private constructor to prevent instantiation
	private DbConnection() {
	}

	// Thread-safe Singleton instance retrieval
	public static DbConnection getInstance() {
		if (dbc == null) {
			synchronized (DbConnection.class) {
				if (dbc == null) {
					dbc = new DbConnection();
				}
			}
		}
		return dbc;
	}

	public Connection getConn() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Driver loading...");

			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "scott";
			String passWord = "tiger";

			con = DriverManager.getConnection(url, id, passWord);
			System.out.println("Driver loaded successfully!!");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver not found.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Connection failed.");
			e.printStackTrace();
		}
		return con;
	}

	// OverLoading
	public Connection getConn(String ip, String id, String passWord) {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Driver loading...");

			String url = "jdbc:oracle:thin:@" + ip + ":1521:orcl";

			con = DriverManager.getConnection(url, id, passWord);
			System.out.println("Driver loaded successfully!!");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver not found.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Connection failed.");
			e.printStackTrace();
		}
		return con;
	}

	// Close resources method
	public void dbClose(ResultSet rs, PreparedStatement pstmt, Connection con) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
