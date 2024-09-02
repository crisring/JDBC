package day0829;

public class SingletonMain {

	public static void main(String[] args) {

		SingletonTest st1 = SingletonTest.getInstance();
		SingletonTest st2 = SingletonTest.getInstance();

		System.out.println("st1의 주소는 : " + st1 + ", st2의 주소는 : " + st2);

		if (st1 == st2) {
			System.out.println("주소가 같습니다.");
		}

	}

}
