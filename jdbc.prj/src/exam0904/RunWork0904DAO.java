package exam0904;

import static java.lang.Integer.parseInt;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RunWork0904DAO {

	// 컬럼명
	public static final int name = 0;
	public static final int email = 1;
	public static final int phone_number = 2;
	public static final int seq = 3;

	// 메뉴 선택
	public static final String ADD_WORK0904 = "1";
	public static final String SEARCH_ALL_WORK0904 = "2";
	public static final String EXIT_PROG = "3";

	public void menu() {

		boolean exitFlag = false;
		String selectedMenu = "0";

		do {
			selectedMenu = JOptionPane.showInputDialog("회원관리\n1.사원추가 2.모든사원조회 3.종료");
			if (selectedMenu != null) {
				switch (selectedMenu) {
				case ADD_WORK0904:
					addWork0904();
					break;
				case SEARCH_ALL_WORK0904:
					searchAllWork0904();
					break;
				case EXIT_PROG:
					switch (JOptionPane.showConfirmDialog(null, "종료하시겠습니까?")) {
					case JOptionPane.OK_OPTION:
						exitFlag = true;
						break;
					}// end switch
				}// end switch
			} else {
				exitFlag = true;
			} // end if
		} while (!exitFlag);
	}// menu

	public void addWork0904() {
		String tempData = JOptionPane.showInputDialog("회원정보입력\n 입력 예)회원명,이메일,전화번호,우편번호\n의 형식으로 입력");
		if (tempData != null && !tempData.isEmpty()) {
			tempData = tempData.replaceAll(" ", "");
			String[] inputData = tempData.split(",");

			if (inputData.length != 4) {
				JOptionPane.showMessageDialog(null, "입력형식이 올바르지 않습니다.");
				return;
			} // end if

			try {
				// 입력된 데이터를 VO에 저장
				Work0904VO wVO = new Work0904VO(inputData[name], inputData[email], inputData[phone_number],
						parseInt(inputData[seq]));

				// DB작업을 수행
				Work0904DAO eDAO = Work0904DAO.getInstance();
				eDAO.insertWork0904(wVO.getName(), wVO.getEmail(), wVO.getPhone_Number(), wVO.getSeq());// 성공
				JOptionPane.showMessageDialog(null, inputData[name] + "의 회원정보가 추가되었습니다.");

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "우편번호는 숫자로만 입력할 수 있습니다.");
			} catch (SQLException se) {// 예외
				se.printStackTrace();
				JOptionPane.showMessageDialog(null, "문제 발생");
			} // end catch

		} // end if
	}// addCpEmp

	public void searchAllWork0904() {

		// 모든 사원 정보를 검색하여 출력하는 일
		Work0904DAO wDAO = Work0904DAO.getInstance();

		try {
			List<Work0904VO> list = wDAO.selectAllWork0904();
			// List<Work0904VO> list = wDAO.callAbleSelectAllWork0904(); //
			// callablestatement 사용

			StringBuilder output = new StringBuilder();
			// num, name, email, phone_number, seq, input_date
			output.append("회원번호\t회원명\t이메일\t전화번호\t우편번호\t입력일\n").append(
					"------------------------------------------------------------------------------------------------------\n");

			if (list.isEmpty()) {
				output.append("회원정보가 존재하지 않습니다.");
			}

			for (Work0904VO wVO : list) {
				output.append(wVO.getNum()).append("\t");
				output.append(wVO.getName()).append("   ");
				output.append(wVO.getEmail()).append("     ");
				output.append(wVO.getPhone_Number()).append("\t");
				output.append(wVO.getSeq()).append("\t");
				output.append(wVO.getInput_date()).append("\n");
			}
			output.append(
					"------------------------------------------------------------------------------------------------------\n");

			output.append("총 회원수 : " + list.size()).append("\n");

			JTextArea jta = new JTextArea(output.toString(), 8, 60);
			JScrollPane jsp = new JScrollPane(jta);
			JOptionPane.showMessageDialog(null, jsp);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "검색 작업 중 문제 발생");
			e.printStackTrace();

		}
	}// searchAllWork0904

	public static void main(String[] args) {

		RunWork0904DAO reDAO = new RunWork0904DAO();

		reDAO.menu();
	}// main
}// class
