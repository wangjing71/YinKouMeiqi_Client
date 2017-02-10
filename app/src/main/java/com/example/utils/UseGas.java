package com.example.utils;

public class UseGas {
	private String UserID;
	private String UserName;
	private String address;
	private String CbData;
	private String LastData;
	private String BCGasNum;
	private String LastGasNum;
	private String waternumber;
	private String Total;
	private String watertype;
	private String nowChange;
	private String lastChange;
	private String  payyesno;
	private String paydate;
	
	
	
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	public String getNowChange() {
		return nowChange;
	}
	public void setNowChange(String nowChange) {
		this.nowChange = nowChange;
	}
	public String getLastChange() {
		return lastChange;
	}
	public void setLastChange(String lastChange) {
		this.lastChange = lastChange;
	}
	public String getPayyesno() {
		return payyesno;
	}
	public void setPayyesno(String payyesno) {
		this.payyesno = payyesno;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCbData() {
		return CbData;
	}
	public void setCbData(String cbData) {
		CbData = cbData;
	}
	public String getLastData() {
		return LastData;
	}
	public void setLastData(String lastData) {
		LastData = lastData;
	}
	public String getBCGasNum() {
		return BCGasNum;
	}
	public void setBCGasNum(String bCGasNum) {
		BCGasNum = bCGasNum;
	}
	public String getLastGasNum() {
		return LastGasNum;
	}
	public void setLastGasNum(String lastGasNum) {
		LastGasNum = lastGasNum;
	}
	public String getWaternumber() {
		return waternumber;
	}
	public void setWaternumber(String waternumber) {
		this.waternumber = waternumber;
	}
	public String getTotal() {
		return Total;
	}
	public void setTotal(String total) {
		Total = total;
	}
	public String getWatertype() {
		return watertype;
	}
	public void setWatertype(String watertype) {
		this.watertype = watertype;
	}
	public UseGas(String userID, String userName, String address,
			String cbData, String lastData, String bCGasNum, String lastGasNum,
			String waternumber, String total, String watertype,
			String nowChange, String lastChange, String payyesno,String paydate) {
		UserID = userID;
		UserName = userName;
		this.address = address;
		CbData = cbData;
		LastData = lastData;
		BCGasNum = bCGasNum;
		LastGasNum = lastGasNum;
		this.waternumber = waternumber;
		Total = total;
		this.watertype = watertype;
		this.nowChange = nowChange;
		this.lastChange = lastChange;
		this.payyesno = payyesno;
		this.paydate = paydate;
	}

	
	
	
}
