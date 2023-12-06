package function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dao.TestDAO;
import main.StartMainProgram;
import vo.MemberVO;
import vo.MovieVO;
import vo.ReservationVO;

public class MovieFunction {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	// ------------------------------영화 추가하기 메서드-------------------------
	public static void mvinsert() { // 메서드 (클래스 만들어서 호출할 것)

		TestDAO dao = new TestDAO();

		System.out
				.println("┏━━━━━━━━━━━━━━━━━━━━━━━┓\r\n" + "    영화 등록을 시작합니다   \r\n" + "┗━━━━━━━━━━━━━━━━━━━━━━━┛\r\n");

		while (true) {
			System.out.println("현재 등록되어있는 영화 목록을 불러오시겠습니까? 1.예  2.아니요");
			String yesNo = typing();
			System.out.println("-------------------------");
			if (yesNo.equalsIgnoreCase("1")) {
				ArrayList<MovieVO> list = dao.mvSelectAll();
				for (MovieVO mv : list) {
					System.out.println(">" + mv);

				}
				System.out.println("등록하실 영화 제목을 입력해 주세요 : ");
			} else if (yesNo.equalsIgnoreCase("2")) {
				System.out.println("등록하실 영화 제목을 입력해 주세요 : ");
			} else {
				System.out.println("입력이 잘못 되었습니다. ");
				System.out.println("다시 입력해 주세요 : ");
				continue;
			}

			String name = typing();
			System.out.println("등록하실 영화 장르를 입력해 주세요 : ");
			String genre = typing();
			;
			System.out.println("등록하실 영화 배우를 입력해 주세요 : ");
			String actor = typing();
			System.out.println("등록하실 영화 관람등급 입력해 주세요 : ");
			int grade = Integer.parseInt(typing());
			System.out.println("등록하실 영화 평점(0~10) 을 입력해 주세요 : ");
			int score = Integer.parseInt(typing());

			System.out.println("등록중입니다. 잠시만 기다려 주세요.");
			countdown(2);

			MovieVO insertedMovie = dao.mvInsert(name, genre, actor, grade, score);
			if (insertedMovie != null) {
				System.out.println("영화 등록 성공:");
				System.out.println("영화 이름: " + insertedMovie.getMov_name());
				System.out.println("영화 장르: " + insertedMovie.getMov_genre());
				System.out.println("주연 배우: " + insertedMovie.getMov_actor());
				System.out.println("영화 등급: " + insertedMovie.getMov_grade());
				System.out.println("영화 평점: " + insertedMovie.getMov_score());
			} else {
				System.out.println("영화 등록 실패.");
			}

			System.out.println("다시 등록 하시겠습니까? 1.예  2.아니요");
			String again = typing();
			if (again.equalsIgnoreCase("1")) {
				System.out.println("영화 등록을 재시작 합니다. 잠시만 기다려 주세요.");
				countdown(2);
				continue;
			} else {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
		System.out.println("초기화면으로 되돌아갑니다.");
		countdown(2);
		StartMainProgram.startProgram();
	}

	// ------------------------------영화 삭제하기 메서드-------------------------
	public static void mvdelete() { // 메서드

		TestDAO dao = new TestDAO();

		System.out
				.println("┏━━━━━━━━━━━━━━━━━━━━━━━┓\r\n" + "    영화 삭제를 시작합니다   \r\n" + "┗━━━━━━━━━━━━━━━━━━━━━━━┛\r\n");

		while (true) {
			System.out.println("현재 등록되어있는 영화 목록을 불러오시겠습니까? (1.예  2.아니요)");
			String yesNo = typing4();

			if (yesNo.equalsIgnoreCase("1")) {
				ArrayList<MovieVO> list = dao.mvSelectAll();
				for (MovieVO mv : list) {
					System.out.println(mv);
				}
				System.out.println("삭제하실 영화 ID값을 입력해 주세요. ");
			} else if (yesNo.equalsIgnoreCase("n")) {
				System.out.println("삭제하실 영화 ID값을 입력해 주세요. ");
			} else {
				System.out.println("입력이 잘못 되었습니다. ");
				System.out.println("다시 입력해 주세요. ");
				continue;
			}

			int mvdelete = Integer.parseInt(typing4());
			System.out.println("삭제 중 입니다. 잠시만 기다려 주세요. ");
			countdown4(2);

			boolean result = dao.mvdelete(mvdelete);
			if (result) {
				System.out.println("영화 삭제 성공!");
			} else {
				System.out.println("영화 삭제 실패.");
			}

			System.out.println("다시 삭제하시겠습니까? 1.예  2.아니요");
			String again = typing4();

			if (again.equalsIgnoreCase("1")) {
				System.out.println("영화 삭제를 재시작 합니다. 잠시만 기다려 주세요.");
				countdown4(2);
				continue;
			} else {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
		System.out.println("초기화면으로 되돌아 갑니다.");
		countdown4(2);
		StartMainProgram.startProgram();

	}

	// ------------------------BuffredReader 메서드화 (코드가독성)-------------------------
	public static String typing4() {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	// ------------------------ 카운트다운 -----------------------------
	public static void countdown4(int seconds) {
		for (int i = seconds; i >= 1; i--) {
			System.out.println(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

//-----------------------------------------영화 전체내역 조회 메서드-------------------------
	public static void mvselectAll() { // 메서드

		TestDAO dao = new TestDAO();

		System.out.println(
				"┏━━━━━━━━━━━━━━━━━━━━━━━━━━┓\r\n" + "    영화 전체내역을 불러옵니다   \r\n" + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━┛\r\n");

		List<MovieVO> list = dao.mvSelectAll();
		for (MovieVO movie : list) {
			System.out.println("영화 코드 : " + movie.getMov_id());
			System.out.println("영화 제목 : " + movie.getMov_name());
			System.out.println("영화 장르 : " + movie.getMov_genre());
			System.out.println("주연 배우 : " + movie.getMov_actor());
			System.out.println("영화 등급 : " + movie.getMov_grade() + "세 미만 관람불가");
			System.out.println("영화 평점 : " + movie.getMov_score() + "/10 점");
			System.out.println("-------------------------------------------------------");
		}

		System.out.println("초기화면으로 되돌아 가시길 원하시면 1번");
		// System.out.println("예매를 원하시면 2번"); // 예매 매서드 만들어고 나서 추가.
		System.out.println("종료를 원하시면 2번을 눌러주세요.");

		int num = Integer.parseInt(typing());
		while (true) {
			if (num == 1) {
				System.out.println("초기화면으로 되돌아갑니다.");
				countdown(2);
				StartMainProgram.startMakingMember();
			} else if (num == 2) {
				System.out.println("프로그램을 종료합니다. ");
				break;
			} else {
				System.out.println("잘못된 입력으로 인해 프로그램을 자동 종료합니다.");
				break;
			}

		}

	}

//------------------------------등록된 영화 평점 높은 순 5개 조회 메서드-------------------------
	public static void mvSelectScore() {

		TestDAO dao = new TestDAO();

		System.out.println("***********************************");
		System.out.println("*                             	  *");
		System.out.println("*    평점 상위 5개의 영화를 조회합니다	  *");
		System.out.println("*                                 *");
		System.out.println("***********************************");

		ArrayList<MovieVO> mvso = dao.mvSelectScore();
		for (MovieVO list : mvso) {
			System.out.println(list);
		}

		System.out.println("초기화면으로 되돌아 가시길 원하시면 1번");
		// System.out.println("예매를 원하시면 2번"); // 예매 매서드 만들어고 나서 추가.
		System.out.println("종료를 원하시면 2번을 눌러주세요.");

		int num = Integer.parseInt(typing());
		while (true) {
			if (num == 1) {
				System.out.println("초기화면으로 되돌아갑니다.");
				countdown(2);
				StartMainProgram.startMakingMember();
			} else if (num == 2) {
				System.out.println("프로그램을 종료합니다. ");
				break;
			} else {
				System.out.println("잘못된 입력으로 인해 프로그램을 자동 종료합니다.");
				break;
			}

		}

	}

//------------------------------영화 제목으로 검색 메서드-------------------------
	public static void mvselectSearch() { 

		TestDAO dao = new TestDAO();

		System.out.println("***********************************");
		System.out.println("*                                 *");
		System.out.println("*       제목으로 영화를 검색합니다	  *");
		System.out.println("*                                 *");
		System.out.println("***********************************");

		System.out.println("검색어 (영화제목) : ");
		String searchTitle = typing();

		ArrayList<MovieVO> list = dao.mvSelectOne(searchTitle);

		for (MovieVO movie : list) {
			System.out.println("영화 코드 : " + movie.getMov_id());
			System.out.println("영화 제목 : " + movie.getMov_name());
			System.out.println("영화 장르 : " + movie.getMov_genre());
			System.out.println("주연 배우 : " + movie.getMov_actor());
			System.out.println("영화 등급 : " + movie.getMov_grade() + "세 미만 관람불가");
			System.out.println("영화 평점 : " + movie.getMov_score() + "/10 점");
			System.out.println("-------------------------------------------------------");
		}

		System.out.println("초기화면으로 되돌아 가시길 원하시면 1번");
		// System.out.println("예매를 원하시면 2번"); // 예매 매서드 만들어고 나서 추가.
		System.out.println("종료를 원하시면 2번을 눌러주세요.");

		int num = Integer.parseInt(typing());
		while (true) {
			if (num == 1) {
				System.out.println("초기화면으로 되돌아갑니다.");
				countdown(2);
				StartMainProgram.startMakingMember();
			} else if (num == 2) {
				System.out.println("프로그램을 종료합니다. ");
				break;
			} else {
				System.out.println("잘못된 입력으로 인해 프로그램을 자동 종료합니다.");
				break;
			}

		}

	}

	// ------------------------BuffredReader 메서드화 (코드가독성)-------------------------
	public static String typing() {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	// ------------------------ 카운트다운 -----------------------------
	public static void countdown(int seconds) {
		for (int i = seconds; i >= 1; i--) {
			System.out.println(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}