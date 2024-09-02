package day0829;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementTest {

	public void preparedInsert(int num, String item) throws SQLException {

		// 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Driver 로딩 중");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 커넥션 연결
		Connection con = null;

		// 쿼리 생성 객체 연결
		PreparedStatement pstmt = null;

		try {
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "scott";
			String passWord = "tiger";

			con = DriverManager.getConnection(url, id, passWord);
			System.out.println("연결 완료!");

			String sql = "insert into SEQ_INSERT(num, item) values(?,?)";

			pstmt.setInt(1, num);
			pstmt.setString(2, item);
			pstmt = con.prepareStatement(sql);

			pstmt.execute();

		} finally {

			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}

		}

	}

	public static void main(String[] args) {

		try {
			new PreparedStatementTest().preparedInsert(11, "로지텍 슈퍼노바");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;

	}

}
