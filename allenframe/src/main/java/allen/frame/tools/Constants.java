package allen.frame.tools;


import allen.frame.AllenManager;

//常量常数
public class Constants {

//	public static String url = "http://222.179.3.62:30050";//正式
	public static String url = "http://150.158.184.184:30050";//测试
	public static boolean ISDEBUG = true;
	public static int version = 0;//0公众版，1管理员版，2领导版

	// APP下载文件夹
	public static final String APPFILE_NAME = "appfile";
	public static final String APPFILE_DOWNLOAD = "download";

	public static boolean APP_DOWNLOAD_SERVICE = false;

	/*传递常用参数 start*/
	public static final String ObjectFirst = "Object_First";//传递对象
	public static final String ObjectSecend = "Object_Secend";//传递对象
	public static final String Key_1 = "_Key_1";//传递参数1
	public static final String Key_2 = "_Key_2";//传递参数2
	public static final String Key_3 = "_Key_3";//传递参数3
	public static final String Key_4 = "_Key_4";//传递参数4
	public static final String Key_Token = "_Key_Token";//token过期标识
	public static final int KeyTokenFlag = 0X11;//token过期跳转登录界面请求标识
	/*传递常用参数 end*/

	/*权限申请code,跳转返回code start*/
	public static final int PERMISSION_REQUEST_ONE = 0X01;
	public static final int PERMISSION_REQUEST_TWO = 0X02;
	public static final int PERMISSION_REQUEST_THREE = 0X03;

	public static final String STH_ID = "sth_id";//事项ID传递标识

	public static final int REQUEST_CODE_SCAN = 0X101;
	public static final int REQUEST_CODE_IMAGE = 0X102;
	public static final int REQUEST_CODE_OTHRE_ONE = 0X103;
	public static final int REQUEST_CODE_OTHRE_TWO = 0X104;
	public static final int REQUEST_CODE_OTHRE_THREE = 0X105;
	/*权限申请code,跳转返回code end*/

	public static final String UserToken = "_User_Token";
	public static final String UserPhone = "_User_Phone";
	public static final String UserPsw = "_User_Psw";
	public static final String UserId = "_User_Id";

	public static final String UserUnitsId = "_User_Units_ID";//用户id
	public static final String UserUnitsNO = "_User_Units_NO";//用户No
	public static final String UserUnitsName = "_User_Units_Name";//用户单位
	public static final String UserName = "_User_Name";//用户名
	public static final String UserPhoto = "_User_Photo";
	public static final String UserAddress = "_User_Address";
	public static final String UserEmail = "_User_email";
	public static final String UserGender = "_User_gender";//性别
	public static final String UserNickName = "_User_nickName";//昵称
	public static final String UserGrage = "_User_Grage";//用户性质
	public static final String UserRoleName = "_User_role_name";//用户角色

	public static final String UserWgUser = "_User_Wg_User";//是否网格员标签

	public static final String UserMap = "_User_Map";//地图巡逻


	public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/NqHjZQTA/NuOeAalhgb20wSoLe8NDNSYGbaOL+wu8s3X++BUezRiBiHdfvp55LNCC2Ua422Bu1UnZgwolcWhKg3ZFTLH47eX5adfrV5R/IpGZxh6ZebsioJGTeCQKq6ZB08jHdq6RBv7Fe0Q2nmW7P2hGblGSHuevMjaJ1rPFwIDAQAB";

}
