package com.example.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

	public ArrayList<String> getUserInfoList(String json) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject str = array.getJSONObject(i);
				String userID = str.getString("userID");
				String userName = str.getString("userName");
				String address = str.getString("address");
				String telephone = str.getString("telephone");
				String watertype = str.getString("watertype");
				String area = str.getString("area");
				String prepaySum = str.getString("prepaySum");
				String fgUserStart = str.getString("fgUserStart");
				list.add(userID);
				list.add(userName);
				list.add(address);
				list.add(telephone);
				list.add(watertype);
				list.add(area);
				list.add(prepaySum);
				list.add(fgUserStart);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getUsegasInfoList(String json) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject str = array.getJSONObject(i);
				String UserID = str.getString("userID");
				String UserName = str.getString("userName");
				String CbData = str.getString("cbData");
				String LastData = str.getString("lastData");

				String BCGasNum = str.getString("BCGasNum");
				String LastGasNum = str.getString("lastGasNum");
				String Total = str.getString("total");
				String watertype = str.getString("watertype");
				list.add(UserID);
				list.add(UserName);
				list.add(CbData);
				list.add(LastData);
				list.add(BCGasNum);
				list.add(LastGasNum);
				list.add(Total);
				list.add(watertype);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getArreargeInfoList(String json) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject str = array.getJSONObject(i);
				String lastChange = str.getString("lastChange");
				String lastTabdate = str.getString("lastTabdate");
				String nowChange = str.getString("nowChange");
				String totMoneyDx = str.getString("totMoneyDx");
				String userID = str.getString("userID");
				String userName = str.getString("userName");
				String waterNumber = str.getString("waterNumber");
				String waterType = str.getString("waterType");
				list.add(userID);
				list.add(userName);
				list.add(nowChange);
				list.add(lastChange);
				list.add(waterType);
				list.add(lastTabdate);
				list.add(waterNumber);
				list.add(totMoneyDx);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	//TODO 解析预存信息
	public ArrayList<PrefecthInfo> getPrefecthInfo(String json) {
		ArrayList<PrefecthInfo> list = new ArrayList<PrefecthInfo>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject str = array.getJSONObject(i);
				list.add(new PrefecthInfo(str.getString("userID"), str.getString("userName"), str.getString("preMoney"), str.getString("preType"), str.getString("preUseDate"), str.getString("address")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//解析用气信息
	public ArrayList<UseGas> getgasuselist(String json){
		ArrayList<UseGas> list = new ArrayList<UseGas>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject str = array.getJSONObject(i);
				
				list.add(new UseGas(str.getString("userID"), str.getString("userName"), 
						str.getString("address"), str.getString("cbData"), str.getString("lastData"), 
						str.getString("BCGasNum"), str.getString("lastGasNum"), str.getString("waternumber"), 
						str.getString("total"), str.getString("watertype"), str.getString("nowChange"), str.getString("lastChange"), str.getString("payyesno"),str.getString("paydate")));
				
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	//解析欠费信息
	public ArrayList<UseGas> getTarrearage(String json){
		ArrayList<UseGas> list = new ArrayList<UseGas>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject str = array.getJSONObject(i);
				list.add(new UseGas(str.getString("userID"), str.getString("userName"), 
						str.getString("address"), str.getString("cbData"), str.getString("lastData"), 
						str.getString("BCGasNum"), str.getString("lastGasNum"), str.getString("waternumber"), 
						str.getString("total"), str.getString("watertype"), str.getString("nowChange"), str.getString("lastChange"), str.getString("payyesno"),str.getString("paydate")));
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
		
	}
}
