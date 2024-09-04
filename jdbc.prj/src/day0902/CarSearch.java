package day0902;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kr.co.sist.vo.DbConnection;

/**
 * 문제 : 제조사를 입력받아 해당 제조사의 모든 차량을 검색하는 코드 작성 preparestatemnet 사용 <Br>
 * 번호, 제조국, 제조사, 모델명, 연식, 가격, 옵션을 검색 <BR>
 * 단, 가장 마지막에 입력된 차량 이전차량부터 10건만 출력 (데이터의 순서보다 속도 중요) Singleton작업
 */
public class CarSearch {
	private static CarSearch cs;

	public static CarSearch getInstance() {
		if (cs == null) {
			cs = new CarSearch();
		} // end if
		return cs;
	}// getInstance

	public List<CarVO> searchAllCarModel() throws SQLException {

		List<CarVO> list = new ArrayList<CarVO>();

		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 2. 커넥션 얻기
			con = dbCon.getConn();

			// 3. 쿼리문 생성 객체 얻기
			StringBuilder SearchAllCarModel = new StringBuilder();

			// 제조국, 제조사, 모델명, 연식, 가격, 옵션
			// 가장 마지막에 입력된 차량 이전차량부터 10건만 출력
			SearchAllCarModel.append(" select country, maker, model, car_year, price, car_option ").append(" from (")
					.append(" select cc.country country, cc.maker maker, cma.model model, cmo.car_year car_year, cmo.price price, cmo.car_option car_option, row_number() over(order by input_date desc) rnum ")
					.append(" from car_country cc, car_maker cma, car_model cmo ")
					.append(" where (cc.maker = cma.maker and cma.model = cmo.model) and cc.maker = '현대'")
					.append(") where rnum between 2 and 7 ");
			pstmt = con.prepareStatement(SearchAllCarModel.toString());
			// 4. bin 값 할당
			// 5. 쿼리문 수행 => n개 행 검색
			rs = pstmt.executeQuery();

			CarVO cmVO = null;

			while (rs.next()) {
				cmVO = new CarVO(rs.getInt("car_year"), rs.getInt("price"), rs.getString("country"),
						rs.getString("maker"), rs.getString("car_option"), rs.getString("model"));
				// 저장된 같은 이름의 객체를 관리하기 위해 list에 추가
				list.add(cmVO);
			}

		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(rs, pstmt, con);

		}
		return list;
	}// SearchAllCarModel

	public void searchAllCpEmp() {
		// 모든 사원 정보를 검색하여 출력하는 일
		CarSearch cs = CarSearch.getInstance();

		try {
			List<CarVO> list = cs.searchAllCarModel();

			StringBuilder output = new StringBuilder();
			output.append("번호\t제조국\t제조사\t모델명\t연식\t가격\t옵션\n").append(
					"---------------------------------------------------------------------------------------------------------------------------------------\n");

			if (list.isEmpty()) {
				output.append("차량정보가 존재하지 않습니다.");
			}
			int num = 1;
			for (CarVO cmVO : list) {
				output.append(num).append("\t");
				num += 1;
				output.append(cmVO.getCountry()).append("\t");
				output.append(cmVO.getMaker()).append("\t");
				output.append(cmVO.getModel()).append("\t");
				output.append(cmVO.getCar_year()).append("\t");
				output.append(cmVO.getPrice()).append("\t");
				output.append(cmVO.getCar_option()).append("\n");

			}
			output.append(
					"---------------------------------------------------------------------------------------------------------------------------------------\n");
			output.append("총 차량수 : [" + list.size()).append("]\n");

			JTextArea jta = new JTextArea(output.toString(), 20, 60);
			JScrollPane jsp = new JScrollPane(jta);
			JOptionPane.showMessageDialog(null, jsp);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "검색 작업 중 문제 발생");
			e.printStackTrace();

		}

	}// searchAllCpEmp

	public void menu() {

		CarSearch cs = CarSearch.getInstance();

		JOptionPane.showMessageDialog(null, "모든 차량을 검색합니다.");
		cs.searchAllCpEmp();

	}

	public static void main(String[] args) {

		CarSearch cs = CarSearch.getInstance();
		cs.menu();

	}
}
