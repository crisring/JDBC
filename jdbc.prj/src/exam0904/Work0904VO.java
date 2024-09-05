package exam0904;

import java.sql.Date;

public class Work0904VO {

	// 번호, 이름, 이메일, 전화번호, 우편번호, 입력일

	private String num, name, email, phone_Number;
	private int seq;
	private Date input_date;

	public Work0904VO() {

	}

	public Work0904VO(String name, String email, String phone_Number, int seq) {
		super();
		this.name = name;
		this.email = email;
		this.phone_Number = phone_Number;
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_Number() {
		return phone_Number;
	}

	public void setPhone_Number(String phone_Number) {
		this.phone_Number = phone_Number;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Date getInput_date() {
		return input_date;
	}

	public void setInput_date(Date input_date) {
		this.input_date = input_date;
	}

}
