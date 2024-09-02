package day0830;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kr.co.sist.vo.CpEmpVO;

public class RunPreparedStatementDAO {

	public static final String ADD_EMP = "1";
	public static final String MODIFY_EMP = "2";
	public static final String REMOVE_EMP = "3";
	public static final String SEARCH_ONE_EMP = "4";
	public static final String SEARCH_ALL_EMP = "5";
	public static final String EXIT_PROG = "6";

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
			selectedMenu = JOptionPane.showInputDialog("사원관리\n1.사원추가 2.사원변경 3.사원삭제 4.사원조회 5.모든사원조회 6.종료\n번호를 입력해주세요.");
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
		String tempData = JOptionPane.showInputDialog("사원정보입력\n 입력 예)사원번호,사원명,직무,매니저번호,연봉,보너스,부서번호\n의 형식으로 입력해주세요.");
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
				PreparedStatementDAO psDAO = PreparedStatementDAO.getInstance();
				psDAO.insertCpEmp(ceVO);// 성공
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

		String tempData = JOptionPane.showInputDialog("사원정보수정\n 입력 예)사원번호,직무,매니저번호,연봉,보너스,부서번호\n의 형식으로 입력해주세요.");
		if (tempData != null && !tempData.isEmpty()) {
			tempData = tempData.replaceAll(" ", "");
			String[] inputData = tempData.split(",");

			if (inputData.length != 6) {
				JOptionPane.showMessageDialog(null, "입력형식이 올바르지 않습니다.");
				return;
			} // end if

			try {
				// 입력된 데이터를 VO에 저장
				CpEmpVO ceVO = new CpEmpVO(parseInt(inputData[EMPNO]), parseInt(inputData[2]), parseInt(inputData[5]),
						parseDouble(inputData[3]), parseDouble(inputData[4]), "", inputData[1]);
//			사원번호,직무,매니저번호,연봉,보너스,부서번호
				// DB작업을 수행
				PreparedStatementDAO psDAO = PreparedStatementDAO.getInstance();

				int rowCnt = psDAO.updateCpEmp(ceVO);// 성공

				String resultMsg = "사원번호가 존재하지 않습니다.\n사원번호를 확인해주세요.";
				if (rowCnt != 0) {
					resultMsg = "사원정보를 변경하였습니다.";
				} // end if

				JOptionPane.showMessageDialog(null, inputData[EMPNO] + resultMsg);

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "사원번호,매니저번호,부서번호,연봉,보너스는 숫자로만 입력할 수 있습니다.");
			} catch (SQLException se) {// 예외
				// Statement의 update를 참조
				se.printStackTrace();
				JOptionPane.showMessageDialog(null, "문제 발생");
			} // end catch

		} // end if

	}// modifiyCpEmp

	public void removeCpEmp() {
		// 사원번호를 입력받아서 해당 사원을 삭제
		try {

			int empno = parseInt(JOptionPane.showInputDialog("사원삭제\n사원번호를 입력해주세요."));

			PreparedStatementDAO pDAO = PreparedStatementDAO.getInstance();
			int rowCnt = pDAO.deleteCpEmp(empno);

			String msg = "번 사원정보가 삭제되지 않았습니다. 사원번호를 확인해주세요.";

			if (rowCnt != 0) {
				msg = "사원정보가 삭제 되었습니다.";
			}
			JOptionPane.showMessageDialog(null, empno + msg);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "삭제 중 문제 발생하였습니다!!");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "사원번호는 숫자입니다!!");
			e.printStackTrace();
		}

	}// removeCpEmp

	public void searchOneCpEmp() {

		// 사원번호를 입력바아서 해당사원 정보를 조회하여 출력하는일
		// 사원번호를 입력받아서 해당 사원을 삭제
		try {

			int empno = parseInt(JOptionPane.showInputDialog("사원삭제\n사원번호를 입력해주세요."));

			PreparedStatementDAO pDAO = PreparedStatementDAO.getInstance();
			CpEmpVO ceVO = pDAO.selectOneCpEmp(empno);

			StringBuilder output = new StringBuilder();
			output.append(empno).append("번 사원정보가 검색되지 않았습니다. 사원번호를 확인해주세요.");
			DecimalFormat df = new DecimalFormat("#,###");

			if (ceVO != null) {
				output.delete(0, output.length());
				output.append(empno).append("번 사원정보 검색 결과\n").append("사원명 : ").append(ceVO.getEname()).append("\n")
						.append("직무 : ").append(ceVO.getJob()).append("\n").append("매니저번호 : ").append(ceVO.getMgr())
						.append("\n").append("입사일 : ").append(ceVO.getHiredate()).append("\n").append("연봉 : ")
						.append(df.format(ceVO.getSal())).append("\n").append("보너스 : ")
						.append(df.format(ceVO.getComm())).append("\n").append("부서번호 : ")
						.append(df.format(ceVO.getDeptno())).append("\n");
			}
			JOptionPane.showMessageDialog(null, output.toString());

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
		PreparedStatementDAO psDAO = PreparedStatementDAO.getInstance();

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

			JTextArea jta = new JTextArea(output.toString(), 6, 40);
			JScrollPane jsp = new JScrollPane(jta);
			JOptionPane.showMessageDialog(null, jsp);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "검색 작업 중 문제 발생");
			e.printStackTrace();

		}

	}// searchAllCpEmp

	public static void main(String[] args) {
		RunPreparedStatementDAO rpsDAO = new RunPreparedStatementDAO();
		rpsDAO.menu();
	}// main

}// class
