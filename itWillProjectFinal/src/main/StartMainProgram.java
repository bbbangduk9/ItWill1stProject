package main;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import dao.MemberDAO;
import dao.TestDAO;
import function.MemberFunction;
import function.MovieFunction;
import function.ReservationFunction;
import vo.MemberVO;
import vo.ReservationVO;

public class StartMainProgram {

	static MovieFunction mf2 = new MovieFunction();
	static ReservationFunction rf = new ReservationFunction();
	static MemberDAO dao = new MemberDAO();
	static MemberFunction mf = new MemberFunction();
	static Scanner scanner = new Scanner(System.in);
//      static TheaterDAO dao2 = new TheaterDAO();
	static TestDAO t1 = new TestDAO();
	public static void main(String[] args) {
		startProgram();

	}

	public static void startProgram() {
		System.out.println("=========================================");
		System.out.println("██████████╗    ███╗   ███╗█████╗██╗  ██╗\r\n"
				+ "██╚══██╔══╝    ████╗ ██████╔══██╚██╗██╔╝\r\n" + "██║  ██║       ██╔████╔█████████║╚███╔╝ \r\n"
				+ "██║  ██║       ██║╚██╔╝████╔══██║██╔██╗ \r\n" + "██║  ██║       ██║ ╚═╝ ████║  ████╔╝ ██╗");
		System.out.println("=========================================");
		while (true) {

			System.out.println("1. 로그인");
			System.out.println("2. 회원가입"); // --> 로그인 후 회원정보조회가능
			System.out.println("3. 관리자 로그인");
			System.out.println("══════════════");
			int choice = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기

			switch (choice) {
			case 1:
				mf.login(dao);
				startMakingReserve();
				break;
			case 2:
				mf.register(dao);
				break;
			case 3:
				mf.login(dao);
				startMakingMovie();
				break;

			}
		}

	}

//	public static void startMakingMovie() {
//
//		while (true) {
//
//			System.out.println("═══════════");
//			System.out.println("1. 영화 추가");
//			System.out.println("2. 영화 삭제");
//			System.out.println("═══════════");
//			int choice = scanner.nextInt();
//			scanner.nextLine(); // 버퍼 비우기
//
//			switch (choice) {
//			case 1:
//				MovieFunction.mvinsert();
//				break;
//			case 2:
//				MovieFunction.mvdelete();
//				break;
//
//			}
//		}
//
//	}
	
	public static void startMakingMovie() {
        
        
 	   System.out.println("1. 영화 추가");
        System.out.println("2. 영화 삭제"); 
        System.out.println("3. 전체 예매내역 조회"); 
        System.out.println("4. 관리자 로그아웃");
        int choice = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        switch (choice) {
        case 1:
           MovieFunction.mvinsert();
           break;
        case 2:
     	   MovieFunction.mvdelete();
           break;
        case 3:
     	   t1.adminShowInfo();
     	   startMakingMovie();
     	   break;
        case 4 : 
     	   System.out.println("로그아웃 성공! 첫화면으로 돌아갑니다.");
            startProgram();
            break;
     	   
     	   
        }
   
}

	public static void startMakingMember() {

		while (true) {
			System.out.println("════════════");
			System.out.println("1. 회원조회");
			System.out.println("2. 비밀번호 수정"); // --> 로그인 후 회원정보조회가능
			System.out.println("3. 회원탈퇴");
			System.out.println("4. 돌아가기");
			System.out.println("════════════");
			int choice = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기

			switch (choice) {
			case 1:
				mf.member_view(dao);
				break;
			case 2:
				mf.updatePassword(dao);
				break;
			case 3:
				mf.deleteMember(dao);
				break;
			case 4:
				startMakingReserve();
				break;

			}
		}
	}

	public static void startMakingReserve() {

		while (true) {
			System.out.println("════════════");
			System.out.println("1. 예매하기");
			System.out.println("2. 예매내역확인");
			System.out.println("3. 예매취소");
			System.out.println("4. 마이페이지");
			System.out.println("5. 로그아웃");
			System.out.println("════════════");
			int choice = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기

			switch (choice) {
			case 1:
				try {
					rf.reInsert();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				rf.viewReservation();
				break;
			case 3:
				rf.deleteReservation();
				break;
			case 4:
				startMakingMember();
				break;
			case 5:
				System.out.println("로그아웃 성공! 첫화면으로 돌아갑니다.");
				startProgram();
				break;

			}
		}
	}

}