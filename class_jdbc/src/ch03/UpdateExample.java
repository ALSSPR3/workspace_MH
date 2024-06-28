package ch03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateExample {

	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/mydb2?serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "asd123";

		// Connection 객체를 얻어서 insert 구문을 직접 만들어 보세요.
		// mydb2 , employee 테이블에 값을 넣는 코드를 작성하세요.

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(url, user, password);

			String sqlQeury = "update employee set salary = ? where id = ?";

			preparedStatement = connection.prepareStatement(sqlQeury);
			preparedStatement.setDouble(1, 4000000.00);
			preparedStatement.setInt(2, 8);
			int rowCount = preparedStatement.executeUpdate();
			System.out.println("rowCount : " + rowCount);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
