package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Http_Server {

	public static void main(String[] args) throws IOException {
		
		HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
		
		httpServer.createContext("/test", new MyTestHandler());
		
		httpServer.start();
		System.out.println(">> My HTTP Server started on port 8080 <<");
	}

	static class MyTestHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String method = exchange.getRequestMethod();

			if ("GET".equalsIgnoreCase(method)) {
				handleGetRequest(exchange);
			}
		}

		private void handleGetRequest(HttpExchange exchange) throws IOException {
			String urlString = "https://jsonplaceholder.typicode.com/todos/1";

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedReader brIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputStr;
			String response = null;
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			while ((inputStr = brIn.readLine()) != null) {
				response = gson.toJson(inputStr);
			}
			brIn.close();
			OutputStream os = exchange.getResponseBody();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(response);
			osw.flush();
			os.close();
		}
	}
}
