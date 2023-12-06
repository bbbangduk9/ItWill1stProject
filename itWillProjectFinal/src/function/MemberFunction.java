package function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import dao.MemberDAO;
import main.StartMainProgram;
import vo.MemberVO;

public class MemberFunction {

	static MemberDAO dao = new MemberDAO();

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	private static String id = null;
	private static String pw = null;
	private static String name = null;
	private static String email = null;
	private static String phone = null;
	private static String strTemp5 = null;
	private static MemberVO existingMember = null;

	// ----------회원가입 기능 메소드--------------
	public void register(MemberDAO dao) {

		System.out.println(">>회원가입");
		try {

			// id등록
			System.out.print("ID : ");
			while (true) {
				id = br.readLine();
				if (id.isEmpty()) { // id값 입력 안했을시
					System.out.println("아이디를 입력해 주세요.");
					continue;
				} else if (dao.memSelect(id) == null) {
					break;
				}
				System.out.println("이미 사용중인 아이디 입니다. 다른 아이디를 입력해 주세요 : "); // id값 중복확인
			}

			// pw등록
			System.out.print("비밀번호 : ");
			while (true) {
				pw = br.readLine();

				if (pw.length() < 5 || pw.length() > 10) { // pw 길이 조건지정
					System.out.println("5자리 이상 10자리 이하로 입력해주세요");
				} else if (pw.isEmpty()) { // pw값 입력 안했을시
					System.out.println("비밀번호를 입력해 주세요.");
				} else {
					break;
				}
			}

			// 이름등록
			System.out.print("이름입력 : ");
			while (true) {
				name = br.readLine();
				if (name.isEmpty()) {
					System.out.println("이름을 입력해 주세요."); // 이름 값 입력 안했을시
				} else {
					break;
				}
			}

			// email 등록
			System.out.print("Email : ");

			while (true) {
				email = br.readLine();
				if (email.isEmpty()) { // email 값 입력 안했을시
					System.out.println("이메일을 입력해 주세요.");
					continue;
				} else if (dao.mSelectEmail(email) == null) {
					break;
				}
				System.out.println("이미 사용중인 이메일 입니다. 다른 이메일을 입력해 주세요 : "); // email값 중복방지
			}

			// 휴대폰번호 등록
			System.out.print("휴대폰번호 : ");

			while (true) {
				phone = br.readLine();
				if (phone.isEmpty()) {
					System.out.println("휴대폰번호를 입력해 주세요.");
					continue;
				} else if (dao.mSelectphone(phone) == null) {
					break;
				}
				System.out.println("이미 사용중인 번호입니다. 다른 번호를 입력해 주세요 : ");
			}

			// DB에 회원가입 정보 데이터 저장
			MemberVO member = new MemberVO(id, pw, name, email, phone);
			int result = dao.memInsert(member);

			if (result == 1)
				System.out.println("회원가입 성공.");
			else
				System.out.println("회원가입 실패 관리자에게 문의하세요.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// -------------------회원정보수정(비밀번호) 메서드-------------------
	public static void updatePassword(MemberDAO dao) {
		System.out.println(">>비밀번호 수정");
		String oldPassword = null;
		String newPassword = null;
		System.out.println("비밀번호 수정을 위해 아이디를 입력하세요");
		System.out.print("아이디: ");

		try {
			while (true) {

				id = br.readLine();
				existingMember = dao.memSelect(id);

				if (existingMember == null) {
					System.out.print("등록된 회원 정보가 없습니다. 다시입력해주세요 :");
					continue;
				} else {
					break;
				}
			}

			System.out.print("기존 비밀번호를 입력하세요 : ");
			while (true) {
				oldPassword = br.readLine();

				if (!existingMember.getCust_password().equals(oldPassword)) {
					System.out.print("비밀번호가 일치하지 않습니다. 다시 입력해주세요 : ");
				} else {
					break;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// pw등록
		System.out.print("새로운 비밀번호를 입력해주세요 : ");
		while (true) {
			try {
				newPassword = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (newPassword.length() < 5 || newPassword.length() > 10) { // pw 길이 조건지정
				System.out.print("5자리 이상 10자리 이하로 입력해주세요 : ");
			} else if (newPassword.isEmpty()) {
				System.out.println("비밀번호를 입력해 주세요.");
			} else if (newPassword.equals(oldPassword)) {
				System.out.print("기존 비밀번호와 같습니다. 다시입력해주세요 : ");

			} else {
				break;
			}
		}
		MemberVO vo = new MemberVO(id, newPassword);

		dao.update(vo);
		System.out.println("비밀번호 변경 성공!");
	}

	// -----------------로그인 메서드---------------------
	public static void login(MemberDAO dao) {

		System.out.println(">>로그인");
		System.out.print("아이디: ");

		try {
			while (true) {

				id = br.readLine();
				existingMember = dao.memSelect(id);

				if (existingMember == null) {
					System.out.println("등록된 회원 정보가 없습니다. 다시입력해주세요 :");

					continue;
				} else {
					break;
				}
			}

			System.out.print("비밀번호를 입력하세요 : ");
			while (true) {
				String oldPassword = br.readLine();

				if (!existingMember.getCust_password().equals(oldPassword)) {
					System.out.println("비밀번호가 일치하지 않습니다. 다시입력해주세요 : ");
				} else {
					System.out.println("로그인 성공!");
					break;
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//-------------------회원정보 조회 메서드-------------------------
	public static void member_view(MemberDAO dao) {
		// -----로그인 후 회원정보 조회기능
		System.out.println("회원조회 하시겠습니까? 1. 예  2. 아니요");
		String choice;

		try {
			choice = br.readLine();
			if (choice.equals("1")) {
				System.out.println("회원조회를 위해 아이디를 입력해주세요 : ");

				while (true) {
					String id2 = br.readLine();

					// 아이디로 회원 정보를 조회하여 기존 비밀번호를 확인
					if (dao.memSelect(id2) == null) {
						System.out.println("해당 아이디로 등록된 회원 정보가 없습니다. 다시입력해주세요 :");
						continue;
					} else if (!(id2.equals(id))) {
						System.out.println("아이디를 잘못입력하셨습니다. 다시입력하세요 : ");

					} else {
						MemberVO member = dao.memSelect(id);
						break;
					}
				}
			} else if (choice.equals("2")) {
				System.out.println("회원조회를 취소하였습니다.");
			} else {
				System.out.println("잘못된 선택입니다.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// --------------------회원탈퇴 메서드--------------------------
	public static void deleteMember(MemberDAO dao) {

		System.out.println("회원탈퇴를 위해 아이디와 비밀번호를 입력해주세요");
		while (true) {

			try {
				id = br.readLine();
				existingMember = dao.memSelect(id);
				// 아이디로 회원 정보를 조회하여 기존 비밀번호를 확인
				if (existingMember == null) {
					System.out.println("해당 아이디로 등록된 회원 정보가 없습니다. 다시입력해주세요 :"); // 기존 아이디 확인
					continue;
				} else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("비밀번호를 입력하세요 : ");
		while (true) {
			String oldPassword;
			try {
				oldPassword = br.readLine();

				if (!existingMember.getCust_password().equals(oldPassword)) { // 기존 비밀번호가 일치하는지 확인
					System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요 : ");
				} else {
					MemberVO member = new MemberVO(id, oldPassword);
					int delete = dao.delete(member);
					if (delete == 1) {
						System.out.println("회원탈퇴 완료");
						break;
					} else {
						System.out.println("회원탈퇴 실패 이전 화면으로 돌아갑니다");
						StartMainProgram.startMakingMember();
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	public static void loginDB(MemberDAO dao) {
		   
	      System.out.println(">>로그인");
	      System.out.print("아이디: ");

	      try {
	         while (true) {

	            id = br.readLine();
	            existingMember = dao.memSelect(id);

	            if (existingMember == null) {
	               System.out.println("등록된 회원 정보가 없습니다. 다시입력해주세요 :");
	              
	               continue;
	            } else {
	               break;
	            }
	         }

	         System.out.print("비밀번호를 입력하세요 : ");
	         while (true) {
	            String oldPassword = br.readLine();

	            if (!existingMember.getCust_password().equals(oldPassword) ||
	            	!(existingMember.getCust_id().equals("admin") && existingMember.getCust_password().equals("1111"))
	            		) {
	            	System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡠⠤⠤⣄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
	                   		+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠔⠉⠀⠀⠀⠀⠀⠀⠉⠢⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
	                   		+ "⠀⠀⠀⠀⠀⠀⠀⠀⢰⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢣⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
	                   		+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡀⢀⣀⡤⣀⡀⠀⠀⠀⢠아이~ 비번 씻팔!!\r\n"
	                   		+ "⠀⠀⠀⠀⠀⠀⠀⠀⠸⠀⢠⣅⣾⠜⠿⣴⣧⢱⠀⠀⠀⡜⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
	                   		+ "⠀⠀⠀⠀⠀⠀⠀⢀⠜⠁⠈⠛⡻⠊⠑⢝⠯⠜⠀⠀⠀⠃⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
	                   		+ "⠀⠀⠀⠀⠀⠀⠀⢨⡀⠀⠀⠀⡇⠀⠀⢸⠀⠀⣀⣀⡤⠂⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
	                   		+ "⠀⠀⠀⠀⠀⠀⠀⠀⠑⠢⢤⣤⡬⣔⣒⣁⣰⡿⠋⠹⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
	                   		+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢞⡄⢀⣜⡽⠋⠀⠀⠀⠑⣄⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
	                   		+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠼⠿⠞⠋⠀⠀⠀⠀⠀⠀⠀⠉⠑⠂⠄");
	            } else if (existingMember.getCust_id().equals("admin") && existingMember.getCust_password().equals("1111")) {
	               System.out.println("로그인 성공!");
	               break;
	            } 
	         
	         }
	         } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	   }
}