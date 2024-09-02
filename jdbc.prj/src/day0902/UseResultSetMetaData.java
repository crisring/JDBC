package day0902;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import day0830.DbConnection;

public class UseResultSetMetaData {

	public UseResultSetMetaData(String tableName) throws SQLException {

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dbCon.getConn();

			// table명, 컬럼명은 bind변수로 사용할 수 없다.
			String query = "select * from " + tableName;
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			String columnNameString = "";
			String dataType = "";
			int dateSize = 0;
			boolean isNullable = false;

			for (int i = 1; i < rsmd.getColumnCount(); i++) {

				int dataSize = rsmd.getPrecision(i);

				System.out.println(tableName + "의 릴레이션 스키마정보");
				System.out.println("컬럼수 : " + rsmd.getColumnCount());
				System.out.println("컬럼의 이름 얻기 : " + rsmd.getColumnName(i));
				System.out.println("컬럼의 데이터 형 명 얻기 : " + rsmd.getColumnTypeName(i));
				System.out.println("컬럼의 데이터형 크기 : " + dataSize);
				System.out.println(" NULL 허용 여부 : " + (rsmd.isNullable(i) == 0 ? "Not Null" : "null"));

				StringBuilder printDataSize = null;
				printDataSize.append(String.valueOf(dataSize));

				if (dataSize != 0) {
					printDataSize = new StringBuilder("(").append(dataSize).append(")");
				}

			}

		} finally {
			dbCon.dbClose(rs, pstmt, con);
		}

	}// SQLException

	public static void main(String[] args) {

		try {
			String tableName = "EMP";
			new UseResultSetMetaData(tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
