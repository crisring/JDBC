package exam0902;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class RunTableSchema {

	private String tableName;

	/**
	 * 모든 테이블 출력
	 * 
	 * @throws SQLException
	 */
	public RunTableSchema() throws SQLException {

		TableSchemaDAO tsDAO = TableSchemaDAO.getInstance();

		List<String> allTable = tsDAO.selectAllTable();

		StringBuilder tableNames = new StringBuilder();

		for (String table : allTable) {
			tableNames.append(table).append("\n");
		}

		tableName = JOptionPane.showInputDialog("테이블명\n" + tableNames);
		searchTable();

	}// RunTableSchema

	/**
	 * table 스키마 정보 출력
	 */
	public void searchTable() {
		TableSchemaDAO tsDAO = TableSchemaDAO.getInstance();

		try {
			List<SchemaVO> list = tsDAO.selectTableSchema(tableName);

			StringBuilder output = new StringBuilder();
			output.append("컬럼명\t데이터 형\t데이터 형 크기\tNULL허용여부")
					.append("\n----------------------------------------------------------------------------------\n");

			if (list.isEmpty()) {
				output.setLength(0);
				output.append(tableName + "정보가 존재하지 않습니다.");
				JOptionPane.showMessageDialog(null, output);
				return;
			}

			for (SchemaVO sVO : list) {

				output.append(sVO.getColumnName()).append("\t");
				output.append(sVO.getColumnLableName()).append("\t");
				output.append(sVO.getDataSize()).append("\t");
				output.append(sVO.getIsNuallalbe()).append("\n");

			}

			output.append("\n----------------------------------------------------------------------------------\n");

			JTextArea jta = new JTextArea(output.toString(), 6, 40);
			JOptionPane.showMessageDialog(null, jta);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "검색 작업 중 문제 발생");
			e.printStackTrace();

		}

	} // searchTable

	public static void main(String[] args) {

		try {
			new RunTableSchema();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// main

}// class
