package vo;

public class MovieVO {
	private int Mov_id;
	private int Mov_grade;
	private int Mov_score;
	private String Mov_name;
	private String Mov_genre;
	private String Mov_actor;

	public MovieVO() {
	}

	//mov_grade에 NULL값을 허용하였기에 생성자를 추가함.
	public MovieVO(int mov_id, String mov_name, String mov_genre, String mov_actor, int mov_score) {
		super();
		Mov_id = mov_id;
		Mov_score = mov_score;
		Mov_name = mov_name;
		Mov_genre = mov_genre;
		Mov_actor = mov_actor;
		Mov_grade = 7;
	}

	public MovieVO(int mov_id, String mov_name, String mov_genre, String mov_actor, int mov_grade, int mov_score) {
		super();
		Mov_id = mov_id;
		Mov_name = mov_name;
		Mov_genre = mov_genre;
		Mov_actor = mov_actor;
		Mov_grade = mov_grade;
		Mov_score = mov_score;

	}

	public int getMov_id() {
		return Mov_id;
	}

	public void setMov_id(int mov_id) {
		Mov_id = mov_id;
	}

	public int getMov_grade() {
		return Mov_grade;
	}

	public void setMov_grade(int mov_grade) {
		Mov_grade = mov_grade;
	}

	public int getMov_score() {
		return Mov_score;
	}

	public void setMov_score(int mov_score) {
		Mov_score = mov_score;
	}

	public String getMov_name() {
		return Mov_name;
	}

	public void setMov_name(String mov_name) {
		Mov_name = mov_name;
	}

	public String getMov_genre() {
		return Mov_genre;
	}

	public void setMov_genre(String mov_genre) {
		Mov_genre = mov_genre;
	}

	public String getMov_actor() {
		return Mov_actor;
	}

	public void setMov_actor(String mov_actor) {
		Mov_actor = mov_actor;
	}

	@Override
	public String toString() {
		return "영화 코드 = " + Mov_id + ", 영화 제목 = " + Mov_name + ", 영화 장르 = " + Mov_genre + ", 주연 배우 = " + Mov_actor
				+ ", 영화 등급 = " + Mov_grade + "세 미만 관람불가" + ", 영화 평점 = " + Mov_score + "/10 점" + "]";
	}
}