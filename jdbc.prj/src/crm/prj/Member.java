package crm.prj;

/**
 * 사용자가 입력한 회원 정보를 저장할 수 있도록 private 멤버변수(필드)를 선언한다. <br>
 * private 멤버 필드에 접근하기 위한 Getter 메소드와 Setter 메소드를 선언한다. <br>
 * Member 객체의 상태를 문자열로 리턴하는 toString() 메소드를 재정의(Overriding)한다. <br>
 * 
 */
public class Member {

	private String member_ID;
	private String name;
	private String phone_Number;

	@Override
	public String toString() {
		return "Member [member_ID=" + member_ID + ", name=" + name + ", phone_Number=" + phone_Number + "]";
	}

	public String getMember_ID() {
		return member_ID;
	}

	public void setMember_ID(String member_ID) {
		this.member_ID = member_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone_Number() {
		return phone_Number;
	}

	public void setPhone_Number(String phone_Number) {
		this.phone_Number = phone_Number;
	}

}
