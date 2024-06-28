package ch03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteExample {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/mydb2?serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "asd123";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection = DriverManager.getConnection(url, user, password);
			
			String query = "DELETE FROM employee WHERE id = ?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, 8);
			
			int rowCount = preparedStatement.executeUpdate();
			
			System.out.println("rowCount : " + rowCount);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
