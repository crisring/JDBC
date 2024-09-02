package day0830;

public class UseTestSingleton {

	public static void main(String[] args) {
//		new TestSingleton();//생성자가 private이므로 외부에서는 접근 불가.
		
		TestSingleton ts=TestSingleton.getInstance();
		TestSingleton ts2=TestSingleton.getInstance();
		TestSingleton ts3=TestSingleton.getInstance();
		
		
		// 다 같은 주소 = 객체화가 한 개만 했음
		System.out.println("ts객체 : "+ ts);
		System.out.println("ts2객체 : "+ ts2);
		System.out.println("ts3객체 : "+ ts3);
	}//main

}//class
