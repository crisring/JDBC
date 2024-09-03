package day0902sub;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kr.co.sist.vo.SchemaVO;

public class RunTableSchema {

	public void searchTable() {
		TableSchemaDAO tsDAO = TableSchemaDAO.getInstance();
		List<String> listTable;

		try {
			listTable = tsDAO.selectAllTable();

			StringBuilder outputTab = new StringBuilder();

			for (String str : listTable) { // 화면처리
				outputTab.append(str);
			}

			String tab = JOptionPane.showInputDialog("테이블명\n" + outputTab); // 문자열 출력

			if (tab.isEmpty()) {
				JOptionPane.showMessageDialog(null, "테이블명을 입력해주세요");
				return;
			} // end if

			tab = tab.toUpperCase();

			StringBuilder schemaTab = new StringBuilder();
			List<SchemaVO> schemaTable = tsDAO.selectTableSchema(tab);

			schemaTab.append("테이블 정보가 존재하지 않습니다.");
			if (!schemaTable.isEmpty()) {
				schemaTab.delete(0, schemaTab.length());
				schemaTab.append(tab).append(tab + "의 Relation Schema 정보").append("\n");
				return;
			} // end if

			for (SchemaVO sVO : schemaTable) {
				schemaTab.append("컬럼명: " + sVO.getColumnName()).append("\n").append("컬럼 타입: " + sVO.getColumnTypeName())
						.append("\n") // 컬럼 타입
						.append("크기: " + sVO.getDataSize()).append("\n") // 크기
						.append("null 허용 여부: " + sVO.getIsNullable());
			} // end for

			JTextArea jta = new JTextArea(schemaTab.toString(), 6, 40);
			JScrollPane jsp = new JScrollPane(jta);
			JOptionPane.showMessageDialog(null, jsp);

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch

	}// searchTable

	public static void main(String[] args) {
		RunTableSchema rts = new RunTableSchema();
		rts.searchTable();
	}// main

}// class
