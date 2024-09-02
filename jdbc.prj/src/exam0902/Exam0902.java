package exam0902;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import day0830.DbConnection;

public class Exam0902 {

	public Exam0902() throws SQLException {

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dbCon.getConn();

			// 모든 테이블명을 가져오는 쿼리 실행
			String tableQuery = "SELECT table_name FROM user_tables";
			pstmt = con.prepareStatement(tableQuery);
			rs = pstmt.executeQuery();

			// 테이블명들을 저장할 StringBuilder
			StringBuilder tableNames = new StringBuilder();
			while (rs.next()) {
				tableNames.append(rs.getString("table_name")).append("\n");
			}

			// 테이블명을 입력받음
			String tableName = JOptionPane.showInputDialog("테이블명\n" + tableNames.toString());

			if (tableName != null && !tableName.trim().isEmpty()) {
				String query = "SELECT * FROM " + tableName;

				pstmt = con.prepareStatement(query);
				rs = pstmt.executeQuery();

				ResultSetMetaData rsmd = rs.getMetaData();
				StringBuilder metaData = new StringBuilder();
				metaData.append("컬럼명\t데이터 형\t데이터 형 크기\tNULL 허용여부").append(
						"\n----------------------------------------------------------------------------------\n");

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					metaData.append(rsmd.getColumnName(i)).append("\t").append(rsmd.getColumnTypeName(i)).append("\t")
							.append(rsmd.getPrecision(i)).append("\t")
							.append(rsmd.isNullable(i) == rsmd.isNullable(i) ? "NULL 허용" : "NULL 불가").append("\n");
				}

				JTextArea jta = new JTextArea(metaData.toString(), 10, 20);
				JOptionPane.showMessageDialog(null, jta);
			} else {
				JOptionPane.showMessageDialog(null, "유효한 테이블명을 입력하세요.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			dbCon.dbClose(rs, pstmt, con);
		}
	}

	public static void main(String[] args) {
		try {
			new Exam0902();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
