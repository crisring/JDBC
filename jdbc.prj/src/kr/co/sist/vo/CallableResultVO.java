package kr.co.sist.vo;

public class CallableResultVO {

	private int cnt;

	public CallableResultVO(int cnt, String resultMSG) {
		super();
		this.cnt = cnt;
		this.resultMSG = resultMSG;
	}

	private String resultMSG;

	@Override
	public String toString() {
		return "CallableResultVO [cnt=" + cnt + ", resultMSG=" + resultMSG + "]";
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getResultMSG() {
		return resultMSG;
	}

	public void setResultMSG(String resultMSG) {
		this.resultMSG = resultMSG;
	}

}
