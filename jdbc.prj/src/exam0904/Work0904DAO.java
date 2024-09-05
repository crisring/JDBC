package exam0904;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.vo.DbConnection;

public class Work0904DAO {

	private static Work0904DAO eDAO;

	private Work0904DAO() {

	}

	public static Work0904DAO getInstance() {
		eDAO = null;

		if (eDAO == null) {
			eDAO = new Work0904DAO();
		}
		return eDAO;
	}// getInstance

	/**
	 * 값 추가하는 작업 수행 <br>
	 * 입력 : 이름, 이메일, 전화번호, 우편번호
	 * 
	 * @throws SQLException
	 */
	public void insertWork0904(String name, String email, String phone_number, int seq) throws SQLException {

		DbConnection dbCon = DbConnection.getInstance();
		// 1.드라이버로딩

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 2.커넥션얻기
			con = dbCon.getConn();
			// 3.쿼리문 생성객체 얻기
			String insertWork0904 = "		INSERT INTO work0904 (num, name, email, phone_number, seq) VALUES ('EMP_' || LPAD(seq_work0904.NEXTVAL, 6, '0'), ?, ?, ?, ?)		";
			pstmt = con.prepareStatement(insertWork0904);

			// 4.바인드변수 값 할당
			// 입력 : 이름, 이메일, 전화번호, 우편번호
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, phone_number);
			pstmt.setInt(4, seq);

			// 5.쿼리문 수행 후 결과 얻기
			pstmt.executeUpdate();

		} finally {
			// 6.연결 끊기
			dbCon.dbClose(null, pstmt, con);
		} // end finally

	}// insertWork0904

	/**
	 * 모든 회원 조회
	 * 
	 * @return list
	 * @throws SQLException
	 */
	public List<Work0904VO> selectAllWork0904() throws SQLException {
		List<Work0904VO> list = new ArrayList<Work0904VO>();

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dbCon.getConn();
			String selectAllWork0904 = "	select * from work0904 order by num		";
			pstmt = con.prepareStatement(selectAllWork0904);
			rs = pstmt.executeQuery();

			Work0904VO wVO = null;
			// num, name, email, phone_number, seq, input_date
			while (rs.next()) {
				wVO = new Work0904VO();
				wVO.setNum(rs.getString("num"));
				wVO.setName(rs.getString("name"));
				wVO.setEmail(rs.getString("email"));
				wVO.setPhone_Number(rs.getString("phone_number"));
				wVO.setSeq(rs.getInt("seq"));
				wVO.setInput_date(rs.getDate("input_date"));

				list.add(wVO);
			}

		} finally {
			dbCon.dbClose(rs, pstmt, con);
		}

		return list;

	}// selectAllWork0904

	/**
	 * CallableStatement, Procedure 사용하여 모든 회원 조회
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Work0904VO> callAbleSelectAllWork0904() throws SQLException {
		List<Work0904VO> list = new ArrayList<Work0904VO>();

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try {
			con = dbCon.getConn();
			cstmt = con.prepareCall("{ call select_all_work0904(?,?) }");

			// 4. bind 값 할당
			cstmt.registerOutParameter(1, Types.REF_CURSOR);
			cstmt.registerOutParameter(2, Types.VARCHAR);

			// 5. 쿼리문 수행 => n개 행 검색
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			Work0904VO wVO = null;

			// 번호, 이름, 이메일, 전화번호, 우편번호, 입력일
			while (rs.next()) {
				wVO = new Work0904VO();
				wVO.setNum(rs.getString("num"));
				wVO.setName(rs.getString("name"));
				wVO.setEmail(rs.getString("email"));
				wVO.setSeq(rs.getInt("seq"));
				wVO.setInput_date(rs.getDate("input_date"));

				// 저장된 같은 이름의 객체를 관리하기 위해 list에 추가
				list.add(wVO);
			}

		} finally {
			dbCon.dbClose(rs, cstmt, con);
		}
		return list;
	}// callAbleSelectAllWork0904

}// class
