package day0830;

/**
 * singleton pattern을 도입한 클래스
 */
public class TestSingleton {
	private static TestSingleton ts;

	// 1. 클래스 외부에서 객체화하지 못하도록 막는다. => 클래스 내부에서만 객체화가능
	private TestSingleton() {
	}// TestSingleton

	// 2.객체를 반환하는 method 작성 :
	// static 이므로 외부에서 클래스명.method명으로 호출가능
	public static TestSingleton getInstance() {
		// 3. 객체를 하나로 생성하여 반환
		if (ts == null) {// 최초호출 이거나, 객체가 없을 때 객체를 생성한다.
			ts = new TestSingleton();
		} // end if
		return ts;
	}

}
