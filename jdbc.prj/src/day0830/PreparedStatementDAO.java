package day0830;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.vo.CpEmpVO;
import kr.co.sist.vo.DbConnection;

public class PreparedStatementDAO {

	private static PreparedStatementDAO psDAO;

	private PreparedStatementDAO() {
	}// PreparedStatementDAO

	public static PreparedStatementDAO getInstance() {
		if (psDAO == null) {
			psDAO = new PreparedStatementDAO();
		} // end if
		return psDAO;
	}// getInstance

	/**
	 * 사원정보 추가 : 성공 | 예외
	 * 
	 * @param ceVO
	 * @throws SQLException
	 */
	public void insertCpEmp(CpEmpVO ceVO) throws SQLException {

		DbConnection dbCon = DbConnection.getInstance();
		// 1.드라이버로딩

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 2.커넥션얻기
			con = dbCon.getConn();
			// 3.쿼리문 생성객체 얻기
			String insertCpEmp = "insert into cp_emp2(empno,ename,job,mgr,sal,comm,deptno)values(?,?,?,?,?,?,?)";

			pstmt = con.prepareStatement(insertCpEmp);
			// 4.바인드변수 값 할당
			pstmt.setInt(1, ceVO.getEmpno());
			pstmt.setString(2, ceVO.getEname());
			pstmt.setString(3, ceVO.getJob());
			pstmt.setInt(4, ceVO.getMgr());
			pstmt.setDouble(5, ceVO.getSal());
			pstmt.setDouble(6, ceVO.getComm());
			pstmt.setInt(7, ceVO.getDeptno());

			// 5.쿼리문 수행 후 결과 얻기
			pstmt.executeUpdate();

		} finally {
			// 6.연결 끊기
			dbCon.dbClose(null, pstmt, con);
		} // end finally
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
		PreparedStatement pstmt = null;

		// 2. 커넥션 얻기
		try {
			con = dbCon.getConn();
			// 3. 쿼리문 생성 객체 얻기
			StringBuilder updateCpEmp = new StringBuilder();
			updateCpEmp.append(" update cp_emp2 ").append(" set job=?, mgr=?, sal=?, comm=?, deptno=?")
					.append(" where empno=? ");

			pstmt = con.prepareStatement(updateCpEmp.toString());
			// 4. bind변수에 값 할당
			pstmt.setString(1, ceVO.getJob());
			pstmt.setInt(2, ceVO.getMgr());
			pstmt.setDouble(3, ceVO.getSal());
			pstmt.setDouble(4, ceVO.getComm());
			pstmt.setInt(5, ceVO.getDeptno());
			pstmt.setInt(6, ceVO.getEmpno());

			// 5. 쿼리문 수행 후 결과 얻기
			rowCnt = pstmt.executeUpdate();

		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(null, pstmt, con);
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
	public int deleteCpEmp(int empno) throws SQLException {
		int rowCnt = 0;

		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			// 2. connection 얻기
			con = dbCon.getConn("localhost", "scott", "tiger");

			// 3. 쿼리문 생성객체 얻기
			String deleteCpEmp = "delete from cp_emp2 where empno=?";
			pstmt = con.prepareStatement(deleteCpEmp);

			// 4. bind변수에 값 할당
			pstmt.setInt(1, empno);

			// 5. 쿼리문 수행후 결과 얻기
			rowCnt = pstmt.executeUpdate();
		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(null, pstmt, con);
		}
		return rowCnt;
	}// deleteCpEmp

	/**
	 * 사원 정보 조회 : null | 객체 | 예외
	 * 
	 * @param empno
	 * @return
	 * @throws SQLException
	 */
	public CpEmpVO selectOneCpEmp(int empno) throws SQLException {
		CpEmpVO ceVO = null;

		// 1. 드라이버 로딩
		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 2. connection 얻기
			con = dbCon.getConn("localhost", "scott", "tiger");
			// 3. 쿼리문 생성 객체 얻기
			StringBuilder selectOneCpEmp = new StringBuilder();
			selectOneCpEmp.append(" select ename,job,mgr,hiredate,sal,comm,deptno from cp_emp2")
					.append(" where empno = ? ");
			pstmt = con.prepareStatement(selectOneCpEmp.toString());

			// 4. bind 얻기
			pstmt.setInt(1, empno);

			// 5. 쿼리문 수행
			rs = pstmt.executeQuery();

			// ename,job,mgr,hiredate,sal,comm,deptno
			if (rs.next()) {
				ceVO = new CpEmpVO();

				ceVO.setEname(rs.getString("ename"));
				ceVO.setJob(rs.getString("job"));
				ceVO.setMgr(rs.getInt("mgr"));
				ceVO.setHiredate(rs.getDate("hiredate"));
				ceVO.setSal(rs.getDouble("sal"));
				ceVO.setComm(rs.getDouble("comm"));
				ceVO.setDeptno(rs.getInt("deptno"));
			}

		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(rs, pstmt, con);

		}

		return ceVO;
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
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dbCon.getConn();
			StringBuilder selectAllCpEmp = new StringBuilder();
			selectAllCpEmp.append(" select empno,ename,to_Char(hiredate,'yyyy-mm-dd day q') hiredate,sal")
					.append(" from cp_emp2");
			// 3. 쿼리문 생성 객체 얻기
			pstmt = con.prepareStatement(selectAllCpEmp.toString());
			// 4. bind 값 할당
			// 5. 쿼리문 수행 => n개 행 검색
			rs = pstmt.executeQuery();
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
			dbCon.dbClose(rs, pstmt, con);
		}

		return list;
	}// selectAllCpEmp

}// class
