package crm.prj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.co.sist.vo.DbConnection;

/**
 * Connection 객체를 획득 <br>
 * MemberDAO 클래스를 작성하고 회원등록, 회원 수정, 회원 삭제, 회원 상세 조회, 회원 목록 검색 기능의 메소드를 각각 구현한다.
 * 
 */

public class MemberDAO {

	/**
	 * 회원등록 - insert <br>
	 * 아이디 비번을
	 */
	public void insertMember() {

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dbCon.getConn();

			String insertMember = "insert into member(MEMBER_ID, NAME, PHONE_NUMBER) values(?,?,?)";

			pstmt = con.prepareStatement(insertMember);
			
			pstmt.set
			rs = pstmt.executeUpdate();

		} finally {

			dbCon.dbClose(rs, pstmt, con);
		}

	}// insertMember

	/**
	 * 회원 수정 - Update
	 */
	public void updateMember() {
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
