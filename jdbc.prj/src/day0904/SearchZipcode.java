package day0904;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.vo.DbConnection;
import kr.co.sist.vo.ZipcodeVO;

@SuppressWarnings("serial")
public class SearchZipcode extends JFrame implements ActionListener {

	private JTextField jtfDong;
	private DefaultTableModel dtm;
	private JTable jt;
	private JButton jbtnSearch;

	public SearchZipcode() {
		super("우편번호 검색");
		jtfDong = new JTextField(10);

		JLabel jlDong = new JLabel("동이름)");
		jbtnSearch = new JButton("검색");

		JPanel jpNorth = new JPanel();
		jpNorth.add(jlDong);
		jpNorth.add(jtfDong);
		jpNorth.add(jbtnSearch);

		String[] columnNames = { "우편번호", "주소" };
		dtm = new DefaultTableModel(columnNames, 0);
		jt = new JTable(dtm);

		jt.getColumnModel().getColumn(0).setPreferredWidth(80);
		jt.getColumnModel().getColumn(1).setPreferredWidth(520);

		jt.setRowHeight(23);

		JScrollPane jsp = new JScrollPane(jt);
		jsp.setBorder(new TitledBorder("검색결과"));

		add("North", jpNorth);
		add("Center", jsp);

		jbtnSearch.addActionListener(this);
		jtfDong.addActionListener(this);

		setBounds(100, 100, 600, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}// SearchZipcode

	@Override
	public void actionPerformed(ActionEvent ae) {

		String dong = jtfDong.getText().trim();

		if (!dong.isEmpty()) {
			setZipcode(dong);
			jtfDong.setText("");
		} // end if

	}// actionPerformed

	private void setZipcode(String dong) {

		try {
			List<ZipcodeVO> data = prepareSelectDong(dong);

			if (data.isEmpty()) {
				JOptionPane.showMessageDialog(this, dong + "이름을 확인하세요.");
				return;
			} // end if

			dtm.setRowCount(0); // 테이블을 초기화 MVV 패턴
			ZipcodeVO zVO = null;
			String[] tempZipcode = null;
			StringBuilder tempAddr = new StringBuilder();
			for (int i = 0; i < data.size(); i++) {
				zVO = data.get(i);

				tempAddr.append(zVO.getSide()).append(" ").append(zVO.getGugun()).append(" ").append(zVO.getDong())
						.append(" ").append(zVO.getBunji());

				tempZipcode = new String[2];
				tempZipcode[0] = zVO.getZipcode();
				tempZipcode[1] = tempAddr.toString();

				// JTable에 설정
				dtm.addRow(tempZipcode);
				tempAddr.delete(0, tempAddr.length());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch

	}// setZipcode

	private List<ZipcodeVO> prepareSelectDong(String dong) throws SQLException {
		List<ZipcodeVO> list = new ArrayList<ZipcodeVO>();

		DbConnection dbCon = DbConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null; // 바인드에 테이블 이름 못씀
		ResultSet rs = null;

		try {

			String selectZipcode = "select zipcode, sido, gugun, dong, nvl(bunji,' ')bunji from zipcode where dong like ?||'%'";
			pstmt = con.prepareStatement(selectZipcode);

			pstmt.setString(1, dong);
			rs = pstmt.executeQuery();

			ZipcodeVO zVO = null;
			while (rs.next()) {
				zVO = new ZipcodeVO(rs.getString("zipcode"), rs.getString("sido"), rs.getString("gugun"),
						rs.getString("dong"), rs.getString("bunji"));

				list.add(zVO);
			}

		} finally {
			dbCon.dbClose(rs, null, con);

		}

		return list;
	}

	private List<ZipcodeVO> selectDong(String dong) throws SQLException {
		List<ZipcodeVO> list = new ArrayList<ZipcodeVO>();

		DbConnection dbCon = DbConnection.getInstance();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			con = dbCon.getConn();

			StringBuilder selectZipcode = new StringBuilder();
			stmt = con.createStatement();
			selectZipcode.append("	select zipcode, sido, gugun, dong, nvl(bunji,' ')bunji	")
					.append("	from zipcode	").append("	where dong like '").append(blockInjection(dong))
					.append("%'");

			rs = stmt.executeQuery(selectZipcode.toString());

			ZipcodeVO zVO = null;
			while (rs.next()) {
				zVO = new ZipcodeVO(rs.getString("zipcode"), rs.getString("sido"), rs.getString("gugun"),
						rs.getString("dong"), rs.getString("bunji"));

				list.add(zVO);
			}

		} finally {
			dbCon.dbClose(rs, null, con);

			if (stmt != null) {
				stmt.close();
			}
		}

		return list;
	}// selectDong

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
					.replaceAll("union", "").replaceAll("update", "");
		} // end if

		return temp;
	}// blockInjection

	public static void main(String[] args) {
		new SearchZipcode();
	}// main

}// class
