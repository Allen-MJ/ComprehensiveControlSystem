package allen.frame.data;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import allen.frame.AllenManager;
import allen.frame.tools.Constants;
import allen.frame.tools.EncryptUtils;
import allen.frame.tools.Logger;
import allen.frame.tools.StringUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

	private static final long TIME_OUT = 120;//单位秒S
	private String url;
	private String mac="";
	public HttpUtils() {
		this.url = AllenManager.getInstance()
				.getStoragePreference()
				.getString(Constants.SERVER_URL, "");
		mac = StringUtils.null2Empty(AllenManager.getInstance().getDeviceMac());
		Logger.e("debug","mac"+mac);
	}
	
	public void changeUrl(String url){
		if(this.url.equals(url)){
			return;
		}
		this.url = url;
		mac = StringUtils.null2Empty(AllenManager.getInstance().getDeviceMac());
	}
	
	public HttpUtils(String url) {
		this.url = url;
		mac = StringUtils.null2Empty(AllenManager.getInstance().getDeviceMac());
	}

	/**
	 * get请求
	 * 
	 * @param mothed
	 * @param array
	 * @return
	 */
	public String okhttpget(String mothed, String array) {
		Logger.e("debug","mac"+mac);
		String data = "";
		try {
			OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build();
			Request request = new Request.Builder()
					.url(url + mothed + (StringUtils.empty(array) ? "" : "?" + array)).addHeader("mac",mac).build();
			Response response = null;
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				data = response.body().string();
			}
		} catch (Exception e) {
			Logger.http("erro:", e.getMessage());
			e.printStackTrace();
		}
		return decry(data);
	}
	public String okhttpgetnomac(String mothed, String array) {
		String data = "";
		try {
			OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build();
			Request request = new Request.Builder()
					.url(url + mothed + (StringUtils.empty(array) ? "" : "?" + array)).build();
			Response response = null;
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				data = response.body().string();
			}
		} catch (Exception e) {
			Logger.http("erro:", e.getMessage());
			e.printStackTrace();
		}
		return decry(data);
	}

	public String okhttpget(String url) {
		Logger.e("debug","mac"+mac);
		String data = "";
		try {
			OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build();
			Request request = new Request.Builder().url(url).addHeader("mac",mac).build();
			Response response = null;
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				data = response.body().string();
			}
		} catch (Exception e) {
			Logger.http("erro:", e.getMessage());
			e.printStackTrace();
		}
		Logger.http("", data);
		return data;
	}

	public String okhttpgetnomac(String url) {
		String data = "";
		try {
			OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build();
			Request request = new Request.Builder().url(url).build();
			Response response = null;
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				data = response.body().string();
			}
		} catch (Exception e) {
			Logger.http("erro:", e.getMessage());
			e.printStackTrace();
		}
		Logger.http("", data);
		return data;
	}

	public String okhttppost(String mothed, String json) {
		Logger.e("debug","mac"+mac);
		String data = "";
		try {
			OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build();// 创建OkHttpClient对象。
			MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
			RequestBody body = RequestBody.create(JSON, json);
			Request request = new Request.Builder().url(url + mothed).addHeader("mac",mac).post(body).build();
			Response response = null;
			response = client.newCall(request).execute();
			Logger.http("Response>>", response.toString());
			if (response.isSuccessful()) {
				data = response.body().string();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decry(data);
	}



	private String decry(String data) {
		if (StringUtils.notEmpty(data)) {
			return EncryptUtils.decryptString(data, Constants.KEY_STORY);
		}
		return "";
	}
}
