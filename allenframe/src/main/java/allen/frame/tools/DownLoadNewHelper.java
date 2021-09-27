package allen.frame.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import allen.frame.entry.DownLoadInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadNewHelper {
	
	private RandomAccessFile newfile;
	private FileUtils fileUtils;
	private DowloadListener listener;
	private boolean isPause = false;
	private long total = 0;
	private long curlen = 0;
	private String path;
	public DownLoadNewHelper(DowloadListener listener){
		fileUtils = FileUtils.getInstance();
		this.listener = listener;
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
	
	public String getFilePath(){
		return path;
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
	public void downLoadFile(String dir, String name, String url, OkHttpClient mClient){
//		String filename = getNewFileName(name, url);
		path = getFile(dir, name, url).getAbsolutePath();
		total = getContentLength(url, mClient);
		if(total<0){
			listener.fail("下载失败!");
			Logger.e("debug", "连接异常!");
			return;
		}
		try {
			newfile = new RandomAccessFile(path, "rw");
			curlen = newfile.length();
			listener.start(path,curlen,total);
			if(curlen>0){
	    		if(curlen==total){
	    			listener.historySucess(path, "文件已经下载!");
	    			return;
	    		}else{
	    			okhttpDownload(url, mClient);
					return;
	    		}
	    	}else{
	    		okhttpDownload(url, mClient);
				return;
	    	}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			listener.fail("下载失败!");
			getFile(dir, name, url).delete();
		}
	}
    
    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl, OkHttpClient mClient) {
    	Logger.e("debug", "file->url:"+doEncoderUrlStr(downloadUrl));
    	Request request;
    	try {
    		request = new Request.Builder()
    		.url(doEncoderUrlStr(downloadUrl))
    		.build();
		} catch (Exception e) {
			// TODO: handle exception
			return DownLoadInfo.TYPE_ERRO;
		}
        try {
            Response response = mClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? DownLoadInfo.TYPE_ERRO : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownLoadInfo.TYPE_ERRO;
    }
	
	public void okhttpDownload(String url, OkHttpClient mClient){
		Request request = new Request.Builder()
        .addHeader("User-Agent", "android")
        .header("Content-Type", "text/html; charset=utf-8;")
        .addHeader("RANGE", "bytes=" + curlen + "-")
        .url(doEncoderUrlStr(url))
        .build();
		Call call = mClient.newCall(request);
		call.enqueue(new Callback() {
		    @Override
		    public void onFailure(Call call, IOException e) {
//		        sendDownloadErrorMsg(e.toString());
		        listener.fail("下载失败!");
		        Logger.e(this.getClass().getSimpleName(), "IOException:" + e.toString());
		    }
		
		    @Override
		    public void onResponse(Call call, Response response) throws IOException {
		    	InputStream is = response.body().byteStream();
		        byte[] buf = new byte[2048];
		        int len = 0;
		        long downloading = 0;
		        long downSum = curlen;
		        newfile.seek(curlen);
		        while ((len = is.read(buf)) != -1) {
	                if (isPause()) {
	                	listener.pause(path, downSum, total, "下载暂停!");
	                    break;
	                }
	                newfile.write(buf, 0, len);
	                downloading += len;
	                downSum = downloading + curlen;
	                //传递更新信息
	                listener.progress(path, downSum, total, "正在下载中...");
	            }
		        newfile.close();
		        if(!isPause()){
		        	listener.sucess(path,total,"下载完成!");
		        }
		    }
		});
	}
	
	/*public int downLoadAppFile(String dir,String url){
		String filename = getNewFileName("", url);
		newfile = fileUtils.creatNewFile(dir, filename);
		try {
			downLoadApp(url, newfile);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}*/
	/*public int downLoadFile(String dir,String name,String url){
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
	}*/
	
	/*public int downLoadFile(AppDialog dialog,String dir,String name,String url,int time){
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
	}*/
	
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
	
	/*private boolean isExitFile(File dir,String name){
		File file = new File(dir, name);
		if(file!=null&&file.exists()){
			return true;
		}else{
			return false;
		}
	}*/
	private boolean isExitFile(String dir, String name){
		File file = new File(fileUtils.creatNewDir(dir), name);
		if(file!=null&&file.exists()){
			return true;
		}else{
			return false;
		}
	}
//	public void deleteFile(){
//		File file = getFile();
//		if(file.exists()){
//			file.delete();
//		}
//	}

	// 将视频地址进行 编码
	public static String doEncoderUrlStr(String str){
		if(StringUtils.empty(str)){
			return "";
		}
		if(!CheckUtils.isContainChinese(str)){
			return str;
		}
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
	public boolean isPause() {
		return isPause;
	}
	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}
	
}
