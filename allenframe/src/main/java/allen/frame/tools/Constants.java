package allen.frame.tools;


import allen.frame.AllenManager;

//常量常数
public class Constants {

	public static String url = "http://150.158.184.184:30050";
	public static boolean ISDEBUG = false;

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
	/*传递常用参数 end*/

	/*权限申请code,跳转返回code start*/
	public static final int PERMISSION_REQUEST_ONE = 0X01;
	public static final int PERMISSION_REQUEST_TWO = 0X02;
	public static final int PERMISSION_REQUEST_THREE = 0X03;

	public static final String UserName = "User_Name";//用户名
	public static final String UserIsPeaceman = "UserIsPeaceman";
	public static final String UserIsAdmin = "UserIsAdmin";
	public static final String UserPhoto = "UserPhoto";


	public static final String USER_ID = "User_ID";//用户ID
	public static final String USER_UNITCODE = "User_UnitCode";//用户单位ID
	public static final String USER_UNITNAME = "User_UnitName";//用户单位名
	public static final String USER_UNITTYPE = "User_UnitType";//用户单位类型(1,街道2科室3社区)

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

}
