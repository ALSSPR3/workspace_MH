package ch03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectExample {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/mydb2?serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "asd123";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url,user,password);
			
			String query = "SELECT * FROM employee";
			
			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				System.out.println("Id : " + resultSet.getInt(1));
				System.out.println("name : " + resultSet.getString(2));
				System.out.println("department : " + resultSet.getString(3));
				System.out.println("salary : " + resultSet.getBigDecimal(4));
				System.out.println("hire_date : " + resultSet.getDate(5));
				System.out.println("------------------------------");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}