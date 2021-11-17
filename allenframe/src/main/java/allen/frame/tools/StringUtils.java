package allen.frame.tools;


import java.text.DecimalFormat;

/**
 * 
 * @author maojing
 * @Description: 字符串工具类
 *
 */
public class StringUtils {

	/**
	 * 检验字符串是否不为空(不为null和空串"")
	 * 
	 * @param checkString
	 *            被检验的字符串
	 * @return true: 不为空,false: 为空
	 */
	public static boolean notEmpty(String checkString) {
		return checkString != null && checkString.trim().length() > 0;
	}

	/**
	 * 检验字符串是否为空(null和""都算作为空)
	 * 
	 * @param checkString
	 *            被检验的字符串
	 * @return true: 为空,false: 不为空
	 */
	public static boolean empty(String checkString) {
		return !notEmpty(checkString)||"anyType{}".equals(checkString);
	}

	/**
	 * 将null字符串对象转成空串"",如果字符串不为null则不做任何改变
	 * 
	 * @param string
	 *            被转换的字符串对象
	 * @return 转换后的字符串
	 */
	public static String null2Empty(String string) {
		return string == null ? "" : string;
	}
	/**
	 * 尾部后几位以特殊形式显示
	 * @param old 需要处理的字符串
	 * @param arrear 尾数
	 * @param ch 显示符号
	 * @return
	 */
	public static String replaceFootStr(String old,int arrear,String ch){
		if(notEmpty(old)){
			int len = old.length();
			if(len>arrear&&arrear>0){
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<arrear;i++){
					sb.append(ch);
				}
				return old.substring(0, len-arrear)+sb.toString();
			}
			return old;
		}
		return "";
	}
	
	public static String replaceFirst(String parm, String ch){
		if(notEmpty(parm)){
			return parm.replaceFirst(ch, "");
		}
		return parm;
	}
	public static String replaceNull(String parm){
		if(empty(parm)){
			return "";
		}
		return parm;
	}
	public static String replaceJson(String str){
		if(empty(str)){
			return "";
		}
		return str.replace("\\r", "").replace("\\n", "").replace("\'", "\\'").replace("\\\"", "\\'");
		
	}
	public static String getObject(Object object){
		if(object==null){
			return null;
		}
		if (object instanceof String && !getJSONType(object.toString())) {
			return "\""+object.toString()+"\"";
		}else{
			return object.toString();
		}
	}
	public static boolean getJSONType(String str) {
		boolean result = false;
		if (StringUtils.notEmpty(str)) {
			str = str.trim();
			if (str.startsWith("{") && str.endsWith("}")) {
				result = true;
			} else if (str.startsWith("[") && str.endsWith("]")) {
				result = true;

			}
		}
		return result;
	}

	public static String getFileNameByPath(String path){
		if(notEmpty(path)){
			return path.substring(path.lastIndexOf("/"));
		}
		return "";
	}

	public static String formatFileSize(long file) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (file < 1024) {
			fileSizeString = df.format((double) file) + "B";
		} else if (file < 1048576) {
			fileSizeString = df.format((double) file / 1024) + "K";
		} else if (file < 1073741824) {
			fileSizeString = df.format((double) file / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) file / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 替换字符
	 * @param param
	 * @param start 1开始
	 * @param end
	 * @param s 字符
	 * @return
	 */
	public static String hideStr(String param,int start,int end,CharSequence s){
		if(empty(param)){
			return "";
		}
		int len = param.length();
		if(len<start||len<end){
			return param;
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			if(i<start-1||i>=end){
				sb.append(param.charAt(i));
			}else{
				sb.append(s);
			}
		}
		return sb.toString();
	}

}
