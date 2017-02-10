package com.example.utils;

public class Url {

	// 数据库服务器主机地址
//	public static String hostaddress = "http://60.23.197.7:8080/";
	public static String hostaddress = "http://yinkou.nat123.net/";
	// 更新服务器主机地址
	public static String updateaddress = "http://60.23.197.7:8080/";
	// 当前版本号
	public static final String CurrentVersion = "v1.0.1";
	// 软件到期时间
	public static String expiringdate = "2099.12.30.23.59.59";
	// 检查更新网址
	public static final String CheckUpdateURL = updateaddress
			+ "MyUpdateService/update?sign=checkversion&UserID=0";
	public static String JTJJ = "http://www.yingkougas.com/jtgk/";
	public static String QYWH = "http://www.yingkougas.com/jtgk/index3.php";
	public static String JGSZ = "http://www.yingkougas.com/jtgk/index4.php";
	public static String DHDZ = "http://www.yingkougas.com/fwzn/";
	public static String SFBZ = "http://www.yingkougas.com/fwzn/index4.php";
	public static String ZZFW = "http://www.yingkougas.com/jtgk/index5.php";
	public static String FWCN = "http://www.yingkougas.com/fwzn/index2.php";
	public static String FWLC = "http://www.yingkougas.com/fwzn/index3.php";
	public static String AQCS = "http://www.yingkougas.com/aqzs/";
	public static String YJCL = "http://www.yingkougas.com/aqzs/index2.php";
	public static String YHXZ = "http://www.yingkougas.com/aqzs/index3.php";
	public static String GetUserInfoUrl = hostaddress
			+ "YKService/yk?sign=userinfoquery&UserID=";
	public static String GetGasInfoUrl = hostaddress
			+ "YKService/yk?sign=gasinfoquery&UserID=";
	public static String GetArrearageInfoUrl = hostaddress
			+ "YKService/yk?sign=balancequery&UserID=";
	public static String GetPrefecthInfoUrl = hostaddress
			+ "YKService/yk?sign=prefecthquery&UserID=";
	public static String GetQianfei = hostaddress
			+ "YKService/yk?sign=qianfei&UserID=";

}
