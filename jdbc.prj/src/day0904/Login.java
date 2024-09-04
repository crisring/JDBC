package day0904;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.vo.DbConnection;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {

	private JTextField jtfId;
	private JPasswordField jpfPass;
	private JLabel jlOutput;

	public Login() {
		super("로그인");
		jtfId = new JTextField();
		jpfPass = new JPasswordField();
		jlOutput = new JLabel("결과");

		jtfId.setBorder(new TitledBorder("아이디"));
		jpfPass.setBorder(new TitledBorder("비밀번호"));
		jlOutput.setBorder(new TitledBorder("로그인 결과"));

		jtfId.addActionListener(this);
		jpfPass.addActionListener(this);

		setLayout(new GridLayout(3, 1));

		add(jtfId);
		add(jpfPass);
		add(jlOutput);

		setBounds(100, 100, 400, 250);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		chkNull();
	}// actionPerformed

	private void chkNull() {
		String id = jtfId.getText().trim();

		if (id.isEmpty()) {
			JOptionPane.showMessageDialog(this, "아이디는 필수 입력!!");
			jtfId.requestFocus();
			return;
		} // end if

		String pass = new String(jpfPass.getPassword()).trim();
		if (pass.isEmpty()) {
			JOptionPane.showMessageDialog(this, "비밀번호는 필수 입력!!");
			jpfPass.requestFocus();
			return;
		} // end if

		try {
			// loginProcess(id, pass); statement : SQLInjection 발생
			preparedLoginProcess(id, pass); // 알아서 방어됨
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "문제발생 잠시후 다시시도!");
			e.printStackTrace();
		} // end if
	}// chkNull

	/**
	 * SQLInjection이 발생하지 않는다.
	 * 
	 * @param id
	 * @param password
	 * @throws SQLException
	 */
	private void preparedLoginProcess(String id, String password) throws SQLException {

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dbCon.getConn();
			String query = "select name,email from test_member where id=? and pass=?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, id);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			if (!rs.next()) {
				JOptionPane.showMessageDialog(this, "아이디나 비밀번호를 확인하세요.");
				return;
			} // end if

			String name = rs.getString("name");
			String email = rs.getString("email");
			setTitle(name + "님 로그인하셨습니다.");

			jtfId.setText("");
			jpfPass.setText("");
			jlOutput.setText(name + "(" + email + ")님 안녕하세요?");

		} finally {

			dbCon.dbClose(rs, pstmt, con);
		}

	}

	private void loginProcess(String id, String password) throws SQLException {

		DbConnection dbCon = DbConnection.getInstance();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			con = dbCon.getConn();
			stmt = con.createStatement();

			StringBuilder select = new StringBuilder();
			select.append("select name,email").append(" from test_member").append(" where id='")
					.append(blockInjection(id)).append("' and pass='").append(blockInjection(password)).append("'");

			rs = stmt.executeQuery(select.toString());
			if (!rs.next()) {
				JOptionPane.showMessageDialog(this, "아이디나 비밀번호를 확인하세요.");
				return;
			} // end if

			String name = rs.getString("name");
			String email = rs.getString("email");
			setTitle(name + "님 로그인하셨습니다.");

			jtfId.setText("");
			jpfPass.setText("");
			jlOutput.setText(name + "(" + email + ")님 안녕하세요?");

			// id : 'or 1=1--

		} finally {

			dbCon.dbClose(rs, null, con);

			if (stmt != null) {
				stmt.close();
			}
		}

	}// loginProcess

	/**
	 * 입력값에 ', =, 공백, 쿼리문(select, insert, update)에 대한 부분을 제거한다.
	 * 
	 * @param str
	 * @return
	 */
	private String blockInjection(String str) {
		String temp = str;

		if (temp != null && !temp.isEmpty()) {
			temp = temp.replaceAll("'", "").replaceAll("=", "").replaceAll(" ", "").replaceAll("select", "")
					.replaceAll("insert", "").replaceAll("update", "");
		} // end if

		return temp;
	}// blockInjection

	public static void main(String[] args) {
		new Login();
	}// main

}// class
