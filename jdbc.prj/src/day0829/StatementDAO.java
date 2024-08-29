package day0829;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.vo.CpEmpVO;

/**
 * DAO (Data Access Object) : DB작업을 목적으로 만드는 클래스 <br>
 * DBMS의 업무만 정의 업무로직을 구현하지 않는다.
 */
public class StatementDAO {

	/**
	 * CP_EMP2 테이블에 입력된 사원 정보를 추가하는 일
	 * 
	 * @param ceVO 값을 가진 VO
	 * @throws SQLException
	 */
	public void insetCpEmp(CpEmpVO ceVO) throws SQLException {

		// 1. Driver 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = null;
		Statement stmt = null;

		try {
			// 2. connection 얻기
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String id = "scott";
			String password = "tiger";

			// 3. 쿼리문 생성 객체 Statement
			con = DriverManager.getConnection(url, id, password);

			stmt = con.createStatement();

			// 4. 결과 출력
			StringBuilder insertCpEmp = new StringBuilder();
			insertCpEmp.append("insert into CP_EMP2(empno, ename, job, mgr, sal, comm, deptno) values(")
					.append(ceVO.getEmpno()).append(",'").append(ceVO.getEname()).append("','").append(ceVO.getJob())
					.append("',").append(ceVO.getMgr()).append(",").append(ceVO.getSal()).append(",")
					.append(ceVO.getComm()).append(",").append(ceVO.getDeptno()).append(")");

			System.out.println(insertCpEmp);

			stmt.executeUpdate(insertCpEmp.toString());

		} finally {

			// 5. 연결 끊기
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}

		}

	}// insetCpEmp

	/**
	 * CP_EMP2 테이블에 사원정보를 변경하는 일 <br>
	 * 사원번호, 부서명, 연봉, 보너스를 받아서 사원번호에 해당하는 사원의 정보를 변경
	 * 
	 * @param ceVO
	 * @return 변경된 사원의 수
	 * @throws SQLException
	 */
	public int updateCpEmp(CpEmpVO ceVO) throws SQLException {
		int rowCnt = 0;

		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = null;
		Statement stmt = null;

		try {

			// 2. 커넥션 얻기
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String id = "scott";
			String password = "tiger";
			con = DriverManager.getConnection(url, id, password);
			// 3. 쿼리문 생성 객체 statment
			stmt = con.createStatement();
			// 4. 수행 결과
			StringBuilder updateCpEmp = new StringBuilder();
			updateCpEmp.append("update cp_emp2").append(" set deptno=").append(ceVO.getDeptno()).append(",job='")
					.append(ceVO.getJob()).append("',sal=").append(ceVO.getSal()).append(",comm=")
					.append(ceVO.getComm()).append(" where empno=").append(ceVO.getEmpno());

			System.out.println(updateCpEmp);
			rowCnt = stmt.executeUpdate(updateCpEmp.toString());

		} finally {

			// 5. 연결 끊기
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}

		}

		return rowCnt;
	}// updateCpEmp

	/**
	 * CP_EMP2 테이블에 사원정보를 삭제하는 일
	 * 
	 * @param empno
	 * @return
	 * @throws SQLException
	 */
	public int deleteCpEmp(int empno) throws SQLException {
		int rowCnt = 0;

		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 2. 커넥션 얻기
		Connection con = null;
		Statement stmt = null;

		try {
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String id = "scott";
			String password = "tiger";

			// 3. 쿼리문 생성 객체 얻기
			con = DriverManager.getConnection(url, id, password);
			stmt = con.createStatement();

			// 4. 쿼리문 수행
			StringBuilder deleteCpEmp = new StringBuilder();
			deleteCpEmp.append("delete from cp_emp2 where empno=").append(empno);

			System.out.println(deleteCpEmp);
			// rowCnt = stmt.executeUpdate(deleteCpEmp.toString());

		} finally {

			if (stmt != null) {
				stmt.close();
			}

			if (con != null) {
				con.close();
			}

		}

		// 5. 연결 끊기

		return rowCnt;
	}// deleteCpEmp

	/**
	 * 사원번호를 입력받아 사원 정보를 조회하는 일
	 * 
	 * @param empno 검색할 사원번호
	 * @return 사원번호로 검색한 결과
	 * @throws SQLException
	 */
	public CpEmpVO selectOnEmp(int empno) throws SQLException {
		CpEmpVO ceVO = null;

		return ceVO;
	}// selectOnEmp

	/**
	 * 모든 사원 정보를 조회하는 일
	 * 
	 * @return 모든 사원 정보
	 * @throws SQLException
	 */
	public List<CpEmpVO> selectAllCpEmp() throws SQLException {
		List<CpEmpVO> list = new ArrayList<CpEmpVO>();

		return list;
	}

}
