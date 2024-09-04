package day0903;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import kr.co.sist.vo.DbConnection;

public class UseCallableStatement {

	public void useCallable(int i, int j) throws SQLException {

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		CallableStatement cstmt = null;

		try {
			con = dbCon.getConn();

			cstmt = con.prepareCall("{ call plus_proc(?,?,?,?) }");

			// 바인드 값 설정 - in parameter
			cstmt.setInt(1, i);
			cstmt.setInt(2, j);

			// out parameter
			cstmt.registerOutParameter(3, Types.NUMERIC);
			cstmt.registerOutParameter(4, Types.VARCHAR);

			cstmt.execute();

			int result = cstmt.getInt(3);
			String msg = cstmt.getString(4);

			System.out.println(result);
			System.out.println(msg);

		} finally {
			dbCon.dbClose(null, cstmt, con);

		}

	}

	public static void main(String[] args) {
		try {
			UseCallableStatement ucs = new UseCallableStatement();

			for (int i = 0; i < 10; i++) {
				ucs.useCallable(9, i);
				Thread.sleep(500);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		;

	}// main

}// class
