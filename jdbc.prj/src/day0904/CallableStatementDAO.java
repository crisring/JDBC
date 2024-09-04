package day0904;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.vo.CallableResultVO;
import kr.co.sist.vo.CpEmpVO;
import kr.co.sist.vo.DbConnection;
import oracle.jdbc.OracleTypes;

public class CallableStatementDAO {

	private static CallableStatementDAO psDAO;

	private CallableStatementDAO() {
	}// PreparedStatementDAO

	public static CallableStatementDAO getInstance() {
		if (psDAO == null) {
			psDAO = new CallableStatementDAO();
		} // end if
		return psDAO;
	}// getInstance

	/**
	 * 사원정보 추가 : 성공 | 예외
	 * 
	 * @param ceVO
	 * @throws SQLException
	 */
	public CallableResultVO insertCpEmp(CpEmpVO ceVO) throws SQLException {

		CallableResultVO crVO = null;

		DbConnection dbCon = DbConnection.getInstance();
		// 1.드라이버로딩

		Connection con = null;
		CallableStatement cstmt = null;

		try {
			// 2.커넥션얻기
			con = dbCon.getConn();
			// 3.쿼리문 생성객체 얻기

			cstmt = con.prepareCall("{ call insert_cp_emp(?,?,?,?,?,?,?,?,? ) }");
			// 4.바인드변수 값 할당
			cstmt.setInt(1, ceVO.getEmpno());
			cstmt.setString(2, ceVO.getEname());
			cstmt.setString(3, ceVO.getJob());
			cstmt.setInt(4, ceVO.getMgr());
			cstmt.setDouble(5, ceVO.getSal());
			cstmt.setDouble(6, ceVO.getComm());
			cstmt.setInt(7, ceVO.getDeptno());

			// out parameter
			cstmt.registerOutParameter(8, Types.NUMERIC);
			cstmt.registerOutParameter(9, Types.VARCHAR);

			// 5.쿼리문 수행 후 결과 얻기
			cstmt.executeUpdate();

			// 6. out parameter 저장된 값 받기
			int cnt = cstmt.getInt(8);
			String msg = cstmt.getString(9);

			crVO = new CallableResultVO(cnt, msg);
		} finally {
			// 6.연결 끊기
			dbCon.dbClose(null, cstmt, con);
		} // end finally

		return crVO;
	}// insertCpEmp

	/**
	 * 사원정보를 변경하는 일 : 0행, n행, 예외 <br>
	 * 사원번호, 직무, 매니저번호, 연봉, 보너스, 부서번호를 입력 받아서 <br>
	 * 사원번호에 해당하는 직무, 메니저번호, 연봉, 보너스, 부서번호를 변경하는 일
	 * 
	 * @param ceVO
	 * @return
	 * @throws SQLException
	 */
	public int updateCpEmp(CpEmpVO ceVO) throws SQLException {
		int rowCnt = 0;

		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();

		Connection con = null;
		CallableStatement cstmt = null;

		// 2. 커넥션 얻기
		try {
			con = dbCon.getConn();
			// 3. 쿼리문 생성 객체 얻기

			cstmt = con.prepareCall("{ call UPDATE_CP_EMP(?,?,?,?,?,?,?) }");
			// 4. bind변수에 값 할당
			cstmt.setInt(1, ceVO.getEmpno());
			cstmt.setString(2, ceVO.getJob());
			cstmt.setDouble(3, ceVO.getSal());
			cstmt.setDouble(4, ceVO.getComm());
			cstmt.setInt(5, ceVO.getDeptno());
			// out parameter
			cstmt.registerOutParameter(6, Types.NUMERIC);
			cstmt.registerOutParameter(7, Types.VARCHAR);

			// 5. 쿼리문 수행 후 결과 얻기
			cstmt.executeUpdate();
			// 6. cnt 값 rowCnt 저장
			rowCnt = cstmt.getInt(6);
		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(null, cstmt, con);
		}

		return rowCnt;
	}// updateCpEmp

	/**
	 * 사원 정보를 삭제하는 일: 0행 | n행 | 예외 <br>
	 * 사원번호를 입력받아 해당 사원 정보를 삭제하는일. <br>
	 * 
	 * @param empno
	 * @return
	 * @throws SQLException
	 */
	public CallableResultVO deleteCpEmp(int empno) throws SQLException {
		CallableResultVO crVO = null;

		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		CallableStatement cstmt = null;
		try {
			// 2. connection 얻기
			con = dbCon.getConn("localhost", "scott", "tiger");

			// 3. 쿼리문 생성객체 얻기
			cstmt = con.prepareCall("{call delete_cp_emp(?,?,?) }");

			// 4. bind변수에 값 할당
			// in parameter
			cstmt.setInt(1, empno);

			// out parameter
			cstmt.registerOutParameter(2, OracleTypes.NUMERIC);
			cstmt.registerOutParameter(3, OracleTypes.VARCHAR);

			// 5. 쿼리문 수행후 결과 얻기
			cstmt.execute();

			// 6. out parameter의 값 받기
			crVO = new CallableResultVO(cstmt.getInt(2), cstmt.getString(3));
		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(null, cstmt, con);
		}
		return crVO;
	}// deleteCpEmp

	/**
	 * 모든 사원정보 조회 : 객체의 크기가 0개 또는 n개 | 예외
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<CpEmpVO> selectDeptCpEmp(int deptno) throws SQLException {
		List<CpEmpVO> list = new ArrayList<CpEmpVO>();

		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		// 2. 커넥션 얻기
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			con = dbCon.getConn();
			// 3. 쿼리문 생성 객체 얻기
			cstmt = con.prepareCall("  { call select_cp_emp2(?,?,?) }");
			// 4. bind 값 할당

			cstmt.setInt(1, deptno);
			cstmt.registerOutParameter(2, Types.REF_CURSOR);
			cstmt.registerOutParameter(3, Types.VARCHAR);

			// 5. 쿼리문 수행 => n개 행 검색
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(2);
			CpEmpVO ceVO = null;

			while (rs.next()) {
				ceVO = new CpEmpVO();
				ceVO.setEmpno(rs.getInt("empno"));
				ceVO.setEname(rs.getString("ename"));
				ceVO.setSal(rs.getDouble("sal"));
				ceVO.setHiredateStr(rs.getString("hiredate"));
				ceVO.setDeptno(rs.getInt("deptno"));

				// 저장된 같은 이름의 객체를 관리하기 위해 list에 추가
				list.add(ceVO);
			}
		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(rs, cstmt, con);
		}

		return list;
	}// selectDeptCpEmp

	/**
	 * 사원 정보 조회 : null | 객체 | 예외
	 * 
	 * @param empno
	 * @return
	 * @throws SQLException
	 */
	public String selectOneCpEmp(int empno) throws SQLException {
		String resultMsg = "";
		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		CallableStatement cstmt = null;
		try {
			// 2. connection 얻기
			con = dbCon.getConn("localhost", "scott", "tiger");
			// 3. 쿼리문 생성 객체 얻기

			cstmt = con.prepareCall("{call select_cp_emp(?,?,?)}");

			// 4. bind 얻기
			cstmt.setInt(1, empno);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);

			// 5. 쿼리문 수행
			cstmt.execute();

			String temp = cstmt.getString(2);
			String errMsg = cstmt.getString(3); // errMsg
			resultMsg = temp != null ? temp : errMsg;

		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(null, cstmt, con);

		}

		return resultMsg;
	}// selectOneCpEmp

	/**
	 * 모든 사원정보 조회 : 객체의 크기가 0개 또는 n개 | 예외
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<CpEmpVO> selectAllCpEmp() throws SQLException {
		List<CpEmpVO> list = new ArrayList<CpEmpVO>();

		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		// 2. 커넥션 얻기
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			con = dbCon.getConn();
			// 3. 쿼리문 생성 객체 얻기
			cstmt = con.prepareCall("  { call select_all_cp_emp(?,?) }");
			// 4. bind 값 할당
			cstmt.registerOutParameter(1, Types.REF_CURSOR);
			cstmt.registerOutParameter(2, Types.VARCHAR);

			// 5. 쿼리문 수행 => n개 행 검색
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			CpEmpVO ceVO = null;

			while (rs.next()) {
				ceVO = new CpEmpVO();
				ceVO.setEmpno(rs.getInt("empno"));
				ceVO.setEname(rs.getString("ename"));
				ceVO.setHiredateStr(rs.getString("hiredate"));
				ceVO.setSal(rs.getDouble("sal"));

				// 저장된 같은 이름의 객체를 관리하기 위해 list에 추가
				list.add(ceVO);
			}
		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(rs, cstmt, con);
		}

		return list;
	}// selectAllCpEmp

}// class
