package day0903;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.vo.ClobVO;
import kr.co.sist.vo.DbConnection;

public class UseClob {

	public List<ClobVO> selectAllNews() throws SQLException {

		List<ClobVO> list = new ArrayList<ClobVO>();

		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 2. 커넥션 얻기
			con = dbCon.getConn();
			// 3. 쿼리문 생성객체 얻기
			String selectNews = "select title,content,writer,to_char(input_date, 'mm-dd-yy') input_date from test_clob";

			pstmt = con.prepareStatement(selectNews);
			// 4. 바인드변수 값 설정
			// 5. 쿼리문 수행 후 값 얻기 -> bufferedReader
			rs = pstmt.executeQuery();

			ClobVO cVO = null;
			while (rs.next()) {
				cVO = new ClobVO();
				cVO.setTitle(rs.getString("title"));
				cVO.setWriter(rs.getString("writer"));
				cVO.setContetnt(rs.getString("content"));
				cVO.setInput_date(rs.getString("input_date"));

				// clob 데이터를 가져오기 위해서 별도의 Stream 연결
				BufferedReader br = new BufferedReader(rs.getClob("content").getCharacterStream());

				try {

					StringBuilder sbContent = new StringBuilder();
					String temp;
					while ((temp = br.readLine()) != null) {
						sbContent.append(temp).append("\n");
					}
					cVO.setContetnt(sbContent.toString());

					br.close();
				} catch (IOException e) {
					cVO.setContetnt("기사내용을 읽어 들일 수 없습니다.");
					e.printStackTrace();

				}
				list.add(cVO);
			}

		} finally {

			// 6. 연결 끊기
			dbCon.dbClose(rs, pstmt, con);

		}

		return list;

	}// selectAllNews

	public void useAllNews() {

		try {
			List<ClobVO> list = selectAllNews();
			if (list.isEmpty()) {
				System.out.println("작성된 기사 내용이 없습니다.");
				return;
			}

			for (ClobVO cVO : list) {
				System.out.print("제목 : " + cVO.getTitle() + "\t");
				System.out.print("\n작성일 : " + cVO.getInput_date() + "\t");
				System.out.print("내용 : " + cVO.getContetnt() + "\t");
				System.out.print("\n기자 : " + cVO.getWriter() + "\t");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}// useAllNews

	public static void main(String[] args) {

		UseClob uc = new UseClob();
		try {
			uc.selectAllNews();
			uc.useAllNews();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// main

}// class
