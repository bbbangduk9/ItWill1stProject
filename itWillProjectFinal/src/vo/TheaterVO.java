package vo;

public class TheaterVO {
	private String sc_time;
	private String sc_seat;
	private int sc_movid;

	public TheaterVO(String sc_time, String sc_seat) {
		super();
		this.sc_time = sc_time;
		this.sc_seat = sc_seat;
	}

	public TheaterVO(int sc_movid, String sc_time, String sc_seat) {
		super();
		this.sc_time = sc_time;
		this.sc_seat = sc_seat;
		this.sc_movid = sc_movid;
	}

	public TheaterVO(String sc_time) {
		super();
		this.sc_time = sc_time;
	}

	public String getSc_time() {
		return sc_time;
	}

	public void setSc_time(String sc_time) {
		this.sc_time = sc_time;
	}

	public String getSc_seat() {
		return sc_seat;
	}

	public void setSc_seat(String sc_seat) {
		this.sc_seat = sc_seat;
	}

	public int getSc_movid() {
		return sc_movid;
	}

	public void setSc_movid(int sc_movid) {
		this.sc_movid = sc_movid;
	}

	@Override
	public String toString() {
		return "관람시간 : " + sc_time;
	}

}