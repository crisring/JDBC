package crm.prj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.co.sist.vo.DbConnection;

/**
 * Connection 객체를 획득 <br>
 * MemberDAO 클래스를 작성하고 회원등록, 회원 수정, 회원 삭제, 회원 상세 조회, 회원 목록 검색 기능의 메소드를 각각 구현한다.
 * 
 */

public class MemberDAO {

	/**
	 * 회원등록 - insert <br>
	 * 아이디, 이름, 비번을 입력받아 추가
	 * 
	 * @throws SQLException
	 */
	public void insertMember(String member_id, String name, String phone_number) throws SQLException {

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dbCon.getConn();

			String insertMember = "insert into member(MEMBER_ID, NAME, PHONE_NUMBER) values(?,?,?)";

			pstmt = con.prepareStatement(insertMember);

			pstmt.setString(1, member_id);
			pstmt.setString(2, name);
			pstmt.setString(3, phone_number);
			pstmt.executeUpdate();

		} finally {

			dbCon.dbClose(null, pstmt, con);
		}

	}// insertMember

	/**
	 * 회원 수정 - Update
	 * 
	 * @throws SQLException
	 */
	public void updateMember() throws SQLException {

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dbCon.getConn();

			String updateMember = "	update member set name = ?, phone_number = ? where MEMBER_ID";

			pstmt = con.prepareStatement(updateMember);

			pstmt.executeUpdate();

		} finally {

			dbCon.dbClose(null, pstmt, con);
		}

	}// updateMember

	/**
	 * 회원 삭제
	 */
	public void deleteMember() {

	}// deleteMember

	/**
	 * 회원 상세 조회
	 */
	public void selectMemeber() {

	}// selectMemeber

	/**
	 * 회원 목록 검색
	 */
	public void getMemberList() {

	}// getMemberList

}// class
