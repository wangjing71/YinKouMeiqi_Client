package com.example.utils;

public class UserInfo {

	private String UserID;
	private String UserName;
	private String Address;
	private String watertype;
	private String PrepaySum;

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getWatertype() {
		return watertype;
	}

	public void setWatertype(String watertype) {
		this.watertype = watertype;
	}

	public String getPrepaySum() {
		return PrepaySum;
	}

	public void setPrepaySum(String prepaySum) {
		PrepaySum = prepaySum;
	}

	public UserInfo(String userID, String userName, String address,
			String watertype, String prepaySum) {
		super();
		UserID = userID;
		UserName = userName;
		Address = address;
		this.watertype = watertype;
		PrepaySum = prepaySum;
	}

}
