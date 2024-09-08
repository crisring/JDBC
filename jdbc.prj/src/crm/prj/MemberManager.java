package crm.prj;

import javax.swing.JFrame;

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

	private static final int MemberList = 1;
	private static final int insertMember = 2;
	private static final int updateMember = 3;
	private static final int deleteMember = 4;

	/**
	 * SerialVersion 설정
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 메뉴를 불러오는 method
	 */
	public void readMenu() {
		setTitle("회원 관리 프로그램");
		setSize(800, 700);
		setVisible(true);
	}

	public static void main(String[] args) {
		MemberManager mm = new MemberManager();

		mm.readMenu();
	}

}
