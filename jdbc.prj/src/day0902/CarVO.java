package day0902;

public class CarVO {
	private int car_year, price;
	private String country, maker, car_option, model;

	public CarVO(int car_year, int price, String country, String maker, String car_option, String model) {
		super();
		this.car_year = car_year;
		this.price = price;
		this.country = country;
		this.maker = maker;
		this.car_option = car_option;
		this.model = model;
	}

	public int getCar_year() {
		return car_year;
	}

	public void setCar_year(int car_year) {
		this.car_year = car_year;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getCar_option() {
		return car_option;
	}

	public void setCar_option(String car_option) {
		this.car_option = car_option;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}// class
