package com.tenco.quiz.ver2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.tenco.quiz.DBConnectionManager;

public class QuizRepositoryTest1 {

	public static void main(String[] args) {

		// 메서드 호출해서 실행 확인 디버깅 확인 테스트 반드시 한다
		// QuizRepository 구현 클래스 테스트
		QuizRespositoryImpl impl = new QuizRespositoryImpl();
		Scanner scanner = new Scanner(System.in);
		try {
			Connection conn = DBConnectionManager.getConnection();

			while (true) {
				printMenu();
				int choice = scanner.nextInt();

				if (choice == 1) {
					// 퀴즈 문제 추가 --> 함수로 만들기(update)
					String question = scanner.nextLine();
					String answer = scanner.nextLine();
					impl.addQuizQuestion(question, answer);
				} else if (choice == 2) {
					// 퀴즈 문제 조회 --> 함수로 만들기(select)
					List<QuizDTO> list = impl.viewQuizQuestion();
					for (QuizDTO quizDTO : list) {
						System.out.println(quizDTO.toString());
					}
				} else if (choice == 3) {
					// 퀴즈 게임 시작 --> 함수로 만들기
					System.out.println("문제 : " + impl.playQuizGame().getQuestion());
					System.out.println("정답 : ");
					String userAnswer = scanner.next();
					scanner.nextLine();
					if (userAnswer.equalsIgnoreCase(impl.playQuizGame().getAnswer())) {
						System.out.println("정답!");
					} else {
						System.out.println("오답!");
						System.out.println("정답은 : " + impl.playQuizGame().getAnswer());
					}
				} else if (choice == 4) {
					System.out.println("프로그램을 종료 합니다.");
					break;
				} else {
					System.out.println("잘못된 선택 입니다. 다시 시도하세요.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 실행의 흐름 만들기
	}

	private static void printMenu() {
		System.out.println();
		System.out.println("----------------------------------");
		System.out.println("1. 퀴즈 문제 추가");
		System.out.println("2. 퀴즈 문제 조회");
		System.out.println("3. 퀴즈 게임 시작");
		System.out.println("4. 종료");
		System.out.print("옵션을 선택 하세요 : ");
	}
}
