package exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exercise3.User;

public class HttpPostClient {

	public static void main(String[] args) {

		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/posts/1/comments");
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

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			User[] postDTD = gson.fromJson(buffer.toString(), User[].class);
			
			System.out.println(postDTD.length);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
