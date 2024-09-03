package day0902sub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.vo.DbConnection;
import kr.co.sist.vo.SchemaVO;

public class TableSchemaDAO {
	private static TableSchemaDAO tsDAO;

	private TableSchemaDAO() {
	}// PreparedStatementDAO

	public static TableSchemaDAO getInstance() {
		if (tsDAO == null) {
			tsDAO = new TableSchemaDAO();
		} // end if
		return tsDAO;
	}// getInstance

	// Singleton pattern

	public List<String> selectAllTable() throws SQLException {
		List<String> list = new ArrayList<String>();

		// 1. 드라이브 연결
		DbConnection dbCon = DbConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 2. 커넥션 얻기
		try {
			// 3. 쿼리문 생성 객체 얻기
			con = dbCon.getConn();
			StringBuilder getTab = new StringBuilder();
			getTab.append("select * from tab");

			pstmt = con.prepareStatement(getTab.toString());
			// 4. 바인드 객체 값 할당
			// 5. 쿼리문 수행 후 결과
			rs = pstmt.executeQuery(); // 쿼리문 수행
			while (rs.next()) {
				list.add(rs.getString("tname")); // 문자열에 모든 테이블명
				list.add("\n");
			} // end while

		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(rs, pstmt, con);
		} // end finally

		return list;
	}// selectAllTable

	public List<SchemaVO> selectTableSchema(String table) throws SQLException {
		List<SchemaVO> list = new ArrayList<SchemaVO>();

		// 1. 드라이브 연결
		DbConnection dbCon = DbConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 2. 커넥션 얻기
		try {
			// 3. 쿼리문 생성 객체 얻기
			con = dbCon.getConn();

			StringBuilder selectTableSchema = new StringBuilder();

			selectTableSchema
					.append("		select column_name, data_type, nvl(data_precision,data_length) data_length,		")
					.append("		decode(nullable,'Y',' ','not null') nullable		")
					.append("		from user_tab_cols		").append("		where table_name='EMP'	");

			// 테이블의 컬럼명, 데이터형, 크기, null 허용 여부 저장할 문자열
			pstmt = con.prepareStatement(selectTableSchema.toString());
			rs = pstmt.executeQuery(); // 쿼리문 수행

			SchemaVO sVO = null;
			while (rs.next()) {
				sVO = new SchemaVO(rs.getString("column_name"), rs.getString("data_type"), rs.getString("nullable"),
						rs.getInt("data_length"));

				list.add(sVO);
			}

		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(rs, pstmt, con);
		} // end finally

		return list;
	}// selectTableSchema
}// class
