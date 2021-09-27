package allen.frame.tools;

import android.util.Log;

import allen.frame.AllenManager;

/**
 * 调试日志
 * @author Administrator
 *
 */
public class Logger {
	private static Logger logger;
	public static boolean isDebug = true;//全局显示判断
	public static boolean isHttp = false;//数据获取显示判断
	private Logger(){
	}
	
	public static Logger init(){
		if(logger==null){
			logger = new Logger();
		}
		return logger;
	}
	public Logger setDebug(boolean debug){
		isDebug = debug;
		return this;
	}
	
	public Logger setHttp(boolean debug){
		isHttp = debug;
		return this;
	}
	/**
	 * 根据全局变量isDebug判断是否显示调试信息
	 * @param tag
	 * @param message
	 */
	public static void debug(String tag,String message){
		if(isDebug){
			if(StringUtils.empty(tag)){
				Log.e("debug", StringUtils.null2Empty(message));
			}else{
				Log.e(tag, StringUtils.null2Empty(message));
			}
		}
	}
	/**
	 * ju
	 * @param tag
	 * @param message
	 */
	public static void e(String tag,String message){
		if(!isDebug){
			return;
		}
		if(StringUtils.empty(tag)){
			Log.e("debug", StringUtils.null2Empty(message));
		}else{
			Log.e(tag, StringUtils.null2Empty(message));
		}
	}
	public static void http(String tag,String message){
		if(!isHttp){
			return;
		}
		Log.e("http", (StringUtils.empty(tag)?"data:":tag)+StringUtils.null2Empty(message));
	}
}
