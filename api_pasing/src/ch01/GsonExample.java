package ch01;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonExample {

	public static void main(String[] args) {

		//
		Student student1 = new Student("고길동", 40, "애완학과");
		Student student2 = new Student("둘리", 5, "문제학과");

		//
		Student[] studentArr = { student1, student2 };

		// --> 특정 형식(구조화) 되어 있는 문자열로 변환 하고 싶다.. --> JSON 형태
//		Gson gson = new Gson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		// 객체를 --> json 형식에 문자열로 변환 시켜! --> 메서드 toJson()
		String studentStr = gson.toJson(student1);
		System.out.println(studentStr);

		// setPrettyPrinting -> 이쁘게 출력하라 옵션...
		Gson gson2 = new Gson();
		String studentStr2 = gson2.toJson(student2);
		System.out.println(studentStr2);

		// 객체에서 ---> 문자열 형태로 가능 그럼 반대로는 어떻게 하지??
		// 문자열에서 ---> 클래스 형태로 어떻게 변경할까??

		Student studentObject = gson.fromJson(studentStr, Student.class);
		System.out.println(studentObject.getName());

		////////////////////////////////////////////////////////////////////////

		Dog dog1 = new Dog("진도5.0", 5, "진돗개");
		Dog dog2 = new Dog("말라뮤트올", 2, "말라뮤트");
		Dog dog3 = new Dog("사모펀드", 3, "사모예드");

		String dog1Str = gson.toJson(dog1);
		System.out.println(dog1Str);
		String dog2Str = gson.toJson(dog2);
		System.out.println(dog2Str);
		String dog3Str = gson.toJson(dog3);
		System.out.println(dog3Str);
		
		Dog dogObject = gson.fromJson(dog1Str, Dog.class);
		System.out.println(dogObject.getName());
		dogObject.setName("진도1.0");
		System.out.println(dogObject.getName());
	}
}
