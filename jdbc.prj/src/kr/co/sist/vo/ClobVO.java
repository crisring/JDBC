package kr.co.sist.vo;

public class ClobVO {

	private String title, contetnt, writer, input_date;

	public ClobVO() {

	}

	public ClobVO(String title, String contetnt, String writer, String input_date) {
		super();
		this.title = title;
		this.contetnt = contetnt;
		this.writer = writer;
		this.input_date = input_date;
	}

	@Override
	public String toString() {
		return "ClobVO [title=" + title + ", contetnt=" + contetnt + ", writer=" + writer + ", input_date=" + input_date
				+ "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContetnt() {
		return contetnt;
	}

	public void setContetnt(String contetnt) {
		this.contetnt = contetnt;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getInput_date() {
		return input_date;
	}

	public void setInput_date(String input_date) {
		this.input_date = input_date;
	}

}
