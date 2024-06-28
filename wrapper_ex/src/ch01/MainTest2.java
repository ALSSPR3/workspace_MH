package ch01;

public class MainTest2 {

	public static void main(String[] args) {

		String str1 = "10A";
		String str2 = "20.5";
		String str3 = "true";

		System.out.println(str2 + "A");
		// str1 <-- 10

		// 문자열에 데이터 타입을 정수값으로 변경하는 방법

		// 예외처리
		try {
			int n1 = Integer.parseInt(str1);
			System.out.println(n1);
		} catch (Exception e) {
			System.out.println("잘못된 입력 값입니다.");
		}

		// str2 ----> double ---> 8byte
		double d = Double.parseDouble(str2);
		System.out.println(d);

		// str3 ---> boolean --> 2byte
		boolean b = Boolean.parseBoolean(str3);
		System.out.println(b);

		// 반대로 ---> int값을 ---> String 데이터 타입으로 변환 하고 싶다면
		int number = 10000;
		String numberStr = String.valueOf(number);
		System.out.println(numberStr);

	}

}
