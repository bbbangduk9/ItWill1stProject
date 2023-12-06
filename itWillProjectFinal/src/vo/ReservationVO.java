package vo;

public class ReservationVO {
	private int re_id;
	private int re_memid;
	private int re_movid;
	private String re_time;
	private String re_seat;
	private int re_price;

	private String Mov_name;
	private String custid;
	private String re_status;


	//------------------------------------
	public String getRe_status() {
		return re_status;
	}

	public void setRe_status(String re_status) {
		this.re_status = re_status;
	}

	//public ReservationVO(int re_id, String custid, String Mov_name,  String re_sctime,  String re_scseat, String re_status) {
//		// TODO Auto-generated constructor stub
//		super();
//		this.re_id = re_id;
//		this.custid = custid;
//		this.Mov_name = Mov_name;
//		this.re_time = re_sctime;
//		this.re_seat = re_scseat;
//		this.re_status = re_status;
	//}

	public ReservationVO(int re_id, String re_status, String custid, String Mov_name,  String re_sctime,  String re_scseat ) {
		// TODO Auto-generated constructor stub
		super();
		this.re_id = re_id;
		this.re_status = re_status;
		this.custid = custid;
		this.Mov_name = Mov_name;
		this.re_time = re_sctime;
		this.re_seat = re_scseat;
	}

	//-------------------------------------
	
	
	public ReservationVO(int re_id, String custid, String Mov_name, String re_sctime, String re_scseat) {
		super();
		this.re_id = re_id;
		this.custid = custid;
		this.Mov_name = Mov_name;
		this.re_time = re_sctime;
		this.re_seat = re_scseat;
	}

	public ReservationVO(int re_id, int re_memid, int re_movid, String re_time, String re_seat, int re_price) {
		super();
		this.re_id = re_id;
		this.re_memid = re_memid;
		this.re_movid = re_movid;
		this.re_time = re_time;
		this.re_seat = re_seat;
		this.re_price = re_price;
	}

	public ReservationVO() {
		super();
		this.re_memid = re_memid;
		this.re_movid = re_movid;
		this.re_time = re_time;
		this.re_seat = re_seat;
		this.re_price = re_price;
	}

	public String getMov_name() {
		return Mov_name;
	}

	public void setMov_name(String mov_name) {
		Mov_name = mov_name;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public int getRe_id() {
		return re_id;
	}

	public void setRe_id(int re_id) {
		this.re_id = re_id;
	}

	public int getRe_memid() {
		return re_memid;
	}

	public void setRe_memid(int re_memid) {
		this.re_memid = re_memid;
	}

	public int getRe_movid() {
		return re_movid;
	}

	public void setRe_movid(int re_movid) {
		this.re_movid = re_movid;
	}

	public String getRe_time() {
		return re_time;
	}

	public void setRe_time(String re_time) {
		this.re_time = re_time;
	}

	public String getRe_seat() {
		return re_seat;
	}

	public void setRe_seat(String seat) {
		this.re_seat = seat;
	}

	public int getRe_price() {
		return re_price;
	}

	public void setRe_price(int re_price) {
		this.re_price = re_price;
	}

	@Override
	public String toString() {
		return "ReservationVO [re_id=" + re_id + ", re_memid=" + re_memid + ", re_movid=" + re_movid + ", re_sctime="
				+ re_time + ", re_scseat=" + re_seat + "]";
	}
}
