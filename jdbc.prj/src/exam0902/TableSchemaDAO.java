package exam0902;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exam0902.SchemaVO;
import kr.co.sist.vo.DbConnection;

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

			String tableName = "";
			while (rs.next()) {
				tableName = rs.getString("table_name");
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
			StringBuilder selectTableSchema = new StringBuilder();

			selectTableSchema
					.append("		select column_name, data_type, nvl(data_precision,data_length) data_length,		")
					.append("		decode(nullable,'Y',' ','not null') nullable		")
					.append("		from user_tab_cols		").append("		where table_name=?	");

			// 쿼리문 생성 객체 얻기
			pstmt = con.prepareStatement(selectTableSchema.toString());

			tableName = tableName.toUpperCase();
			pstmt.setString(1, tableName);

			// 쿼리문 수행
			rs = pstmt.executeQuery();

			SchemaVO sVO = null;

			// String columnName, String columnLableName, String isNuallalbe, int dataSize
			while (rs.next()) {
				sVO = new SchemaVO(rs.getString("column_name"), rs.getString("data_type"), rs.getString("nullable"),
						rs.getInt("data_length"));

				list.add(sVO);
			}

		} finally {

			// 연결 끊기
			dbCon.dbClose(rs, pstmt, con);
		}
		return list;
	}// selectTableSchema

}// class