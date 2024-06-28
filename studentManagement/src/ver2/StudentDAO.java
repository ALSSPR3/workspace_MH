package ver2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import ver2.model.StudentDTO;

// 물론 기능 설계는 인터페이스를 먼저 작성하고 구현 클래스를 만드는 것이 좋다.
public class StudentDAO {

	// 학생 정보 추가 기능 만들기
	public void addStudent(StudentDTO dto) throws SQLException {
		String query = " INSERT INTO students(name, age, email) VALUES (?, ?, ?) ";
		try (Connection conn = DBConnationManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getAge());
			pstmt.setString(3, dto.getEmail());
			pstmt.executeUpdate();
		}
	}

	// 학생의 아이디로 조회하는 기능 만들기, (id)
	public StudentDTO getStudentById(int id) throws SQLException {
		String query = " SELECT * FROM students WHERE id = ? ";
		try (Connection conn = DBConnationManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new StudentDTO(rs.getInt("id"), rs.getString("name"), rs.getInt("age"),
							rs.getString("email"));
				}
			}
		}
		return null;
	}

	// 학생 전체 조회 기능
	public List<StudentDTO> getAllStudents() throws SQLException {
		// tip - 리스트라면 무조건 리스트를 생성하고 코드 작성
		List<StudentDTO> list = new ArrayList<>();
		String query = " SELECT * FROM students ";
		try (Connection conn = DBConnationManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				StudentDTO dto = new StudentDTO().builder().id(rs.getInt("id")).name(rs.getString("name"))
						.age(rs.getInt("age")).email(rs.getString("email")).build();
				list.add(dto);
			}
		}
		return list;
	}

	// 학생 정보 수정 하기
	public void updateStudent(StudentDTO dto, String name) throws SQLException {
		String query = " UPDATE students SET name = ?, age = ?, email = ? WHERE name = ? ";
		try (Connection conn = DBConnationManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getAge());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, name);
			pstmt.executeUpdate();

		}
	}

	// 학생 정보 삭제하기
	public void deleteStudent(int id) throws SQLException {
		String query = " DELETE FROM students WHERE id = ? ";

		try (Connection conn = DBConnationManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}

	}
}
