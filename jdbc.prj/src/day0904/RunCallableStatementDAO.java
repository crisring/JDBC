package day0904;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kr.co.sist.vo.CallableResultVO;
import kr.co.sist.vo.CpEmpVO;

public class RunCallableStatementDAO {

	public static final String ADD_EMP = "1";
	public static final String MODIFY_EMP = "2";
	public static final String REMOVE_EMP = "3";
	public static final String SEARCH_ONE_EMP = "4";
	public static final String SEARCH_DEPT_EMP = "5";
	public static final String SEARCH_ALL_EMP = "6";
	public static final String EXIT_PROG = "7";

	public static final int EMPNO = 0;
	public static final int ENAME = 1;
	public static final int JOB = 2;
	public static final int MGR = 3;
	public static final int SAL = 4;
	public static final int COMM = 5;
	public static final int DEPTNO = 6;

	public void menu() {

		boolean exitFlag = false;

		String selectedMenu = "0";
		do {
			selectedMenu = JOptionPane
					.showInputDialog("사원관리\n1.사원추가 2.사원변경 3.사원삭제 4.사원조회 5. 부서조회 6.모든사원조회 7.종료\n번호를 입력해주세요.");
			if (selectedMenu != null) {
				switch (selectedMenu) {
				case ADD_EMP:
					addCpEmp();
					break;
				case MODIFY_EMP:
					modifyCpEmp();
					break;
				case REMOVE_EMP:
					removeCpEmp();
					break;
				case SEARCH_ONE_EMP:
					searchOneCpEmp();
					break;
				case SEARCH_DEPT_EMP:
					searchDeptCpEmp();
					break;
				case SEARCH_ALL_EMP:
					searchAllCpEmp();
					break;
				case EXIT_PROG:
					switch (JOptionPane.showConfirmDialog(null, "종료하시겠습니까?")) {
					case JOptionPane.OK_OPTION:
						exitFlag = true;

					}// end switch
				}// end switch
			} else {
				switch (JOptionPane.showConfirmDialog(null, "종료하시겠습니까?")) {
				case JOptionPane.OK_OPTION:
					exitFlag = true;
				}
			} // end if

		} while (!exitFlag);

	}// menu

	public void addCpEmp() {
		String tempData = JOptionPane
				.showInputDialog("Callable 사원정보입력\n 입력 예)사원번호,사원명,직무,매니저번호,연봉,보너스,부서번호\n의 형식으로 입력해주세요.");
		if (tempData != null && !tempData.isEmpty()) {
			tempData = tempData.replaceAll(" ", "");
			String[] inputData = tempData.split(",");

			if (inputData.length != 7) {
				JOptionPane.showMessageDialog(null, "입력형식이 올바르지 않습니다.");
				return;
			} // end if

			try {
				// 입력된 데이터를 VO에 저장
				CpEmpVO ceVO = new CpEmpVO(parseInt(inputData[EMPNO]), parseInt(inputData[MGR]),
						parseInt(inputData[DEPTNO]), parseDouble(inputData[SAL]), parseDouble(inputData[COMM]),
						inputData[ENAME], inputData[JOB]);

				// DB작업을 수행
				CallableStatementDAO psDAO = CallableStatementDAO.getInstance();
				CallableResultVO crVO = psDAO.insertCpEmp(ceVO);// 성공

				String msg = crVO.getResultMSG();
				if (crVO.getCnt() == 0) {
					System.out.println(msg); // 문제 발생시 개발자가 봐야하는 message
					msg = "사원정보 추가 시 문제 발생, 사원번호 확인해주세요";

				}

				JOptionPane.showMessageDialog(null, inputData[EMPNO] + "번 사원정보가 추가되었습니다.");

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "사원번호,매니저번호,부서번호,연봉,보너스는 숫자로만 입력할 수 있습니다.");
			} catch (SQLException se) {// 예외
				// Statement의 insert를 참조
				se.printStackTrace();
				JOptionPane.showMessageDialog(null, "문제 발생");
			} // end catch

		} // end if
	}// addCpEmp

	public void modifyCpEmp() {

		String tempData = JOptionPane.showInputDialog("Callable 사원정보수정\n 입력 예)사원번호,직무,연봉,보너스,부서번호\n의 형식으로 입력해주세요.");
		if (tempData != null && !tempData.isEmpty()) {
			tempData = tempData.replaceAll(" ", "");
			String[] inputData = tempData.split(",");

			if (inputData.length != 5) {
				JOptionPane.showMessageDialog(null, "입력형식이 올바르지 않습니다.");
				return;
			}

			try {
				// 입력된 데이터를 VO에 저장
				int empno = parseInt(inputData[EMPNO]); // 사원번호
				String job = inputData[1]; // 직무
				double sal = parseDouble(inputData[2]); // 연봉
				double comm = parseDouble(inputData[3]); // 보너스
				int deptno = parseInt(inputData[4]); // 부서번호

				CpEmpVO ceVO = new CpEmpVO(empno, 0, deptno, sal, comm, "", job);

				// DB작업을 수행
				CallableStatementDAO csDAO = CallableStatementDAO.getInstance();

				int rowCnt = csDAO.updateCpEmp(ceVO);

				String resultMsg = "사원번호가 존재하지 않습니다.\n사원번호를 확인해주세요.";
				if (rowCnt != 0) {
					resultMsg = " 사원정보를 변경하였습니다.";
				}

				JOptionPane.showMessageDialog(null, empno + resultMsg);

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "사원번호, 부서번호, 연봉, 보너스는 숫자로만 입력할 수 있습니다.");
			} catch (SQLException se) {
				se.printStackTrace();
				JOptionPane.showMessageDialog(null, "DB 작업 중 문제 발생. 다시 시도해주세요.");
			}
		}
	}

	public void removeCpEmp() {
		// 사원번호를 입력받아서 해당 사원을 삭제
		try {

			int empno = parseInt(JOptionPane.showInputDialog("Callable 사원삭제\n사원번호를 입력해주세요."));

			CallableStatementDAO pDAO = CallableStatementDAO.getInstance();
			CallableResultVO crVO = pDAO.deleteCpEmp(empno);

			String msg = crVO.getResultMSG();

			// 프로시저가 이미 msg를 만들어놨기 때문에 필요 x
//			if (crVO.getCnt() != 0) {
//				msg = "사원정보가 삭제 되었습니다.";
//			}

			JOptionPane.showMessageDialog(null, empno + msg);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "삭제 중 문제 발생하였습니다!!");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "사원번호는 숫자입니다!!");
			e.printStackTrace();
		}

	}// removeCpEmp

	public void searchDeptCpEmp() {
		// 모든 사원 정보를 검색하여 출력하는 일
		CallableStatementDAO psDAO = CallableStatementDAO.getInstance();

		try {
			int deptno = parseInt(JOptionPane.showInputDialog("Callable 부서조회\n부서번호를 입력해주세요."));
			List<CpEmpVO> list = psDAO.selectDeptCpEmp(deptno);

			// 사원번호, 사원명, 연봉, 입사일, 부서번호
			StringBuilder output = new StringBuilder();
			output.append("사원번호\t사원명\t연봉\t입사일\t부서번호\n")
					.append("--------------------------------------------------------------------------------------\n");

			if (list.isEmpty()) {
				output.append("사원정보가 존재하지 않습니다.");
			}

			Double totalSal = 0.0;
			for (CpEmpVO ceVO : list) {
				output.append(ceVO.getEmpno()).append("\t");
				output.append(ceVO.getEname()).append("\t");
				output.append(ceVO.getSal()).append("\t");
				output.append(ceVO.getHiredateStr()).append("\t");
				output.append(ceVO.getDeptno()).append("\n");
				totalSal += ceVO.getSal();

			}
			output.append("--------------------------------------------------------------------------------------\n");

			output.append("총 사원수 : " + list.size()).append("\n");
			output.append("연봉합 : ".concat("$")).append(totalSal);

			JTextArea jta = new JTextArea(output.toString(), 20, 40);
			JScrollPane jsp = new JScrollPane(jta);
			JOptionPane.showMessageDialog(null, jsp);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "검색 작업 중 문제 발생");
			e.printStackTrace();

		}

	}// searchDeptCpEmp

	public void searchOneCpEmp() {

		// 사원번호를 입력바아서 해당사원 정보를 조회하여 출력하는일
		try {

			int empno = parseInt(JOptionPane.showInputDialog("사원조회\n사원번호를 입력해주세요."));

			CallableStatementDAO pDAO = CallableStatementDAO.getInstance();
			String msg = pDAO.selectOneCpEmp(empno);

			JOptionPane.showMessageDialog(null, msg);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "검색 중 문제 발생하였습니다!!");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "사원번호는 숫자입니다!!");
			e.printStackTrace();
		}

	}// searchOneCpEmp

	public void searchAllCpEmp() {
		// 모든 사원 정보를 검색하여 출력하는 일
		CallableStatementDAO psDAO = CallableStatementDAO.getInstance();

		try {
			List<CpEmpVO> list = psDAO.selectAllCpEmp();

			StringBuilder output = new StringBuilder();
			output.append("사원번호\t사원명\t연봉\t입사일\n")
					.append("-----------------------------------------------------------------------------\n");

			if (list.isEmpty()) {
				output.append("사원정보가 존재하지 않습니다.");
			}

			Double totalSal = 0.0;
			for (CpEmpVO ceVO : list) {
				output.append(ceVO.getEmpno()).append("\t");
				output.append(ceVO.getEname()).append("\t");
				output.append(ceVO.getSal()).append("\t");
				output.append(ceVO.getHiredateStr()).append("\n");
				totalSal += ceVO.getSal();

			}
			output.append("-----------------------------------------------------------------------------\n");
			output.append("총 사원수 : " + list.size()).append("\n");
			output.append("연봉합 : ".concat("$")).append(totalSal);

			JTextArea jta = new JTextArea(output.toString(), 20, 40);
			JScrollPane jsp = new JScrollPane(jta);
			JOptionPane.showMessageDialog(null, jsp);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "검색 작업 중 문제 발생");
			e.printStackTrace();

		}

	}// searchAllCpEmp

	public static void main(String[] args) {
		RunCallableStatementDAO rpsDAO = new RunCallableStatementDAO();
		rpsDAO.menu();
	}// main

}// class
