package day0902;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import day0830.DbConnection;

public class UseLikeDAO {

	/**
	 * car_model 테이블에서 옵션에 'ABS'가 들어있는 차량의 모델명, 연식, 가격, 배기량, 옵션 검색 <br>
	 * String, int, int, int String
	 * 
	 * @throws SQLException
	 */
	public void like(String option) throws SQLException {

		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			// 2. 커넥션 얻기
			con = dbCon.getConn();
			StringBuilder selectCar = new StringBuilder();
			selectCar.append("  select model, car_year, price, cc, car_option").append("  from car_model  ")
					.append("  where car_option like '%'||?||'%'  ");

			// 3. 쿼리문 수행 객체 얻기
			pstmt = con.prepareStatement(selectCar.toString());

			// 4. bind객체에 넣기
			pstmt.setString(1, option);

			// 5. 쿼리문 수행
			rs = pstmt.executeQuery();

			System.out.println("\n모델명\t년식\t가격\t배기량\t옵션");
			while (rs.next()) {
				System.out.print(rs.getString("model") + "\t");
				System.out.print(rs.getInt("car_year") + "\t");
				System.out.print(rs.getInt("price") + "\t");
				System.out.print(rs.getInt("cc") + "\t");
				System.out.print(rs.getString("car_option") + "\n");
			}

		} finally {

			// 6. 연결끊기
			dbCon.dbClose(rs, pstmt, con);
		}

	}// like

	public static void main(String[] args) {
		try {
			new UseLikeDAO().like("ABS");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// main

}// UseLikeDAO
