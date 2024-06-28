package ch01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class selfAPIExplorer {

	public static void main(String[] args) throws IOException {

		// 순수 자바코드로 (클라이언트 측 코딩)
		// 준비물
		// 1. 서버측 주소 - 경로
		// http://localost:8080/test?name=홍길동&age=20
		// http://localost:8080/test?name=%ED%99%8D%EA%B8%B8%EB%8F%99&age=20
		StringBuilder urlBuilder = new StringBuilder(
				"http://api.odcloud.kr/api/15072220/v1/uddi:6dbf0a9b-2a31-4339-9aef-c112286787bd");

		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=qObhrT2smxd1uRFIZGGXPNll8nx8Yv7XQp5nXLzfYLxpBIkY5b%2FKzyv9ZL0%2FTmB3dKR7xh01kV5THW4RUcmUaw%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "="
				+ URLEncoder.encode("json", "UTF-8")); /* xml 또는 json */
		urlBuilder.append(
				"&" + URLEncoder.encode("page", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지 번호 */
		urlBuilder.append(
				"&" + URLEncoder.encode("perPage", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */

		// URL 객체에서 문자열 경로 넣어서 객체 생성
		// url.openConnection() 데이터 요청 보내기 - 설정하고

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET"); // 자원 요청
		conn.setRequestProperty("Content-type", "application/json");
		// 200, 실패 404, 405
		System.out.println("Response code: " + conn.getResponseCode());

		// 100 ~ 500 의미 (약속)
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

	}
}
