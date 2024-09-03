package crm.prj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Connection 객체를 획득하고, 사용한 Connection 객체를 종료(close)하는 Utility 클래스를 작성한다. <br>
 * MemberDAO 클래스를 작성하고 회원등록, 회원 수정, 회원 삭제, 회원 상세 조회, 회원 목록 검색 기능의 메소드를 각각 구현한다.
 * 
 */

/**
 * 
 */
public class MemberDAO {

	/**
	 * DB연결객체들의 연결을 끊는 일.
	 * 
	 * @param rs
	 * @param pstmt
	 * @param con
	 * @throws SQLException
	 */
	public void Utility(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException {
		if (rs != null) {
			rs.close();
		} // end if
		if (pstmt != null) {
			pstmt.close();
		} // end if
		if (con != null) {
			con.close();
		} // end if
	}// Utility

	/**
	 * 회원등록 - Create
	 */
	public void CreateMember() {

	}

	/**
	 * 회원 수정 - Update
	 */
	public void UpdateMember() {
	}

	/**
	 * 회원 삭제
	 */
	public void DeleteMember() {

	}

	/**
	 * 회원 상세 조회
	 */
	public void SelectMemeber() {

	}

	/**
	 * 회원 목록 검색
	 */
	public void MemberCatalog() {

	}

}
