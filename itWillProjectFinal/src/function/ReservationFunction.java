package function;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.MemberDAO;
import dao.TestDAO;
import dao.TheaterDAO;
import vo.MemberVO;
import vo.MovieVO;
import vo.ReservationVO;
import vo.TheaterVO;
import dao.ReservationDAO;
import main.StartMainProgram;

public class ReservationFunction {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Scanner scan = new Scanner(System.in);

	TestDAO dao = new TestDAO();
	TheaterDAO dao1 = new TheaterDAO();
	MemberDAO mdao = new MemberDAO();
	ReservationDAO rdao = new ReservationDAO();
	StartMainProgram startMP = new StartMainProgram();

	// ------------------------------영화 예매하기(데이터 업로드) 메서드-------------------------
	public void reInsert() throws IOException { // 메서드 (클래스 만들어서 호출할 것)

		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━┓\r\n" + "    티켓 구매를 진행합니다   \r\n" + "┗━━━━━━━━━━━━━━━━━━━━━━┛\r\n");

		int memID = 0;
		int mov;
		String time = null;
		String seat = null;
		int price = 500000;
		int movID = 0;

		while (true) {
			while (true) {
				System.out.println("구매하시는 분의 아이디를 재입력해주세요 : ");

				String id = typing();

				// 아이디로 회원 정보를 조회하여 기존 비밀번호를 확인
				if (dao.selectOne(id) == null) {
					System.out.println("해당 아이디로 등록된 회원 정보가 없습니다.");
					continue; // 오류발생.
				} else {
					MemberVO member = dao.selectOne(id);
					memID = member.getMember_ID();
					break;
				}
			}

			System.out.println("영화 목록을 불러옵니다. 잠시만 기다려 주십시오");
			countdown(3);
			List<MovieVO> list = dao.mvSelectAll();
			for (MovieVO movie : list) {
				System.out.println("영화 코드 : " + movie.getMov_id());
				System.out.println("영화 제목 : " + movie.getMov_name());
				System.out.println("영화 장르 : " + movie.getMov_genre());
				System.out.println("주연 배우 : " + movie.getMov_actor());
				System.out.println("영화 등급 : " + movie.getMov_grade() + "세 미만 관람불가");
				System.out.println("영화 평점 : " + movie.getMov_score() + "/10 점");
				System.out.println("-----------------------------------------");
			}

			System.out.println("관람을 원하시는 영화의 코드를 입력하여 주십시오.");
			movID = scan.nextInt();

			System.out.println("선택 가능한 시간 입니다. 원하시는 시간을 선택하여 주십시오.");
			System.out.println("1. 10:00   2. 16:00   3.22:00");

			String selectTime = typing();

			switch (selectTime) {
			case "1":
				System.out.println("오전 10시를 선택하셨습니다.");
				selectTime = "10:00";
				break;
			case "2":
				System.out.println("오후 4시를 선택하셨습니다.");
				selectTime = "16:00";
				break;
			case "3":
				System.out.println("오후 10시를 선택하셨습니다.");
				selectTime = "22:00";
				break;
			default:
				System.out.println("없는 번호 입니다. 상영 중인 시간을 다시 선택해 주세요.");
				continue;
			}
			System.out.println("--------------------");
			System.out.println("좌석을 선택해 주세요.");
			System.out.println("-=-=-=-=-= 잔여 좌석 -=-=-=-=-=");

			System.out.println(dao1.selectSeat(selectTime, movID));
			if (dao1.selectSeat(selectTime, movID).isEmpty()) {
				System.out.println("잔여 좌석이 없습니다. 예매화면으로 돌아갑니다.");
				countdown(2);
				StartMainProgram.startMakingReserve();
			}

			seat = typing();
			System.out.println(seat + "번 좌석을 선택하셨습니다. ");

			ReservationVO reInsert1 = dao.booking(memID, movID, selectTime, seat, price);
			if (reInsert1 != null) {
				System.out.println("예매가 완료되었습니다. 감사합니다.");
				System.out.println("예매를 더 진행하시겠습니까? 1.예  2. 아니요");
				String choice1 = typing();
				if (choice1.equals("1")) {
					continue;
				} else {
					break;
				}
			} else {
				System.out.println("예매에 실패 했습니다.");
				// 좌석,시간 중복되어서 오류까지 가지 않게 자바에서 안내문 필요.
				System.out.println("예매 초기화면으로 넘어갑니다.");
				reInsert();

			}
		}
	}

	//-------------------------예매내역 확인 메서드----------------------------
	public void viewReservation() {

		while (true) {

			System.out.println("예매내역 조회입니다.");
			System.out.println("1. 조회   2. 이전 화면으로 돌아가기");
			String choice;
			choice = typing6();
			while (true) {
				if (choice.equals("1")) {
					System.out.println("조회하시는 분의 아이디를 재입력해주세요 : ");
					System.out.println("-----------");
					String id = typing6();

					// 아이디로 회원 정보를 조회하여 기존 비밀번호를 확인
					if (mdao.memSelect(id) == null) {
						System.out.println("해당 아이디로 등록된 회원 정보가 없습니다.");
						continue;
					} else {

						if (rdao.selectAll(id) == null) {
							System.out.println("조회된 예매내역이 없습니다. 이전 화면으로 돌아갑니다.");
							countdown(2);

							StartMainProgram.startMakingReserve();
							break;
						} else {
							System.out.println("예매조회가 완료되었습니다. 이전화면으로 돌아갑니다.");
							countdown(2);

							StartMainProgram.startMakingReserve();
							break;
						}

					}

				} else if (choice.equals("2")) {
					System.out.println("조회를 취소하였습니다. 이전화면으로 되돌아갑니다.");
					countdown(2);
					System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					StartMainProgram.startMakingReserve();
				} else {
					System.out.println("잘못된 선택입니다. 다시 입력하여 주세요.");
					continue;
				}
			}
		}
	}

	public String typing6() {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

//-----------------------------예매 취소 메서드------------------------------
//	public void deleteReservation() {
//		String id = null;
//		while (true) {
//
//			System.out.println("예매취소입니다.");
//			System.out.println("1. 취소하기   2. 이전 화면으로 돌아가기");
//			String choice;
//			choice = typing7();
//			while (true) {
//
//				if (choice.equals("1")) {
//					System.out.print("취소하시는 분의 아이디를 재입력해주세요 : ");
//					System.out.println("-----------");
//					id = typing7();
//
//					// 아이디로 회원 정보를 조회하여 기존 비밀번호를 확인
//					if (mdao.memSelect(id) == null) {
//						System.out.println("해당 아이디로 등록된 회원 정보가 없습니다.");
//						continue; // 오류발생.
//					} else {
//						System.out.println("다음 예매내역 중 취소하실 예매번호를 입력해주세요");
//						rdao.selectAll(id);
//						int bookingId = Integer.parseInt(typing7());
//						ReservationVO result = rdao.bookingCancel(bookingId);
//						if (result == null) {
//							System.out.println("예매취소가 완료되었습니다. 이어서 취소하시겠습니까?");
//							System.out.println("1.예  2. 아니요");
//							String choice1 = typing();
//							if (choice1.equals("1")) {
//								continue;
//							} else {
//								System.out.println("이전 화면으로 돌아갑니다.");
//								countdown(2);
//								StartMainProgram.startMakingReserve();
//								break;
//							}
//						}
//						break;
//					}
//
//				} else if (choice.equals("2")) {
//					System.out.println("예매취소 진행을 중단하였습니다. 이전화면으로 되돌아갑니다.");
//					countdown(2);
//					System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
//					StartMainProgram.startMakingReserve();
//				} else {
//					System.out.println("잘못된 선택입니다. 다시 입력하여 주세요.");
//					continue;
//				}
//			}
//
//			System.out.println("다음 예매내역 중 취소하실 예매번호를 입력해주세요");
//			rdao.selectAll(id);
//			int bookingId = Integer.parseInt(typing7());
//			ReservationVO result = rdao.bookingCancel(bookingId);
//			System.out.println(result);
//			if (result != null) {
//				System.out.println("예매취소가 완료되었습니다. 이어서 취소하시겠습니까?");
//				System.out.println("1.예  2. 아니요");
//				String choice1 = typing();
//				if (choice1.equals("1")) {
//					continue;
//				} else {
//					System.out.println("이전 화면으로 돌아갑니다.");
//					countdown(2);
//					StartMainProgram.startMakingReserve();
//					break;
//				}
//			}
//
//		}
//
//	}
	
	// 예매 취소
		public void deleteReservation() {

			while (true) {

				System.out.println("예매취소입니다.");
				System.out.println("1. 취소하기   2. 이전 화면으로 돌아가기");
				String choice;
				choice = typing7();

				if (choice.equals("1")) {
					System.out.print("구매하시는 분의 아이디를 재입력해주세요 : ");
					String id = typing7();
//					viewReservation();

					while (true) {

						// 아이디로 회원 정보를 조회하여 기존 비밀번호를 확인
						if (mdao.memSelect(id) == null) {
							System.out.println("해당 아이디로 등록된 회원 정보가 없습니다. 다시입력해주세요 :");
							continue; // 오류발생.
						} else
							break;

					}

				} else if (choice.equals("2")) {
					System.out.println("조회를 취소하였습니다. 초기화면으로 되돌아갑니다.");
					countdown(2);
					StartMainProgram.startMakingMember();
				} else {
					System.out.println("잘못된 선택입니다. 다시 입력하여 주십시오.");
					continue;
				}

				System.out.println("취소하실 예매번호를 입력해주십시오");
				int bookingId = Integer.parseInt(typing7());		
				rdao.bookingCancel(bookingId);
				rdao.bookingCancelView(bookingId);
			}

		}

	public String typing7() {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
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