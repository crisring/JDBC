package kr.co.sist.vo;

public class ZipcodeVO {

	private String zipcode, side, gugun, dong, bunji;

	public ZipcodeVO() {

	}// ZipcodeVO

	public ZipcodeVO(String zipcode, String side, String gugun, String dong, String bunji) {
		super();
		this.zipcode = zipcode;
		this.side = side;
		this.gugun = gugun;
		this.dong = dong;
		this.bunji = bunji;
	}// ZipcodeVO

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getGugun() {
		return gugun;
	}

	public void setGugun(String gugun) {
		this.gugun = gugun;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getBunji() {
		return bunji;
	}

	public void setBunji(String bunji) {
		this.bunji = bunji;
	}

	@Override
	public String toString() {
		return "ZipcodeVO [zipcode=" + zipcode + ", side=" + side + ", gugun=" + gugun + ", dong=" + dong + ", bunji="
				+ bunji + "]";
	}

}// class
