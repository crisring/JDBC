package kr.co.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DB와 연결된 Connection 객체를 반환하고,
 * DB관련객체들의 연결을 끊는 일. 
 */
public class DbConnection {
	private static DbConnection dbCon;
	
	private DbConnection() {
	}//DbConnection
	
	public static DbConnection getInstance() {
		if( dbCon == null) {
			dbCon=new DbConnection();
		}//end if
		return dbCon;
	}//getInstance
	
	/**
	 * Connection을 반환하는 일
	 * @return
	 * @throws SQLException
	 */
	public Connection getConn()throws SQLException{
		Connection con=null;
		
		//1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
		
		//2. 커넥션 얻기
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String id="scott";
		String pass="tiger";
		
		con=DriverManager.getConnection(url, id, pass);
		
		return con;		
	}//getConn
	
	/**
	 * DB연결객체들의 연결을 끊는 일.
	 * @param rs
	 * @param stmt
	 * @param con
	 * @throws SQLException
	 */
	public void dbClose(ResultSet rs, Statement stmt, Connection con) throws SQLException{
		if( rs != null ) { rs.close(); }//end if
		if( stmt != null ) { stmt.close(); }//end if
		if( con != null ) { con.close(); }//end if
	}//dbClose
	
}//class





