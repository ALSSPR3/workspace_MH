package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Http_Client {

	public static void main(String[] args) throws IOException {
		String urlString = "https://jsonplaceholder.typicode.com/todos/1";

		URL url = new URL(urlString);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		BufferedReader brIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String inputStr;
		StringBuffer responseBuffer = new StringBuffer();

		while ((inputStr = brIn.readLine()) != null) {
			responseBuffer.append(inputStr);
		}

		brIn.close();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Jsonplace toStr = gson.fromJson(responseBuffer.toString(), Jsonplace.class);
		System.out.println(toStr);

	}
}
