package allen.frame.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DownLoadHelper {
	
	private static DownLoadHelper helper;
	private File newfile;
	private FileUtils fileUtils;
	private DownLoadHelper(){
		fileUtils = FileUtils.getInstance();
	}
	public static DownLoadHelper getInstall(){
		if(helper==null){
			helper = new DownLoadHelper();
		}
		return helper;
	}
	/**
	 * 获取存储文件
	 * @param dir 文件夹名
	 * @param name 服务器文件名
	 * @param url 下载地址
	 * @return 存储文件
	 */
	public File getFile(String dir, String name, String url){
		return fileUtils.creatNewFile(dir, getNewFileName(name,url));
	}
	
	public File getFile(){
		return newfile;
	}
	/**
	 * 通过文件名以及下载地址命名文件在系统的存储名称
	 * @param name 服务器文件名
	 * @param url 下载地址
	 * @return 文件在系统的存储名称
	 */
	private String getNewFileName(String name, String url){
		String fileName = "";
		String type = "";
		if(StringUtils.notEmpty(url)&&url.contains(".")){
			type = url.substring(url.lastIndexOf(".")+1);
			fileName = EncryptUtils.MD5Encoder(url)+"."+type;
		}else if(StringUtils.notEmpty(name)&&name.contains(".")){
			type = name.substring(name.lastIndexOf(".")+1);
			fileName = EncryptUtils.MD5Encoder(url)+"."+type;
		}else {
			fileName = EncryptUtils.MD5Encoder(url);
		}
		return fileName;
	}
	
	/**
	 * 模糊下载文件
	 * @param dir
	 * @param name
	 * @param url
	 * @return -1下载失败， 0,下载成功 1文件存在
	 */
	public int downLoadFile(String dir, String name, String url){
		File perant = fileUtils.creatNewDir(dir);
		String filename = getNewFileName(name, url);
		boolean isExitFile = isExitFile(perant, filename);
		newfile = fileUtils.creatNewFile(dir, filename);
		if(isExitFile){
			return 1;
		}else{
			try {
				downLoad(url, newfile);
				return 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	private void downLoad(String url, File file) throws Exception {
	        // 构造URL
        URL mUrl = new URL(doEncoderUrlStr(url));
        // 打开连接     
        HttpURLConnection con = (HttpURLConnection) mUrl.openConnection();
        //获得文件的长度  
//        int contentLength = con.getContentLength();
//	        System.out.println("长度 :"+contentLength);  
        // 输入流     
        InputStream is = con.getInputStream();
        // 1K的数据缓冲     
        byte[] bs = new byte[1024];     
        // 读取到的数据长度     
        int len;     
        // 输出的文件流     
        OutputStream os = new FileOutputStream(file);
        // 开始读取     
        while ((len = is.read(bs)) != -1) {
        	os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接     
        os.close();    
        is.close();  
	}
	/**
	 * 带进度下载app文件
	 * @param dialog
	 * @param dir
	 * @param url
	 * @return 0成功 -1失败
	 */
	public int downLoadAppFile(AppDialog dialog, String dir, String url){
		String filename = getNewFileName("", url);
		newfile = fileUtils.creatNewFile(dir, filename);
		try {
			downLoadApp(dialog,url, newfile);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * 带进度下载文件
	 * @param dialog
	 * @param dir
	 * @param url
	 * @return 0,1成功 -1失败
	 */
	public int downLoadFile(AppDialog dialog, String dir, String name, String url){
		String filename = getNewFileName(name, url);
		boolean isExitFile = isExitFile(dir, filename);
		newfile = fileUtils.creatNewFile(dir, filename);
		if(isExitFile){
			return 1;
		}else{
			try {
				downLoadApp(dialog,url, newfile);
				return 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(newfile!=null&&newfile.exists()){
				newfile.delete();
			}
			return -1;
		}
	}
	public int downLoadFile(AppDialog dialog, String dir, String name, String url, int time){
		String filename = getNewFileName(name, url);
		boolean isExitFile = isExitFile(dir, filename);
		newfile = fileUtils.creatNewFile(dir, filename);
		if(isExitFile){
			return 1;
		}else{
			try {
				downLoadApp(dialog,url, newfile, time);
				return 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(newfile!=null&&newfile.exists()){
				newfile.delete();
			}
			return -1;
		}
	}
	
	private void downLoadApp(AppDialog dialog, String url, File file) throws Exception {
	    URL mUrl = new URL(doEncoderUrlStr(url));
	    HttpURLConnection con = (HttpURLConnection) mUrl.openConnection();
	    int length = con.getContentLength();
	    int start = 0;
	    InputStream is = con.getInputStream();
	    byte[] bs = new byte[1024];     
	    int len;     
	    OutputStream os = new FileOutputStream(file);
	    while ((len = is.read(bs)) != -1) {
	    	os.write(bs, 0, len);
	    	start+=len;
	    	dialog.setProgress(start, length);
	    }
	    os.close();    
	    is.close();  
	}
	
	private void downLoadApp(AppDialog dialog, String url, File file, int time) throws Exception {
//		URL mUrl = new URL(url);     
		URL mUrl = new URL(doEncoderUrlStr(url));
		HttpURLConnection con = (HttpURLConnection) mUrl.openConnection();
		if(time>0){
			con.setConnectTimeout(time);
		}
		int length = con.getContentLength();
		int start = 0;
		InputStream is = con.getInputStream();
		byte[] bs = new byte[1024];     
		int len;     
		OutputStream os = new FileOutputStream(file);
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
			start+=len;
			dialog.setProgress(start, length);
		}
		os.close();    
		is.close();  
	}
	
	private boolean isExitFile(File dir, String name){
		File file = new File(dir, name);
		if(file!=null&&file.exists()){
			return true;
		}else{
			return false;
		}
	}
	private boolean isExitFile(String dir, String name){
		File file = new File(fileUtils.creatNewDir(dir), name);
		if(file!=null&&file.exists()){
			return true;
		}else{
			return false;
		}
	}
	public void deleteFile(){
		File file = getFile();
		if(file.exists()){
			file.delete();
		}
	}
//	private File creatFile(File dir,String name){
//		File file = new File(dir, name);
//		if(file.exists()&&file!=null){
//			return file;
//		}else{
//			return file;
//		}
//	}
	
	
	// 将视频地址进行 编码
	public static String doEncoderUrlStr(String str){
		try {
			String url = URLEncoder.encode(str,"utf-8").replaceAll("\\+", "%20")
					.replaceAll("%3A", ":").replaceAll("%2F", "/");
			return url;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
}
