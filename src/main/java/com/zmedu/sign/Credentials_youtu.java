package com.zmedu.sign;

public class Credentials_youtu {
	private String APP_ID = "10123620";
	private String SECRET_ID = "AKIDc9Wjm32gtxwpX4tlqkT6ZCh4GH7sitBw";
	private String SECRET_KEY ="yrjWKyZ7Vipz0rX2MVTGtMeruoXN4bxD";
	private String USER_ID =  "646911949";

	public Credentials_youtu() {
		super();
	}

	public Credentials_youtu(String aPP_ID, String sECRET_ID, String sECRET_KEY, String uSER_ID) {
		super();
		APP_ID = aPP_ID;
		SECRET_ID = sECRET_ID;
		SECRET_KEY = sECRET_KEY;
		USER_ID = uSER_ID;
	}

	public String getAPP_ID() {
		return APP_ID;
	}

	public void setAPP_ID(String aPP_ID) {
		APP_ID = aPP_ID;
	}

	public String getSECRET_ID() {
		return SECRET_ID;
	}

	public void setSECRET_ID(String sECRET_ID) {
		SECRET_ID = sECRET_ID;
	}

	public String getSECRET_KEY() {
		return SECRET_KEY;
	}

	public void setSECRET_KEY(String sECRET_KEY) {
		SECRET_KEY = sECRET_KEY;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

}
