package ch01.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// DTO 설계 하고
// 값을 담아서 . 연산자를 사용해 보시오.
public class Employee {

	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/mydb2?serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "asd123";

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection connection = DriverManager.getConnection(url, user, password);

			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");

			while (resultSet.next()) {
				System.out.println("User Id : " + resultSet.getInt("id"));
				System.out.println("User Name : " + resultSet.getString("name"));
				System.out.println("department : " + resultSet.getString("department"));
				System.out.println("---------------------------------");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
