package ver1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentsExample {

	private static final Logger LOGGER = Logger.getLogger(StudentsExample.class.getName());

	public static void main(String[] args) {

		try (Connection conn = DBConnectionManager.getConnection(); Scanner scanner = new Scanner(System.in)) {

			int choice = 0;

			while (true) {
				System.out.println("------ 학생 정보 시스템 ------");
				System.out.println("1. 학생 정보 추가");
				System.out.println("2. 학생 정보 전체 조회");
				System.out.println("3. 학생 정보 조건 조회");
				System.out.println("4. 학생 정보 수정");
				System.out.println("5. 학생 정보 삭제");
				System.out.println("6. 종료");
				System.out.print("번호를 선택하세요 : ");
				try {
					choice = scanner.nextInt();
				} catch (Exception e) {
					System.out.println("입력하신 값은 숫자가 아닙니다.");
				}

				if (choice == 1) {
					Insert(conn, scanner);
				} else if (choice == 2) {
					SelectAll(conn);
				} else if (choice == 3) {
					Select(conn, scanner);
				} else if (choice == 4) {
					Update(conn, scanner);
				} else if (choice == 5) {
					Delete(conn, scanner);
				} else if (choice == 6) {
					System.out.println("종료 합니다.");
					break;
				} else {
					System.out.println("올바른 숫자를 입력하세요.");
				}
			}

		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "MySQl 연결 오류");
			e.printStackTrace();
		}
	}

	private static void Update(Connection conn, Scanner scanner) {
		String selectQuery = " SELECT * FROM students WHERE name = ? and email = ? ";
		String query = " UPDATE students SET name = ?, age = ? , email = ? WHERE id = ? ";
		System.out.print("수정할 대상 이름 : ");
		String querybeforName = scanner.next();
		System.out.print("수정할 대상 이메일 : ");
		String querybeforEmail = scanner.next();
		System.out.print("수정할 이름 : ");
		String queryName = scanner.next();
		System.out.print("수정할 나이 : ");
		int queryAge = scanner.nextInt();
		System.out.print("수정할 이메일 : ");
		String queryEmail = scanner.next();
		System.out.print("수정 대상 아이디 : ");
		String queryId = scanner.next();
		try (PreparedStatement pstmt = conn.prepareStatement(selectQuery);
				PreparedStatement pstmt2 = conn.prepareStatement(query)) {

			pstmt.setString(1, querybeforName);
			pstmt.setString(2, querybeforEmail);
			ResultSet set = pstmt.executeQuery();
			if (set.next()) {

				pstmt2.setString(1, queryName);
				pstmt2.setInt(2, queryAge);
				pstmt2.setString(3, queryEmail);
				pstmt2.setString(4, queryId);
				int rowCount = pstmt2.executeUpdate();
				if (rowCount != 0) {
					System.out.println("변경된 row 값 : " + rowCount);
				}
			} else {
				System.out.println("일치하는 값이 없습니다.");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "UPDATE 오류 발생!");
			e.printStackTrace();
		}
	}

	private static void Insert(Connection conn, Scanner scanner) {
		String query = " INSERT INTO students(name, age, email) VALUES (?, ?, ?) ";

		System.out.print("학생 이름 : ");
		String queryName = scanner.next();
		System.out.print("학생 나이 : ");
		int queryAge = scanner.nextInt();
		System.out.print("학생 이메일 : ");
		String queryEmail = scanner.next();

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, queryName);
			pstmt.setInt(2, queryAge);
			pstmt.setString(3, queryEmail);

			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				System.out.println("변경된 row 값 : " + rowCount);
			} else {
				System.out.println("변경된 row 값이 없습니다.");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "INSERT 오류 발생!");
			e.printStackTrace();
		}
	}

	private static void SelectAll(Connection conn) {
		String query = " SELECT * FROM students ";

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				System.out.println("학생 ID : " + resultSet.getInt("id"));
				System.out.println("학생 이름 : " + resultSet.getString("name"));
				System.out.println("학생 나이 : " + resultSet.getInt("age"));
				System.out.println("학생 이메일 : " + resultSet.getString("email"));
				if (!resultSet.isLast()) {
					System.out.println("--------------------");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "SELECT 오류 발생!");
			e.printStackTrace();
		}
	}

	private static void Select(Connection conn, Scanner scanner) {
		System.out.print("검색할 대상을 입력하세요 : ");
		String queryWhere = scanner.next();
		System.out.print("대상의 값을 입력하세요 : ");
		String queryValue = scanner.next();
		String query = " SELECT * FROM students WHERE " + queryWhere + "= ? ";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, queryValue);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				System.out.println("학생 ID : " + resultSet.getInt("id"));
				System.out.println("학생 이름 : " + resultSet.getString("name"));
				System.out.println("학생 나이 : " + resultSet.getInt("age"));
				System.out.println("학생 이메일 : " + resultSet.getString("email"));
				if (!resultSet.isLast()) {
					System.out.println("--------------------");
				}
				System.out.println();
			}
			if (!resultSet.next()) {
				System.out.println("데이터가 없습니다 !");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "SELECT 오류 발생!");
			e.printStackTrace();
		}
	}

	private static void Delete(Connection conn, Scanner scanner) {
		String query = " DELETE FROM students WHERE id = ? ";

		System.out.print("삭제할 id : ");
		String queryDel = scanner.next();

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, queryDel);

			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				System.out.println("변경된 row 값 : " + rowCount);
			} else {
				System.out.println("변경된 row 값이 없습니다.");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "DELETE 오류 발생!");
			e.printStackTrace();
		}
	}
}
