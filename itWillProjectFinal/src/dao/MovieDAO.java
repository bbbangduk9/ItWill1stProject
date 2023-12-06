package dao;

// 영화 전체 리스트 불러오기

//수정할 점: 콘솔에 한줄씩 나오게 하기

import java.io.InputStreamReader;
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

public class MovieDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public ArrayList<MovieVO> mvSelectAll() {
		String sql = "SELECT * FROM MOVIE ";
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


	// 영화 제목으로 검색하기 위한 select
	public ArrayList<MovieVO> mvSelectOne(String title) {
		MovieVO result = null;
		ArrayList<MovieVO> movList = new ArrayList<>();

		try {
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

	// 영화 식별자 키로 영화를 찾기 위한 select
	public int mvSelectOne(int mov_id) {
		int result = 0;

		try {

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

	// 별점순으로 영화 리스트를 보기 위한 select

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

	//영화 등록을 위한 insert
	public MovieVO mvInsert(String name, String genre, String actor, int grade, int score) {
		MovieVO result = null;

		try {
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO MOVIE ");
			sql.append("(Mov_ID, Mov_name, Mov_genre, Mov_actor, Mov_grade, Mov_score) ");
			sql.append("VALUES ((SELECT COALESCE(MAX(MOV_ID), 0) + 1 FROM MOVIE), ?, ?, ?, ?, ?) ");
			//SELECT COALESCE(MAX(MOV_ID), 0) + 1 FROM MOVIE
			//현존하는 시퀀스의 맥스값에서 +1하여 생성. 데이터 삭제해도 숫자를 이어나갈 수 있음.
			//하지만 여러 스레드 또는 동시성 환경에서 충돌이 발생할 수 있으므로 주의
			
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

	//delete 영화 삭제를 위한 delete
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

	//예매할 때 MEMBER_ID가져오기 위한 select
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

}
