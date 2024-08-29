package day0829;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.vo.CpEmpVO;

/**
 * DAO를 실행하여 결과를 받는 클래스
 */
public class CRUDStatementDAO {

	/**
	 * 사원정보를 추가하는 일
	 */
	public void addCpEmp() {

		CpEmpVO ceVO = new CpEmpVO(1115, 7788, 3100, 100, 20, "", "개발자");
		StatementDAO sDAO = new StatementDAO();
		try {
			sDAO.insetCpEmp(ceVO);
			System.out.println(ceVO.getEmpno() + "번 사원정보 추가성공");
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			StringBuilder errMsg = new StringBuilder();
			errMsg.append(ceVO.getEmpno() + "번 사원정보 추가실패\n");
			switch (e.getErrorCode()) {

			case 1:
				errMsg.append("사원번호가 중복되었습니다.");
				break;
			case 1400:
				errMsg.append("사원명은 필수 입력입니다.");
				break;
			case 1438:
				errMsg.append("사원번호 4자리, 매니저번호 4자리, 부서번호 2자리, 연봉, 커미션은 5자리 까지 입니다.");
				break;
			case 12899:
				errMsg.append("사원이름 3글자, 직무 3글자 까지 입니다.");
				break;

			}

			JOptionPane.showMessageDialog(null, errMsg);
			e.printStackTrace();
		}

	}// addCpEmp

	/**
	 * 사원정보를 변경하는 일
	 */
	public void modiftCpEmp() {
		CpEmpVO ceVO = new CpEmpVO();
		ceVO.setEmpno(1114);
		ceVO.setDeptno(30);
		ceVO.setJob("개팔자");
		ceVO.setSal(5000);
		ceVO.setComm(500);

		StatementDAO sDAO = new StatementDAO();
		try {
			int cnt = sDAO.updateCpEmp(ceVO); // 0~n건 발생 -> 0또는 1
			String msg = "사원번호를 확인하세요";

			switch (cnt) {
			case 1:
				msg = "사원정보 변경 성공";
			}
			JOptionPane.showMessageDialog(null, ceVO.getEmpno() + "번" + msg);

		} catch (SQLException e) {
			String msg = "문제 발생!";
			switch (e.getErrorCode()) {
			case 1438:
				msg = "부서번호 2자리, 연봉, 보너스 5자리 까지만 입력";
				break;

			case 12899:
				msg = "직무 한글 3자리까지만 입력";
			}
			JOptionPane.showMessageDialog(null, ceVO.getEmpno() + "번 변경실패" + msg);
			e.printStackTrace();
		}

	}

	/**
	 * 사원정보를 삭제하는 일
	 */
	public void removeCpEmp() {

		CpEmpVO ceVO = new CpEmpVO();
		ceVO.setEmpno(0);

		StatementDAO sDAO = new StatementDAO();

		try {
			int cnt = sDAO.deleteCpEmp(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 사원정보 하나 검색하는 일
	 */
	public void searchOneCpEmp() {

	}// searchOneCpEmp

	/**
	 * 모든 사원 정보를 검색하는 일
	 */
	public void searchAllCpEmp() {

	}// searchAllCpEmp

	public static void main(String[] args) {

		CRUDStatementDAO crudDAO = new CRUDStatementDAO();
		// crudDAO.addCpEmp();
		crudDAO.modiftCpEmp();
		crudDAO.removeCpEmp();

	}// main

}// class
