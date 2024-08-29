package dept_prj;

import java.sql.SQLException;

public class Dept_Run {

	public static void main(String[] args) {

		Dept dept = new Dept();

		String dname = "";
		String loc = "DALLAS";

		try {
			dept.search(loc, dname);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}