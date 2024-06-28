package ver2;

import java.sql.SQLException;
import java.util.List;

import ver2.model.StudentDTO;

public class studentManagementSystem {

	private static final StudentDAO STUDENT_DAO = new StudentDAO();

	public static void main(String[] args) {
		// 사용자에게 보여주는 부분
		try {

			List<StudentDTO> list = STUDENT_DAO.getAllStudents();
			System.out.println(list.size());
			System.out.println(list.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}