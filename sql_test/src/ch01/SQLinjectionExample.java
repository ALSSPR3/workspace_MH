package ch01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SQLinjectionExample {

	public static void main(String[] args) {

		try (Connection conn = DBConnectionManager.getConnection()) {

			Scanner scanner = new Scanner(System.in);
			System.out.print("사용자 이름을 입력하세요 : ");
			String username = scanner.nextLine();

			// 취약한 SQL 쿼리 작성해보기(SQL 인젝션이 가능하다.)
			String query = " SELECT * FROM users WHERE name = " + username + " ";

			try (Statement stmt = conn.createStatement(); PreparedStatement pstmt = conn.prepareStatement(query)) {
				// resultSet = stmt.executeQuery(query);
				pstmt.setString(1, username);
				ResultSet resultSet = pstmt.executeQuery();

				while (resultSet.next()) {
					System.out.println("사용자 ID : " + resultSet.getInt("id"));
					System.out.println("사용자 이름 : " + resultSet.getString("name"));
					System.out.println("사용자 이메일 : " + resultSet.getString("email"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // end of main

}
