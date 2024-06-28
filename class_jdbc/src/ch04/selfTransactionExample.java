package ch04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class selfTransactionExample {

	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/m_board?serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "asd123";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, user, password)) {

				conn.setAutoCommit(false);

				String sqlInsert = " INSERT INTO reply(userId, boardId, content, createDate) "
						+ "	values(?,?,?,now()); ";

				PreparedStatement psmt1 = conn.prepareStatement(sqlInsert);
				psmt1.setInt(1, 5);
				psmt1.setInt(2, 2);
				psmt1.setString(3, "새로운 답글");

				String sqlDelete = " delete FROM reply WHERE id = ? ";
				PreparedStatement psmt2 = conn.prepareStatement(sqlDelete);
				psmt2.setInt(1, 8);
				
				int rowInsertCount = psmt1.executeUpdate();
				int rowDeleteCount = psmt2.executeUpdate();
				
				System.out.println("rowInsertCount : " + rowInsertCount);
				System.out.println("rowDeleteCount : " + rowDeleteCount);
				conn.commit();
			} catch (SQLException e) {

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
