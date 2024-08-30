package day0829;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kr.co.sist.vo.CpEmpVO;

/**
 * DAO를 실행하여 결과를 받는 클래스 => XXXService로 변경 <br>
 * DBMS 작업을 하기전, 조회 결과를 가지고 업무 로직 구현 <Br>
 * method명 : 쿼리문이 들어가지 않고, 현업의 업무 용어로 method명이 반영되어야한다.
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
	}// modiftCpEmp

	/**
	 * 사원정보를 삭제하는 일
	 */
	public void removeCpEmp() {

		CpEmpVO ceVO = new CpEmpVO();
		ceVO.setEmpno(1111);

		StatementDAO sDAO = new StatementDAO();
		int empno = ceVO.getEmpno();

		try {
			int cnt = sDAO.deleteCpEmp(empno);

			// 0건 또는 primary key가 없다면 n건까지 삭제
			StringBuilder msg = new StringBuilder();
			msg.append(empno).append("번 사원 정보가");

			String temp = "삭제되지 않았습니다. 사원번호를 확인해주세요.";
			if (cnt != 0) {
				msg.append(cnt).append("건");
				temp = "삭제되었습니다.";
			}
			msg.append(temp);
			JOptionPane.showMessageDialog(null, msg.toString());

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "문제발생!!");
			e.printStackTrace();
		}
	}// removeCpEmp

	/**
	 * 사원정보 하나 검색하는 일
	 */
	public void searchOneCpEmp() {

		int empno = 1113;

		StatementDAO sDAO = new StatementDAO();
		try {
			CpEmpVO ceVO = sDAO.selectOnEmp(empno);

			if (ceVO == null) {
				JOptionPane.showMessageDialog(null, empno + "번 사원은 존재하지 않습니다.");
				return;
			} // end~if

			StringBuilder output = new StringBuilder();
			output.append(ceVO.getEmpno()).append("번 사원정보 조회 결과\n");

			output.append("사원명 : ").append(ceVO.getEname()).append("\n");
			output.append("직무 : ").append(ceVO.getJob()).append("\n");
			output.append("매니저번호 : ").append(ceVO.getMgr()).append("\n");

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy EEEE");
			output.append("입사일 : ").append(sdf.format(ceVO.getHiredate())).append("\n");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd a");
			output.append("입사일 : ").append(sdf.format(ceVO.getHiredate())).append("\n");
			output.append("연봉 : ").append(ceVO.getSal()).append("\n");
			output.append("부서번호 : ").append(ceVO.getDeptno()).append("\n");
			JTextArea jtaEmpDisplay = new JTextArea(output.toString(), 8, 20);
			JOptionPane.showMessageDialog(null, jtaEmpDisplay);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// searchOneCpEmp

	/**
	 * 모든 사원 정보를 검색하는 일
	 */
	public void searchAllCpEmp() {

		StatementDAO sDAO = new StatementDAO();

		try {
			List<CpEmpVO> listAllCpEmp = sDAO.selectAllCpEmp();
			StringBuilder output = new StringBuilder();

			output.append("사원번호\t사원명\t직무\t매니저번호\t연봉\t보너스\t입사일\t부서번호\t입사일\n");
			output.append(
					"-------------------------------------------------------------------------------------------------------\n");

			// 사원의 없는 경우
			if (listAllCpEmp.isEmpty()) {
				output.append("사원이 존재하지 않습니다.");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEEE a hh:mm");
			for (CpEmpVO ceVO : listAllCpEmp) {
				output.append(ceVO.getEmpno()).append("\t");
				output.append(ceVO.getEname()).append("\t");
				output.append(ceVO.getJob()).append("\t");
				output.append(ceVO.getMgr()).append("\t");
				output.append(ceVO.getSal()).append("\t");
				output.append(ceVO.getComm()).append("\t");
				output.append(ceVO.getHiredate()).append("\t");
				output.append(ceVO.getDeptno()).append("\t");

				output.append(sdf.format(ceVO.getHiredate())).append("\n");

			}
			JTextArea jtaEmpDisplay = new JTextArea(output.toString(), 8, 70);
			JScrollPane jsp = new JScrollPane(jtaEmpDisplay);
			JOptionPane.showMessageDialog(null, jsp);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// searchAllCpEmp

	public static void main(String[] args) {

		CRUDStatementDAO crudDAO = new CRUDStatementDAO();
		// crudDAO.addCpEmp();
		// crudDAO.modiftCpEmp();
		// crudDAO.removeCpEmp();
		// crudDAO.searchOneCpEmp();
		crudDAO.searchAllCpEmp();

	}// main

}// class
