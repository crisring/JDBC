package day0829;

/**
 * singleton을 도입
 */
public class SingletonTest {

	private static SingletonTest st;

	// 생성자를 private로 객체화
	private SingletonTest() {

	}

	public static SingletonTest getInstance() {

		if (st == null) {
			st = new SingletonTest();
		}

		return st;
	}

}
