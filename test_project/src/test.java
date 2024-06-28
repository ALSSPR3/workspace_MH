import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;

public class test {

	public static void main(String[] args) {

		String query = " INSERT INTO item (product_name, price, state, date, product_info, user_num, category_id) "
				+ "	VALUES(?, ?, ?, now(), ?, ?, ?) ";
		String bImagequery = " INSERT INTO original_item_image (product_id, image, image_num) VALUES(?, ?, ?) ";
		String sImagequery = " INSERT INTO scaled_item_image (product_id, image, image_num) VALUES(?, ?, ?) ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement userPstmt = conn.prepareStatement(query);
				PreparedStatement bIamgePstmt = conn.prepareStatement(bImagequery);
				PreparedStatement sImagePstmt = conn.prepareStatement(sImagequery)) {

			for (int i = 0; i < 50; i++) {
				userPstmt.setString(1, productName);
				userPstmt.setString(2, price);
				userPstmt.setString(3, state);
				userPstmt.setString(4, content);
				userPstmt.setInt(5, myUserNum);
				userPstmt.setInt(6, categoryId);
				userPstmt.executeUpdate();

				for (int j = 0; j < 3; j++) {
					bIamgePstmt.setInt(1, product_id);
					for (int k = 0; k < 3; k++) {
						bIamgePstmt.setBytes(2, image[k]);
						bIamgePstmt.setInt(3, num + 1);
						bIamgePstmt.executeUpdate();

						sImagePstmt.setInt(1, product_id);
						sImagePstmt.setBytes(2, image[k]);
						sImagePstmt.setInt(3, num + 1);
						sImagePstmt.executeUpdate();
					}
				}
				rowCount++;
			}
			System.out.println("Total rows inserted: " + rowCount);
		}
	}
}
