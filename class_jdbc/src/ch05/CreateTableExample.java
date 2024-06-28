package ch05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTableExample {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/demo3?serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "asd123";
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url,user,password);
			conn.setAutoCommit(false);
			
			String sql = " CREATE TABLE player ("
					+ " id INT AUTO_INCREMENT PRIMARY KEY, "
					+ " name VARCHAR(100) NOT NULL, "
					+ " job VARCHAR(100) NOT NULL "
					+ " ) ";
			
			PreparedStatement psmt = conn.prepareStatement(sql);
			
			psmt.addBatch();
			
			psmt.execute();
			
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
