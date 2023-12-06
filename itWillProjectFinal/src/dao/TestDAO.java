package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.CommonJDBCUtil;
import vo.MemberVO;
import vo.MovieVO;
import vo.ReservationVO;

public class TestDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

// ---------회원가입을 위한 insert
	public int memberInsert(MemberVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO MEMBER ");
		sql.append("(MEMBER_ID, CUST_ID, CUST_PASSWORD, CUST_NAME, CUST_EMAIL, CUST_PHONE) ");
		sql.append("VALUES ((SELECT COALESCE(MAX(MEMBER_ID), 0) + 1 FROM MEMBER), ?, ?, ?, ?, ?) ");

		try {
			conn = CommonJDBCUtil.getConnection();

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, vo.getCust_id());
			pstmt.setString(2, vo.getCust_password());
			pstmt.setString(3, vo.getCust_name());
			pstmt.setString(4, vo.getCust_email());
			pstmt.setString(5, vo.getCust_phone());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	//회원조회, 로그인을 위한 select
	public MemberVO selectOne(String id) {
		MemberVO vo = null;

		try {
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT MEMBER_ID, CUST_ID, CUST_PASSWORD, CUST_NAME, CUST_EMAIL, CUST_PHONE ");
			sb.append("  FROM MEMBER ");
			sb.append(" WHERE CUST_ID = ? "); // CUST_ID를 기준으로 조회

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new MemberVO(rs.getInt("MEMBER_ID"), rs.getString("CUST_ID"), rs.getString("CUST_PASSWORD"),
						rs.getString("CUST_NAME"), rs.getString("CUST_EMAIL"), rs.getString("CUST_PHONE"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return vo;
	}

    //회원가입 시 같은 email 중복값을 비교하기 위한 select
	public MemberVO selectOne2(String email) {
		MemberVO vo = null;

		try {
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT CUST_ID, CUST_PASSWORD, CUST_NAME, CUST_EMAIL, CUST_PHONE ");
			sb.append("  FROM MEMBER ");
			sb.append(" WHERE CUST_EMAIL = ? "); // CUST_EMAIL를 기준으로 조회

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new MemberVO(rs.getString("CUST_ID"), rs.getString("CUST_PASSWORD"), rs.getString("CUST_NAME"),
						rs.getString("CUST_EMAIL"), rs.getString("CUST_PHONE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return vo;
	}

	//회원가입 시 같은 휴대폰 번호 중복값을 비교하기 위한 select
	public MemberVO selectOne3(String phone) {
		MemberVO vo = null;

		try {
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT CUST_ID, CUST_PASSWORD, CUST_NAME, CUST_EMAIL, CUST_PHONE ");
			sb.append("  FROM MEMBER ");
			sb.append(" WHERE CUST_PHONE = ? "); // CUST_EMAIL를 기준으로 조회

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, phone);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new MemberVO(rs.getString("CUST_ID"), rs.getString("CUST_PASSWORD"), rs.getString("CUST_NAME"),
						rs.getString("CUST_EMAIL"), rs.getString("CUST_PHONE"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return vo;
	}

	//회원정보수정(비밀번호)을 위한 update
	public int update(MemberVO vo) {
		int result = 0;

		try {
			// 2. DB연결 - Connection 객체 생성 <- DriverManager
			conn = CommonJDBCUtil.getConnection();

			// 3. Statement 문 실행(SQL 문 실행)
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE Member ");
			sql.append("   SET CUST_PASSWORD = ? ");
			sql.append(" WHERE CUST_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, vo.getCust_password());
			pstmt.setString(2, vo.getCust_id());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}

		return result;
	}

	//회원탈퇴를 위한 delete
	public int delete(MemberVO vo) {
		int result = 0;
		try {

			conn = CommonJDBCUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM MEMBER ");
			sql.append(" WHERE CUST_ID = ? AND CUST_PASSWORD = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, vo.getCust_id());
			pstmt.setString(2, vo.getCust_password());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			CommonJDBCUtil.close(conn, pstmt, rs);
		}

		return result;
	}

// MOVIE=========================================================================================================

	// 영화 전체 리스트를 불러오기 위한 select
	public ArrayList<MovieVO> mvSelectAll() {
		String sql = "SELECT * FROM MOVIE ";
		ArrayList<MovieVO> movList = new ArrayList<>();

		try {
			// DB 연결 - Connection 객체 생성
			conn = CommonJDBCUtil.getConnection();

			// PreparedStatement 설정
			pstmt = conn.prepareStatement(sql);

			// SQL 실행
			rs = pstmt.executeQuery();

			// SQL 실행 결과 처리
			while (rs.next()) {
				MovieVO vo = new MovieVO(rs.getInt("mov_id"), rs.getString("mov_name"), rs.getString("mov_genre"),
						rs.getString("mov_actor"), rs.getInt("mov_grade"), rs.getInt("mov_score"));
				movList.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 리소스 반납
			CommonJDBCUtil.close(conn, pstmt, rs);
		}
		return movList;
	}

	//영화 제목으로 검색하기 위한 select
	public ArrayList<MovieVO> mvSelectOne(String title) {
		MovieVO result = null;
		ArrayList<MovieVO> movList = new ArrayList<>();

		try {
			// 2. DB연결 - Connection 객체 생성 <- DriverManager
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT Mov_ID, Mov_name, Mov_genre, Mov_actor, Mov_grade, Mov_score ");
			sql.append("  FROM MOVIE ");
			sql.append("WHERE LOWER(Mov_name) LIKE ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, "%" + title.toLowerCase() + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieVO vo = new MovieVO(rs.getInt("mov_id"), rs.getString("mov_name"), rs.getString("mov_genre"),
						rs.getString("mov_actor"), rs.getInt("mov_grade"), rs.getInt("mov_score"));
				movList.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBCUtil.close(conn, pstmt, rs);
		}

		return movList;
	}

	// 영화 식별자 키로 찾기위한 select
	public int mvSelectOne(int mov_id) {
		int result = 0;

		try {
			// 2. DB연결 - Connection 객체 생성 <- DriverManager
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOV_ID ");
			sql.append("  FROM MOVIE ");
			sql.append("WHERE MOV_ID =  ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, mov_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieVO vo = new MovieVO(rs.getInt("mov_id"), rs.getString("mov_name"), rs.getString("mov_genre"),
						rs.getString("mov_actor"), rs.getInt("mov_grade"), rs.getInt("mov_score"));
				result = vo.getMov_id();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBCUtil.close(conn, pstmt, rs);
		}

		return result;
	}

	// SELECT : 별점순으로 영화 리스트를 보기 위한 select
	public ArrayList<MovieVO> mvSelectScore() {
		String sql = "SELECT * FROM MOVIE WHERE ROWNUM <= 5 ORDER BY MOV_SCORE DESC ";
		ArrayList<MovieVO> movList = new ArrayList<>();

		try {
			conn = CommonJDBCUtil.getConnection();

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieVO vo = new MovieVO(rs.getInt("mov_id"), rs.getString("mov_name"), rs.getString("mov_genre"),
						rs.getString("mov_actor"), rs.getInt("mov_grade"), rs.getInt("mov_score"));
				movList.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBCUtil.close(conn, pstmt, rs);
		}
		return movList;
	}

	//INSERT : 영화를 등록하기 위한 insert
	public MovieVO mvInsert(String name, String genre, String actor, int grade, int score) {
		MovieVO result = null;

		try {
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO MOVIE ");
			sql.append("(Mov_ID, Mov_name, Mov_genre, Mov_actor, Mov_grade, Mov_score) ");
			sql.append("VALUES ((SELECT COALESCE(MAX(MOV_ID), 0) + 1 FROM MOVIE), ?, ?, ?, ?, ?) ");

			// SELECT COALESCE(MAX(MOV_ID), 0) + 1 FROM MOVIE
			// 현존하는 시퀀스의 맥스값에서 +1하여 생성. 데이터 삭제해도 숫자를 이어나갈 수 있음.
			// 하지만 여러 스레드 또는 동시성 환경에서 충돌이 발생할 수 있으므로 주의
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, name);
			pstmt.setString(2, genre);
			pstmt.setString(3, actor);
			pstmt.setInt(4, grade);
			pstmt.setInt(5, score);

			int success = pstmt.executeUpdate();

			if (success > 0) {
				result = new MovieVO();
				result.setMov_name(name);
				result.setMov_genre(genre);
				result.setMov_actor(actor);
				result.setMov_grade(grade);
				result.setMov_score(score);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBCUtil.close(conn, pstmt, rs);
		}
		return result;

	}

// ----------------------------------------------------------------------------------------------------------------
// delete 영화 삭제하기

	public boolean mvdelete(int mov_id) {
		boolean result = false;
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM MOVIE WHERE MOV_ID = ? ");

		try {
			conn = CommonJDBCUtil.getConnection();

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, mov_id);

			int success = pstmt.executeUpdate();

			if (success > 0) {
				result = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBCUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	
	//예약 정보를 저장하기 위한 insert
	public ReservationVO booking(int memID, int movID, String time, String seat, int price) {
		ReservationVO result = null;

		try {
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO RESERVATION ");
			sql.append("(RE_ID, RE_MEMID, RE_MOVID, RE_TIME, RE_SEAT, RE_PRICE) ");
			sql.append("VALUES ( " + "(SELECT COALESCE(MAX(RE_ID), 0) + 1 FROM RESERVATION), " + "?, " + "?, " + "?, "
					+ "?, " + "?) ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, memID);
			pstmt.setInt(2, movID);
			pstmt.setString(3, time);
			pstmt.setString(4, seat);
			pstmt.setInt(5, price);

			int success = pstmt.executeUpdate();

			if (success > 0) {
				result = new ReservationVO();
				result.setRe_memid(memID);
				result.setRe_movid(movID);
				result.setRe_time(time);
				result.setRe_seat(seat);
				result.setRe_price(price);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBCUtil.close(conn, pstmt, rs);
		}
		return result;
	}

	//예매할 때 MEMBER_ID를 가져오기 위한 select
	public MemberVO reSelectOne(String id) {
		MemberVO vo = null;

		try {
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT MEMBER_ID, CUST_ID, CUST_PASSWORD, CUST_NAME, CUST_EMAIL, CUST_PHONE ");
			sb.append("  FROM MEMBER ");
			sb.append(" WHERE CUST_ID = ? "); // CUST_ID를 기준으로 조회

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new MemberVO(rs.getInt("MEMBER_ID"), rs.getString("CUST_ID"), rs.getString("CUST_PASSWORD"),
						rs.getString("CUST_NAME"), rs.getString("CUST_EMAIL"), rs.getString("CUST_PHONE"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return vo;
	}

	public void adminShowInfo() {
		
		try {
			conn = CommonJDBCUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT ");
			sql.append("M.MEMBER_ID, M.CUST_ID, M.CUST_NAME, M.CUST_PHONE, R.RE_ID, MV.MOV_NAME, R.RE_TIME, R.RE_SEAT, M.CUST_EMAIL, R.RE_STATUS ");
			sql.append("FROM RESERVATION R ");
			sql.append("JOIN MEMBER M ON R.RE_MEMID = M.MEMBER_ID ");
			sql.append("JOIN MOVIE MV ON R.RE_MOVID = MV.MOV_ID ");
			
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			System.out.println("회원코드    아이디        성함         연락처                           이메일                                 예매번호    영화제목    예약 시간    예약 좌석    예매 상태");
			  while (rs.next()) {
				    int reid = rs.getInt("RE_ID");
				    int mcode = rs.getInt("MEMBER_ID");
				    String cid = rs.getString("CUST_ID");
			        String name = rs.getString("CUST_NAME");
			        String phone = rs.getString("CUST_PHONE");
			        String email = rs.getString("CUST_EMAIL");
			        String mvName = rs.getString("MOV_NAME");
			        String time = rs.getString("RE_TIME");
			        int seat = rs.getInt("RE_SEAT");
			        String status = rs.getString("RE_STATUS");
			        
			        System.out.println("    " +  mcode +  "           " + cid + "    "  + name +
                            "    " +    phone +                            
                            "    " +     email +
                            "    " +     reid +
                            "             " +    mvName +
                            "          " +   time +
                            "              " +   seat +
                           	"              " + status);
			        
			        			 			        
//			        System.out.println("회원코드 : " + mcode + " 아이디 : " + cid + " 고객이름 : " + name +
//			                            " 핸드폰 번호 : " + phone +
//			                            " 이메일 주소 : " + email + 
//			                            " 주문번호 : " + reid +
//			                            " 영화 제목 : " + mvName +
//			                            " 예약 시간 : " + time +
//			                            " 예약 좌석 : " + seat + " 예매 상태 : " + status);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 리소스 반납
			CommonJDBCUtil.close(conn, pstmt, rs);
		}		
	}
	
// ----------------------------------------------------------------------------------------------------------------		

}