package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.CommonJDBCUtil;
import vo.ReservationVO;

public class ReservationDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	//예매 정보를 등록할 insert
	public ReservationVO booking(int memID, int movID, String time, String seat, int price) {
		ReservationVO result = null;

		try {
			conn = CommonJDBCUtil.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO RESERVATION ");
			sql.append("(RE_ID, RE_MEMID, RE_MOVID, RE_TIME, RE_SEAT, RE_PRICE ) ");
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

	//예매조회를 하기 위한 select
	public ReservationVO selectAll(String id) {

		conn = CommonJDBCUtil.getConnection();
		ReservationVO vo = null;

		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT R.RE_ID, B.CUST_ID, M.MOV_NAME, R.RE_TIME, R.RE_SEAT ");
			sb.append(" FROM RESERVATION R, MOVIE M, MEMBER B ");
			sb.append(" WHERE R.RE_MEMID = M.MOV_ID AND RE_MEMID = MEMBER_ID ");
			sb.append(" AND B.CUST_ID  = ? ORDER BY R.RE_ID ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ReservationVO(rs.getInt("RE_ID"), rs.getString("CUST_ID"), rs.getString("MOV_NAME"),
						rs.getString("RE_TIME"), rs.getString("RE_SEAT"));

				System.out.println("예매번호 : " + vo.getRe_id() + "번");
				System.out.println("아이디 : " + vo.getCustid());
				System.out.println("영화 제목 : " + vo.getMov_name());
				System.out.println("상영시각 : " + vo.getRe_time());
				System.out.println("좌석번호 : " + vo.getRe_seat() + "번");
				System.out.println("-----------");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBCUtil.close(conn, pstmt, rs);
		}

		return vo;
	}

	//예매 취소를 위한 delete
	//	public ReservationVO bookingCancel(int re_id) {
	//
	//		conn = CommonJDBCUtil.getConnection();
	//		ReservationVO vo = null;
	//
	//		try {
	//			StringBuilder sql = new StringBuilder();
	//			sql.append("DELETE FROM RESERVATION WHERE RE_ID = ? ");
	//
	//			pstmt = conn.prepareStatement(sql.toString());
	//
	//			pstmt.setInt(1, re_id);
	//
	//			rs = pstmt.executeQuery();
	//
	//		} catch (SQLException e) {
	//			e.printStackTrace();
	//
	//		} finally {
	//			CommonJDBCUtil.close(conn, pstmt, rs);
	//		}
	//		return vo;
	//	}

	public ReservationVO bookingCancel(int re_id) {

		conn = CommonJDBCUtil.getConnection();
		ReservationVO vo = null;

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("UPDATE RESERVATION  ");		
			sql.append("SET RE_STATUS = '예매취소' ");
			sql.append("WHERE RE_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, re_id);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			CommonJDBCUtil.close(conn, pstmt, rs);
		}
		return vo;
	}

	//	예매취소 view
	public ReservationVO bookingCancelView(int re_id) {
		conn = CommonJDBCUtil.getConnection();
		ReservationVO vo = null;

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM RESERVATION_CANCEL WHERE RE_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, re_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new ReservationVO(rs.getInt("RE_ID")
						,rs.getString("RE_STATUS")
						,rs.getString("CUST_ID")
						,rs.getString("MOV_NAME")
						,rs.getString("RE_TIME")
						,rs.getString("RE_SEAT")
						);
			}		
			System.out.println("예매번호 : " + vo.getRe_id() + "번 " + "상태 : "
					+ vo.getRe_status() + " " + " 아이디 : " + vo.getCustid() + " " +  "영화 제목 : " + vo.getMov_name() +" " + "상영 시각 : "
					+ vo.getRe_time() + " " + "좌석 번호 : " + vo.getRe_seat() );

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}  finally {
			CommonJDBCUtil.close(conn, pstmt, rs);
		}

		return vo;

	}


}
