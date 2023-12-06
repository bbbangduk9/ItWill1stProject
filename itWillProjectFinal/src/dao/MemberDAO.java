package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.MemberVO;

public class MemberDAO {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	//회원가입을 위한 insert
	public int memInsert(MemberVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO MEMBER ");
		sql.append("(MEMBER_ID, CUST_ID, CUST_PASSWORD, CUST_NAME, CUST_EMAIL, CUST_PHONE) ");
		sql.append("VALUES ((SELECT COALESCE(MAX(MEMBER_ID), 0) + 1 FROM MEMBER), ?, ?, ?, ?, ?) ");

		try {
			conn = common.CommonJDBCUtil.getConnection();

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
	public MemberVO memSelect(String id) {
		MemberVO vo = null;

		try {
			conn = common.CommonJDBCUtil.getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT CUST_ID, CUST_PASSWORD, CUST_NAME, CUST_EMAIL, CUST_PHONE ");
			sb.append("  FROM MEMBER ");
			sb.append(" WHERE CUST_ID = ? "); // CUST_ID를 기준으로 조회

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);

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

	//회원가입 시 같은 email 중복값을 비교하기 위한 select
	public MemberVO mSelectEmail(String email) {
		MemberVO vo = null;

		try {
			conn = common.CommonJDBCUtil.getConnection();

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
	public MemberVO mSelectphone(String phone) {
		MemberVO vo = null;

		try {
			conn = common.CommonJDBCUtil.getConnection();

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

	//회원정보수정(비밀번호)을 위한 UPDATE
	public int update(MemberVO vo) {
		int result = 0;

		try {
			// 2. DB연결 - Connection 객체 생성 <- DriverManager
			conn = common.CommonJDBCUtil.getConnection();

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

			conn = common.CommonJDBCUtil.getConnection();

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

			common.CommonJDBCUtil.close(conn, pstmt, rs);
		}

		return result;
	}

}