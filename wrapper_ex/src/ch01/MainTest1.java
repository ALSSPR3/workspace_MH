package ch01;

public class MainTest1 {

	public static void main(String[] args) {
		
		// 박싱, 언박싱 이라는 용어를 이해하자.
		int num1 = 3; // ---> Integer
		Integer num2 = new Integer(3); // 박싱
		int num3 = num2.intValue(); // 언박싱
		
		System.out.println(num2);
		System.out.println(num3);
		
		// 2단계 - 자동 박싱, 자동 언박싱 용어를 이해하지
		
		Integer num4 = 17; // 자동 박싱 int <-- Wrapper ---> Integer 변환 된것 !
		int num5 = num4; // 자동 언박싱
		
	}
	
}
