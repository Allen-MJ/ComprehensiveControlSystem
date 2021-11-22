package allen.frame;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import allen.frame.tools.EncryptUtils;
import allen.frame.tools.StringUtils;

/**
 * app管理 
 * 1.activity 管理
 * 2.map 开启应用时数据存储
 * 3.app版本信息 升级检查等
 * 4.SharedPreferences 持久存储
 * @author maojing
 *
 */
public class AllenManager {
	
	private static AllenManager manager;
	private ArrayList<Activity> acts;
	private Map<String, Object> user;//只在应用打开时存储String,int,long,float,boolean数据
	private String packagename;
	private String appName;
	public String verCode  = "0.0.0";
	private SharedPreferences shared;
	private String uuid;
	private Context context;
	
	private AllenManager(Context mctt) {
		context = mctt.getApplicationContext();
		acts = new ArrayList<Activity>();
		user = new HashMap<String, Object>();
		packagename = context.getApplicationInfo().packageName;
		appName = context.getString(context.getResources()
				.getIdentifier("app_name", "string", context.getPackageName()));
		try {
			verCode  = context.getPackageManager().getPackageInfo(packagename, 0).versionName;
		} catch (NameNotFoundException e) {
			verCode  = "0.0.0";
		}
		shared = context.getApplicationContext().getSharedPreferences(EncryptUtils
				.MD5Encoder(appName), Context.MODE_PRIVATE);
	}
	public static AllenManager init(Application context){
		if(manager==null){
			manager = new AllenManager(context);
		}
		return manager;
	}
	public static AllenManager getInstance(){
		if(manager==null){
			throw new NullPointerException("AppManager is not init!");
		}
		return manager;
	}
	public AllenManager put(String key,Object object){
		user.put(key, object);
		return this;
	}
	private Object get(String key){
		return user.get(key);
	}
	public String getString(String key,String def){
		if(user.containsKey(key)){
			return (String) get(key);
		}else{
			return def;
		}
	}
	public int getInt(String key,int def){
		if(user.containsKey(key)){
			return (Integer) get(key);
		}else{
			return def;
		}
	}
	public long getLong(String key,long def){
		if(user.containsKey(key)){
			return (Long) get(key);
		}else{
			return def;
		}
	}
	public float getFloat(String key,float def){
		if(user.containsKey(key)){
			return (Float) get(key);
		}else{
			return def;
		}
	}
	public boolean getBoolean(String key,boolean def){
		if(user.containsKey(key)){
			return (Boolean) get(key);
		}else{
			return def;
		}
	}
	/**
	 * 清除用户信息数据
	 */
	public void clearUserData(){
		if(!user.isEmpty()){
			user.clear();
		}
	}
	/**
	 * 加入管理堆栈
	 * @param activity
	 */
	public void addActivity(Activity activity){
		acts.add(activity);
	}
	/**
	 * 结束指定Activity
	 * @param activity
	 */
	public void closeActivity(Activity activity){
		if(activity!=null){
			acts.remove(activity);
			activity.finish();
		}
	}
	/**
	 * 关闭除Activity外的所有Activity
	 * @param cls
	 */
	public void closeAllActivityNot(Class<?> cls){
		for(int i=0;i<acts.size();i++){
			if(!acts.get(i).getClass().equals(cls)){
				closeActivity(acts.get(i));
			}
		}
	}
	/**
	 * 返回到指定Activity
	 * @param cls
	 */
	public void back2Activity(Class<?> cls){
		for(int i=acts.size()-1;i>=0;i--){
			if(!acts.get(i).getClass().equals(cls)){
				closeActivity(acts.get(i));
			}else{
				return;
			}
		}
	}
	/**
	 * 退出应用
	 */
	public void exitApp(){
		for(int i=0;i<acts.size();i++){
			acts.get(i).finish();
		}
	}
	/**
	 * 是否大于当前版本
	 * @param newVercode
	 * @return
	 */
	public boolean isNewVersion(String newVercode){
		if(StringUtils.empty(newVercode)){
			return false;
		}
		boolean isupdate = false;
		int first = 0;
		int second = 0;
		int third = 0;
		String[] oldV = verCode.split("\\.");
		String[] newV = newVercode.split("\\.");
		for(int i=0;i<oldV.length;i++){
			int nC = Integer.parseInt(newV[i]);
			int oC = Integer.parseInt(oldV[i]);
			switch (i) {
			case 0:
				first = nC-oC;
				break;
			case 1:
				second = nC-oC;
				break;
			case 2:
				third = nC-oC;
				break;

			default:
				break;
			}
			if(first>0){
				isupdate = true;
			}else if(first==0){
				if(second>0){
					isupdate = true;
				}else if(second ==0){
					if(third>0){
						isupdate = true;
					}
				}
			}
		}
		return isupdate;
	}
	/**
	 * 获取app名称
	 * @return
	 */
	public String getAppname(){
		return appName;
	}
	/**
	 * 获取版本号
	 * @return x.x.x
	 */
	public String getVersionCode(){
		return verCode;
	}
	/**
	 * 获取包名
	 * @return
	 */
	public String getPackagename(){
		return packagename;
	}
	/**
	 * SharedPreferences
	 * @return
	 */
	public SharedPreferences getStoragePreference(){
		return shared;
	}
	/**
	 * 获取设备uuid
	 * @return
	 */
	public String getDeviceId(){
		if(StringUtils.empty(uuid)){
			String serial = null;
			String m_szDevIDShort = "35" +
					Build.BOARD.length()%10+ Build.BRAND.length()%10 +
					Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
					Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
					Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
					Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
					Build.TAGS.length()%10 + Build.TYPE.length()%10 +
					Build.USER.length()%10 ; //13 位
			try {
				serial = android.os.Build.class.getField("SERIAL").get(null).toString();
				//API>=9 使用serial号
				return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
			} catch (Exception exception) {
				//serial需要一个初始化
				serial = "serial"; // 随便一个初始化
			}
			//使用硬件信息拼凑出来的15位号码
			uuid = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();

		}
		return uuid;
	}

	public String getDeviceMac(){
		String macAddress = null;
		StringBuffer buf = new StringBuffer();
		NetworkInterface networkInterface = null;
		try {
			networkInterface = NetworkInterface.getByName("eth1");
			if (networkInterface == null) {
				networkInterface = NetworkInterface.getByName("wlan0");
			}
			if (networkInterface == null) {
				return "02:00:00:00:00:02";
			}
			byte[] addr = networkInterface.getHardwareAddress();
			for (byte b : addr) {
				buf.append(String.format("%02X:", b));
			}
			if (buf.length() > 0) {
				buf.deleteCharAt(buf.length() - 1);
			}
			macAddress = buf.toString();
		} catch (SocketException e) {
			e.printStackTrace();
			return "02:00:00:00:00:02";
		}
		return macAddress;
	}


}
