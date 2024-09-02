package day0902;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RunCarSearch {

	public void carSearch() {

		String maker = JOptionPane.showInputDialog("제조사를 입력");

		if (maker != null && !maker.isEmpty()) {
			CarSearchDAO csDAO = CarSearchDAO.getInstance();

			try {
				List<CarVO> listCar = csDAO.selectMaker(maker);

				StringBuilder output = new StringBuilder();
				output.append("번호\t제조국\t제조사\t모델명\t연식\t가격\t옵션\n").append(
						"---------------------------------------------------------------------------------------------------------------------------------------\n");

				if (listCar.isEmpty()) {
					output.append("보유중인 차량이 없습니다. 제조사를 확인하세요");
				}

				int cnt = 1;
				for (CarVO cVO : listCar) {

					output.append(cnt).append("\t");
					cnt += 1;
					output.append(cVO.getCountry()).append("\t");
					output.append(cVO.getMaker()).append("\t");
					output.append(cVO.getModel()).append("\t");
					output.append(cVO.getCar_year()).append("\t");
					output.append(cVO.getPrice()).append("\t");
					output.append(cVO.getCar_option()).append("\n");
				}
				output.append(
						"---------------------------------------------------------------------------------------------------------------------------------------\n");
				JTextArea jta = new JTextArea(output.toString(), 20, 60);
				JScrollPane jsp = new JScrollPane(jta);
				JOptionPane.showMessageDialog(null, jsp);

			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}

	public static void main(String[] args) {

		new RunCarSearch().carSearch();

	}

}
