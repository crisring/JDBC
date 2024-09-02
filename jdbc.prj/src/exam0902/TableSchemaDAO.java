package exam0902;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import day0830.DbConnection;

public class TableSchemaDAO {

	private static TableSchemaDAO tsDAO;

	private TableSchemaDAO() {
	}

	public static TableSchemaDAO getInstance() {
		if (tsDAO == null) {
			tsDAO = new TableSchemaDAO();
		}
		return tsDAO;
	}// getInstance

	public List<String> selectAllTable() throws SQLException {
		List<String> list = new ArrayList<String>();

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dbCon.getConn();
			String tableQuery = "  SELECT table_name FROM user_tables  ";

			pstmt = con.prepareStatement(tableQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString("table_name");
				list.add(tableName);
			}

		} finally {
			dbCon.dbClose(rs, pstmt, con);
		}

		return list;
	} // selectAllTable

	public List<SchemaVO> selectTableSchema(String tableName) throws SQLException {
		List<SchemaVO> list = new ArrayList<SchemaVO>();

		// 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dbCon.getConn();
			String Query = "  select * from " + tableName;

			// 쿼리문 생성 객체 얻기
			pstmt = con.prepareStatement(Query);
			// 쿼리문 수행
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			SchemaVO sVO = null;

			// String columnName, String columnLableName, String isNuallalbe, int dataSize
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				sVO = new SchemaVO(rsmd.getColumnName(i), rsmd.getColumnTypeName(i),
						rsmd.isNullable(i) == 0 ? "NULL" : "NOT NULL", rsmd.getColumnDisplaySize(i));

				list.add(sVO);

			}

		} finally {

			// 연결 끊기
			dbCon.dbClose(rs, pstmt, con);
		}
		return list;
	}// selectTableSchema

}// class