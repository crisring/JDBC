package day0905;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.co.sist.vo.DbConnection;

/**
 * Transaction의 처리
 */
public class TestTransaction {
	Connection con = null;

	/**
	 * 여러 개의 DML을 하나의 Transaction으로 구성된 method
	 * 
	 * @return 모든 insert가 수행되었을 때 행수
	 * @throws SQLException
	 */
	public int transcation(String name, String addr) throws SQLException {
		int rowCnt = 0;
		DbConnection dbCon = DbConnection.getInstance();
		con = dbCon.getConn();
		con.setAutoCommit(false);

		// test_transcation 추가
		String insertTranscation = "insert into test_transaction(name,addr) values(?,?)";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(insertTranscation); // 1행 또는 예외
		pstmt.setString(1, name);
		pstmt.setString(2, addr);

		int cnt1 = pstmt.executeUpdate();

		// test_transcation2추가
		String insertTranscation2 = "insert into test_transaction2(name,addr) values(?,?)";
		PreparedStatement pstmt2 = null;
		pstmt2 = con.prepareStatement(insertTranscation2); // 1행 또는 예외
		pstmt2.setString(1, name);
		pstmt2.setString(2, addr);

		int cnt2 = pstmt2.executeUpdate();
		rowCnt = cnt1 + cnt2;
		return rowCnt;

	}// Transaction

	/**
	 * 여러개의 쿼리문이 하나의 Transaction으로 구성된 method를 호출하여 사용 commit이나 rollback을 한 후, DMBS와
	 * 연결을 끊는 일
	 */
	public void useTransaction() {

		String name = "김현우3";
		String addr = "서울시 강남구3";

		try {
			int cnt = transcation(name, addr);

			if (cnt == 2) {
				System.out.println("DB추가작업 성공!");
				con.commit();
			}

		} catch (SQLException e) {
			e.printStackTrace();

			// 첫번째 쿼리나, 두번째 쿼리문에서 문제가 발생
			try {
				System.out.println("DB추가작업 실패");
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}// useTransaction

	public static void main(String[] args) {
		TestTransaction tt = new TestTransaction();
		tt.useTransaction();

	}// main

}// class
