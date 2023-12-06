package vo;

public class MemberVO {
	private int Member_ID;
	private String Cust_id;
	private String Cust_password;
	private String Cust_name;
	private String Cust_email;
	private String Cust_phone;

	public MemberVO(String cust_name, String cust_phone, String cust_email) {
		super();
		Cust_name = cust_name;
		Cust_phone = cust_phone;
		Cust_email = cust_email;
	}

	public MemberVO(String cust_id, String cust_password) {
		super();
		Cust_id = cust_id;
		Cust_password = cust_password;
	}

	public MemberVO() {
	}

	public MemberVO(int member_ID, String cust_id, String cust_password, String cust_name, String cust_email,
			String cust_phone) {
		super();
		Member_ID = member_ID;
		Cust_id = cust_id;
		Cust_password = cust_password;
		Cust_name = cust_name;
		Cust_email = cust_email;
		Cust_phone = cust_phone;
	}

	public MemberVO(String cust_id, String cust_password, String cust_name, String cust_email, String cust_phone) {
		super();
		Cust_id = cust_id;
		Cust_password = cust_password;
		Cust_name = cust_name;
		Cust_email = cust_email;
		Cust_phone = cust_phone;

	}

	public int getMember_ID() {
		return Member_ID;
	}

	public void setMember_ID(int member_ID) {
		Member_ID = member_ID;
	}

	public String getCust_id() {
		return Cust_id;
	}

	public void setCust_id(String cust_id) {
		Cust_id = cust_id;
	}

	public String getCust_password() {
		return Cust_password;
	}

	public void setCust_password(String cust_password) {
		Cust_password = cust_password;
	}

	public String getCust_name() {
		return Cust_name;
	}

	public void setCust_name(String cust_name) {
		Cust_name = cust_name;
	}

	public String getCust_email() {
		return Cust_email;
	}

	public void setCust_email(String cust_email) {
		Cust_email = cust_email;
	}

	public String getCust_phone() {
		return Cust_phone;
	}

	public void setCust_phone(String cust_phone) {
		Cust_phone = cust_phone;
	}

	@Override
	public String toString() {
		return "아이디 = " + Cust_id + ", 비밀번호 = " + Cust_password + ", 회원이름 = " + Cust_name + ", 이메일 = " + Cust_email
				+ ", 전화번호 = " + Cust_phone + "]";
	}

}