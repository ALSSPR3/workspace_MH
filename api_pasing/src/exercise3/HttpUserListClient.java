package exercise3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class HttpUserListClient {

	public static void main(String[] args) {

		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/users");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			int response = conn.getResponseCode();
			System.out.println("response Code : " + response);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;
			StringBuffer buffer = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine);
			}
			System.out.println(buffer);
			
			in.close();
			Type userType = new TypeToken<List<User>>() {
			}.getType();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			List<User> userList = gson.fromJson(buffer.toString(), userType);

			System.out.println(userList.size());
			System.out.println("----------------------------");
			for (User user : userList) {
				System.out.println(user.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
