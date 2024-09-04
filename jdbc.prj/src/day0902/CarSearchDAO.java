package day0902;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.vo.DbConnection;

public class CarSearchDAO {

	private static CarSearchDAO csDAO;

	private CarSearchDAO() {

	}

	public static CarSearchDAO getInstance() {
		if (csDAO == null) {
			csDAO = new CarSearchDAO();
		}
		return csDAO;
	}// getInstance

	public List<CarVO> selectMaker(String maker) throws SQLException {
		List<CarVO> list = new ArrayList<CarVO>();

		DbConnection dbCon = DbConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dbCon.getConn();

			StringBuilder selectCarMaker = new StringBuilder();
			selectCarMaker.append("  select rnum, country, maker, model, car_year, price, car_option  ").append(
					"  from( select /*+ index (car_model idx_car_model )*/ cc.country country, cc.maker maker, cma.model model, cmo.car_year car_year, cmo.price price, cmo.car_option car_option, rownum rnum  ")
					.append("  from car_country cc, car_maker cma, car_model cmo  ")
					.append("  where (cc.maker = cma.maker and cma.model = cmo.model) and cc.maker = ? and (input_Date<sysdate))")
					.append("  where rnum between 1 and 10  ");
			pstmt = con.prepareStatement(selectCarMaker.toString());

			pstmt.setString(1, maker);

			rs = pstmt.executeQuery();
			CarVO cVO = null;

			while (rs.next()) {
				cVO = new CarVO(rs.getInt("car_year"), rs.getInt("price"), rs.getString("country"),
						rs.getString("maker"), rs.getString("car_option"), rs.getString("model"));

				list.add(cVO);

			}

		} finally {
			dbCon.dbClose(rs, pstmt, con);

		}
		return list;

	}

}
