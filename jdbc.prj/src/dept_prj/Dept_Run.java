package dept_prj;

import java.sql.SQLException;

public class Dept_Run {

	public void searchDEPT() {

		Dept dept = new Dept();

		String dname = "";
		String loc = "DALLAS";

		try {
			System.out.println(dept.selectDEPT(loc, dname));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		Dept_Run drun = new Dept_Run();
		drun.searchDEPT();

	}

}