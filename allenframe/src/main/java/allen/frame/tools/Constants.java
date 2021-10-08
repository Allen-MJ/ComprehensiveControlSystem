package allen.frame.tools;


import allen.frame.AllenManager;

//常量常数
public class Constants {

	public static final String tyurl = "http://gxzyapp.wangan.cn/";//通用服务地址
	public static String url = "http://oa.wangan.cn:8089/";//公司外网
	public static boolean ISDEBUG = false;
	public static String JPUSHTAG = "allen_jpush_tag";//本地
//	public static String image_path = AllenManager.getInstance().getStoragePreference().getString(Constants.SERVER_URL, "");

    //通用版本基本设置
    public static String SERVER_URL = "edu_server_url";//服务地址
    public static String EDU_NAME = "edu_server_name";//学校名字
    public static String EDU_ROLE = "edu_server_role";//开通权限

	public static String ACTIVITY_TITLE = "Activity_title";

	// public static String INTENT_IP="192.168.0.146";
	public static String INTENT_IP = "192.168.0.176";
	public static String INTENT_PORT = "29865";

	// 界面间标识
	public static final int QRCODE_CODE = 110;// 二维码扫描
	public static final int CLS_CREAT_REQUEST_CODE = 20;// 创建班课
	public static final String CLS_EXIT_ACTION = "CLS_EXIT_ACTION";// 退出班课
	public static final String TOUPING_ACTION = "TOUPING_ACTION";//投屏
	public static final String TOUPING_EXIT_ACTION = "TOUPING_EXIT_ACTION";//投屏退出
	public static final int CLS_CHOICE_GRADE_REQUEST_CODE = 21;// 选择专业
	public static final int CLS_CHOICE_STUDENT_REQUEST_CODE = 22;// 选择学生
	public static final int CLS_CHOICE_KC_REQUEST_CODE = 23;// 选择课程
	public static final String CLS_CHOICE_GRADE_REQUEST_DATA = "CLS_CHOICE_GRADE_REQUEST_DATA";// 选择专业
	public static final int STU_CREAT_REQUEST_CODE = 30;// 创建学习圈
	public static final int STU_PL_REQUEST_CODE = 32;// 学习圈评论
	public static final int STU_PIC_REQUEST_CODE = 33;// 学习圈图片选择
	public static final String STU_RICIEVE_ACTION = "STU_RICIEVE_ACTION";// 学习圈通知
	public static final int STU_NAME_EDITE_CODE = 34;// 学习圈名称编辑
	public static final int STU_DETAIL_REQUEST_CODE = 35;// 打开学习圈后有处理
	public static final int MSG_REQUEST_CODE = 101;// 消息详情跳转
	public static final int USER_EDIT_REQUEST_CODE = 102;// 个人信息编辑跳转
	public static final int USER_PHOTO_REQUEST_CODE = 103;// 个人头像跳转
	public static final int USER_FACE_REQUEST_CODE = 104;// 人脸采集跳转

	public static final int QRCODE_TYPE_PC_LOGIN = -1;
	public static final int QRCODE_TYPE_APP_LOGIN = 1;
	public static final int QRCODE_TYPE_CLASS = 2;
	public static final int QRCODE_TYPE_PERSON = 3;
	public static final int QRCODE_TYPE_QZ = 4;
	public static final int QRCODE_TYPE_TEACH = 5;

	// 用户权限
	public static String LOGIN_USER_RID = "LOGIN_USER_RID";
	// 权限类型 RID
	public static final String USER_GUEST = "ce70b31b-914f-4bb5-8d11-d37c014695be";// 游客
	public static final String USER_STUDENTE = "3BB36E82-72A0-4232-9A40-30B2FD40271F";
	public static final String USER_TEACHER = "5328AE71-1174-4C6A-84B9-A3F18D6D0FF1";
	public static final String USER_MANEGER = "1E6E7CD0-AAB1-4F2B-BAB7-C5D0000804BF";
	public static final String USER_SYSTEM = "E15EAA46-1D2E-41ED-9D2F-0064BBFD3750";

	// 用户权限
	public static String LOGIN_USER_ROLE = "LOGIN_USER_ROLE";
	// 权限类型 RID
	public static final int USER_ROLE_GUEST = -1;// 游客
	public static final int USER_ROLE_STUDENTE = 3;
	public static final int USER_ROLE_TEACHER = 2;
	public static final int USER_ROLE_MANEGER = 1;
	public static final int USER_ROLE_SUPER_MANEGER = 0;
	public static final int USER_ROLE_SYSTEM = 6;

	// 评价类型 菜单ID
	public static final String EST_TYPE_FQPJ = "1B42795B-46BE-4E68-BC63-535173C9E706";// 发起评价
	public static final String EST_TYPE_LXGL = "55DA5BA4-A83C-4A73-8737-6E5A39A0D5C0";// 评价类型管理
	public static final String EST_TYPE_XSPJ = "BD98C319-513A-42BA-BADE-CB478D8966A0";// 学生评教
	public static final String EST_TYPE_JSPX = "517171A0-79FF-4A79-A2E8-37AD2F19FD1A";// 教师评学
	public static final String EST_TYPE_THHP = "C949B6BF-1300-433C-B6EC-70D46871B8BD";// 同行互评
	public static final String EST_TYPE_DDPJ = "2EA5702B-C23C-45B6-A848-132547E6E4DA";// 督导评教
	public static final String EST_TYPE_PJFX = "668880CB-EC8C-41EA-9DA7-BBA2B0D0BADB";// 教学评价统计
	public static final String EST_TYPE_WDPJ = "A000B379-E8A0-4186-AD06-F1DE9C72B42C";// 我的评价
	public static final String EST_TYPE_ZLC = "39F9D9D1-8CAE-4988-B615-D6A3A041275F";// 质量处评价
	// 评价类型ID（督导评教和同行互评的固定ID）
	public static final String EST_TYPEID_THHP = "e5b4ff0a-dc37-4a6b-bde8-257c57fa3a5b";// 督导评教
	public static final String EST_TYPEID_DDPJ = "3f00b5bb-2898-42df-a0e4-63806ae0ad87";// 同行互评

	public static final String CES_ID = "AFB7F037-6A09-4209-83B2-2E465B87262B";

	public static String LOGIN_USER_ID = "LOGIN_USER_ID";
	public static String LOGIN_USER_ZH = "LOGIN_USER_ZH";
	public static String LOGIN_USER_NAME = "LOGIN_USER_NAME";
	public static String LOGIN_USER_ICON = "LOGIN_USER_ICON";
	public static String LOGIN_USER_GXQM = "LOGIN_USER_GXQM";
	public static String LOGIN_USER_MMXG = "LOGIN_USER_MMXG";
	public static String IS_LOGINING = "IS_LOGINING";// 是否登录
	public static String IS_CLASS_CREATER = "IS_CLASS_CREATER";//是否班课创建者和助教
	public static String IS_CREATER = "IS_CREATER";//是否班课创建者
	public static String CLASS_CID = "CLASS_CID";
	public static String CLASS_KCID = "CLASS_KCID";
	public static String TOU_PING_CODE = "TOU_PING_CODE";
	public static String TOU_PING_ADDRESS = "TOU_PING_ADDRESS";
	public static String KEY_STORY = "12371key";
	// APP下载文件夹
	public static final String APPFILE_NAME = "appfile";
	public static final String APPFILE_DOWNLOAD = "download";

	public static boolean APP_DOWNLOAD_SERVICE = false;

	public static final String ObjectFirst = "Object_First";//传递对象
	public static final String ObjectSecend = "Object_Secend";//传递对象
	public static final String Key_1 = "_Key_1";//传递参数1
	public static final String Key_2 = "_Key_2";//传递参数2
	public static final String Key_3 = "_Key_3";//传递参数3
	public static final String Key_4 = "_Key_4";//传递参数4

	public static final int PERMISSION_REQUEST_ONE = 0X01;
	public static final int PERMISSION_REQUEST_TWO = 0X02;
	public static final int PERMISSION_REQUEST_THREE = 0X03;

	public static final int REQUEST_CODE_SCAN = 0X101;
	public static final int REQUEST_CODE_IMAGE = 0X102;
	public static final int REQUEST_CODE_OTHRE_ONE = 0X103;
	public static final int REQUEST_CODE_OTHRE_TWO = 0X104;
	public static final int REQUEST_CODE_OTHRE_THREE = 0X105;

	public static String getFullUrl(String url) {
		 Logger.e("debug", "url:" + url);
		if (StringUtils.empty(url)) {
			return "";
		}
		if (url.contains("http:") || url.contains("https:")) {
			return url;
		} else {
			String head = AllenManager.getInstance().getStoragePreference().getString(Constants.SERVER_URL, "");
			if (url.substring(0, 1).equals("/")) {
				 Logger.e("debug", "image_path:" + head);
				return head + url.substring(1);
			} else {
				return head + url;
			}
		}
	}

	public static String changenull(String parm) {
		if (StringUtils.notEmpty(parm)) {
			if (parm.equals("null")) {
				return "";
			} else {
				return parm;
			}
		} else {
			return "";
		}
	}

	public static class QQ {
		public static String appid = "101502244";
	}

	public static class Login {
		public static boolean isLogin = false;
	}
}
