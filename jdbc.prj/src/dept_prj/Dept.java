package dept_prj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Dept : deptno, dname, loc
 */
public class Dept {

	public String selectDEPT(String loc, String dname) throws SQLException {

		String data = null;

		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 연결 중");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 2. 커넥션 얻기
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// 3. 쿼리문 생성 객체 statement 얻기
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String id = "scott";
			String passWord = "tiger";
			con = DriverManager.getConnection(url, id, passWord);
			System.out.println("드라이버 연결 성공");

			stmt = con.createStatement();

			// 4. 쿼리문 사용

			StringBuilder searchDept = new StringBuilder();

			// 지역만 넘어온 경우 지역만 검색 결과 리턴
			if (loc != null && dname.isEmpty()) {
				searchDept = new StringBuilder();
				searchDept.append("select deptno,dname,loc from dept where loc=").append("'").append(loc).append("'");
				System.out.println(searchDept);
				rs = stmt.executeQuery(searchDept.toString());

				while (rs.next()) {
					String loc1 = rs.getString("loc");
					data = loc1;
				} // end while
			} else if (loc.isEmpty() && dname != null) {

				searchDept.append("select dname from dept where dname=").append("'").append(dname).append("'");
				rs = stmt.executeQuery(searchDept.toString());

				while (rs.next()) {
					String loc1 = rs.getString(1);
					data = loc1;
				} // end while
			} else { // 지역, 부서이름 둘 다 넘어온 경우 둘 다 리턴
				searchDept = new StringBuilder();
				searchDept.append("select loc, dname from dept where dname=").append("'").append(dname)
						.append("' and loc='").append(loc).append("'");

				rs = stmt.executeQuery(searchDept.toString());

				while (rs.next()) {
					String loc1 = rs.getString(1);
					String dname1 = rs.getString("dname");
					data = loc1 + "\t" + dname1;
				} // end while
			} // end if
			return data;
		} finally {
			// 5. 연결 끊기
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			if (con != null) {
				con.close();
			}

		} // try~finallyF

	}// search
}// class