package com.example.utils;

public class PrefecthInfo {
	private String UserID;
	private String UserName;
	private String PreMoney;
	private String PreType;
	private String PreUseDate;
	private String address;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
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
	public String getPreMoney() {
		return PreMoney;
	}
	public void setPreMoney(String preMoney) {
		PreMoney = preMoney;
	}
	public String getPreType() {
		return PreType;
	}
	public void setPreType(String preType) {
		PreType = preType;
	}
	public String getPreUseDate() {
		return PreUseDate;
	}
	public void setPreUseDate(String preUseDate) {
		PreUseDate = preUseDate;
	}
	public PrefecthInfo(String userID, String userName, String preMoney,
			String preType, String preUseDate,String address) {
		UserID = userID;
		UserName = userName;
		PreMoney = preMoney;
		PreType = preType;
		PreUseDate = preUseDate;
		this.address = address;
	}
	

}
