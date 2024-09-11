package crm.prj;

import static java.lang.Integer.parseInt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import exam0904.Work0904DAO;
import exam0904.Work0904VO;

/**
 * MemberDAO 와 사용자가 키보드를 통해 입력한 데이터를 읽어들이는 입력 스트림을 멤버변수(필드)로 선언하고, <br>
 * 멤버번수를 초기화하는 생성자를 작성한다.<br>
 * 
 * 무한루프를 돌면서 사용자가 입력한 메뉴 정보를 읽어들이는 readMenu() 메소드를 구현한다. <br>
 * readMenu() 메소드에서는 사용자가 입력한 숫자에 따라 다음과 같이 분기처리한다.
 * 
 * 선택한 번호마다 실행할 기능(호출할 메소드) <br>
 * 1 회원 목록 (getMemberList()) <br>
 * 2 회원 등록 (insertMember()) <br>
 * 3 회원 수정 (updateMember()) <br>
 * 4 회원 삭제 (deleteMember()) <br>
 * 0 프로그램 종료
 * 
 */

public class MemberManager extends JFrame {

	private static final long serialVersionUID = 4894379414249904372L;

	private static final int MemberList = 1;
	private static final int insertMember = 2;
	private static final int updateMember = 3;
	private static final int deleteMember = 4;

	private static final int Member_ID = 0;
	private static final int Name = 1;
	private static final int Phone_Number = 2;

	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * 메뉴를 불러오는 method
	 */
	public void readMenu() {
		setTitle("회원 관리 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setLayout(new BorderLayout());

		// 테이블 설정 (아이디, 이름, 전화번호 열)
		String[] columnNames = { "아이디", "이름", "전화번호" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		// 버튼 설정 (회원 등록 버튼)
		JButton registerButton = new JButton("회원 등록");
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		// 하단 패널에 버튼 추가
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(registerButton);

		// 컴포넌트 추가
		add(scrollPane, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		setVisible(true);

	}

	private void MemberList() {

	}

	// 회원 추가
	private void addMember() throws SQLException {

		StringBuilder output = new StringBuilder();
		output.append("회원 추가").append("\n").append("아이디,이름,전화번호(010-xxxx-xxxx) 순으로 입력해주세요.");

		String tempData = JOptionPane.showInputDialog(output);

		if (tempData != null && !tempData.isEmpty()) {
			tempData = tempData.replaceAll(" ", "");
			String[] inputData = tempData.split(",");
			if (inputData.length != 3) {
				JOptionPane.showMessageDialog(null, "입력형식이 올바르지 않습니다.");
				return;
			} // end if

			try {
				// 입력된 데이터를 VO에 저장
				Member mVO = new Member(inputData[Member_ID], inputData[Name], inputData[Phone_Number]);

				// DB작업을 수행 -> 아이디, 이름, 전화번호
				MemberDAO mDAO = MemberDAO.getInstance();
				mDAO.insertMember(mVO.getMember_ID(), mVO.getName(), mVO.getPhone_Number());// 성공
				JOptionPane.showMessageDialog(null, inputData[Name] + "의 회원정보가 추가되었습니다.");

			} catch (SQLException se) {// 예외
				se.printStackTrace();
				JOptionPane.showMessageDialog(null, "문제 발생");
			} // end catch

		}
	}

	private void updateMember() {

	}

	private void deleteMember() {

	}

	public static void main(String[] args) {
		MemberManager mm = new MemberManager();

		try {
			mm.addMember();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
