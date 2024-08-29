package day0829;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 드라이버를 로딩하여, DB Connection을 얻는 일
 */
public class TestConnection {

	public TestConnection() throws SQLException {

		// 1. driver loading
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Access Driver Loading");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 2. Connection 얻기
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String id = "scott";
		String pass = "tiger";

		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection(url, id, pass);
			System.out.println("Driver loading successful!!! " + con);

			// connection은 autocommit된다.
			// con.setAutoCommit(false);

			// 3. 쿼리문 생성 객체 (Statement) 얻기
			stmt = con.createStatement();
			int deptno = 80;
			String dname = "SI";
			String loc = "서울";

			// 4. 쿼리문 수행 후 결과 얻기 -> 값과 쿼리문이 합쳐진다.

//			String insertCpDept = "insert into cp_dept(deptno, dname, loc) values(" + deptno + ",'" + dname + "','"
//					+ loc + "')";

			StringBuilder insertCpDept = new StringBuilder();
			insertCpDept.append("insert into cp_dept(deptno, dname, loc) values(").append(deptno).append(",'")
					.append(dname).append("','").append(loc).append("')");

			int cnt = stmt.executeUpdate(insertCpDept.toString());
			System.out.println(insertCpDept);
			System.out.println(cnt + "건 추가되었음.");

			if (cnt == 1) { // 별도로 tranzection을 처리할 때 사용
				con.commit();
			}
		} finally {
			// 5. 뒤에서 부터 연결 끊기
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} // end finally

	}// TestConnection

	public static void main(String[] args) {

		try {
			new TestConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// main

}// class
