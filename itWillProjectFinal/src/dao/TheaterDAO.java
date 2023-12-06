package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.CommonJDBCUtil;
import vo.TheaterVO;
import vo.TheaterVO2;

public class TheaterDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	//상영관 정보 추가(관리자권한)를 위한 insert
	public int theaterInsert(TheaterVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO THEATER ");
		sql.append("(SC_MOVID, SEAT, TIME) ");
		sql.append("VALUES (?, ?, ?) ");

		try {
			conn = common.CommonJDBCUtil.getConnection();

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, vo.getSc_movid());
			pstmt.setString(2, vo.getSc_seat());
			pstmt.setString(3, vo.getSc_time());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	//영화코드 중복확인을 위한 select
	public TheaterVO selectOne3(int movieid) {
		TheaterVO vo = null;

		conn = common.CommonJDBCUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SC_MOVID, SEAT, TIME FROM THEATER ");
		sb.append(" WHERE SC_MOVID = ? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, movieid);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new TheaterVO(rs.getInt("SC_MOVID"), rs.getString("SEAT"), rs.getString("TIME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;
	}

	//상영관 정보(time) 추가 시 중복확인을 위한 select
	public TheaterVO selectOne(String time) {
		TheaterVO vo = null;

		conn = common.CommonJDBCUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SEAT, TIME FROM THEATER ");
		sb.append(" WHERE TIME = ? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, time);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new TheaterVO(rs.getString("SEAT"), rs.getString("TIME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;
	}

	//상영관 정보(seat) 추가 시 중복확인을 위한 select
	public TheaterVO selectOne1(String seat) {
		TheaterVO vo = null;

		conn = common.CommonJDBCUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SEAT, TIME FROM THEATER ");
		sb.append(" WHERE SEAT = ? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, seat);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new TheaterVO(rs.getString("SEAT"), rs.getString("TIME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;
	}

	//상영관 정보(time)를 확인할수있는 select
	public List<TheaterVO> selectOne4(int movieid) {
		List<TheaterVO> list = null;

		conn = common.CommonJDBCUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT TIME FROM THEATER ");
		sb.append(" WHERE SC_MOVID = ? ORDER BY TIME ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, movieid);
			rs = pstmt.executeQuery();

			list = new ArrayList<TheaterVO>();
			while (rs.next()) {
				TheaterVO vo = new TheaterVO(rs.getString("TIME"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 시간을 입력했을 때 잔여좌석을 보여주기 위한 select(join사용)
	public List<TheaterVO2> selectSeat(String time, int movid) {
		List<TheaterVO2> list = null;

		TheaterVO2 vo = null;

		conn = common.CommonJDBCUtil.getConnection();

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT T.SEAT ");
		sb.append("FROM ( ");
		sb.append("    SELECT SC_MOVID, TIME, SEAT ");
		sb.append("    FROM THEATER ");
		sb.append("    WHERE TIME = ? AND SC_MOVID = ? ");
		sb.append(") T ");
		sb.append("LEFT JOIN RESERVATION R ");
		sb.append("ON T.TIME = R.RE_TIME AND T.SEAT = R.RE_SEAT ");
		sb.append("AND T.SC_MOVID = R.RE_MOVID ");
		sb.append("WHERE R.RE_TIME IS NULL ");
		sb.append("ORDER BY TO_NUMBER(T.SEAT) ");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, time);
			pstmt.setInt(2, movid);

			rs = pstmt.executeQuery();
			list = new ArrayList<TheaterVO2>();

			while (rs.next()) {
				vo = new TheaterVO2(rs.getString("SEAT"));
				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// 여기다가 메서드를 넣어??
		}
		return list;
	}
}