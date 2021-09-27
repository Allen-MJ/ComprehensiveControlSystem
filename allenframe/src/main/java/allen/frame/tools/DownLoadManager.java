package allen.frame.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class DownLoadManager {
	private static DownLoadManager manager;
    private OkHttpClient mClient;//OKHttpClient;
    private HashMap<String, DownLoadNewHelper> helpers;
    private long TIME_OUT = 120;
    
    private DownLoadManager(){
        OkHttpClient.Builder okhttpClient = new OkHttpClient.Builder();
		/*//信任所有服务器地址
        okhttpClient.hostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String s, SSLSession sslSession) {
				return true;
			}
		});
		//创建管理器
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] x509Certificates,
					String s) throws java.security.cert.CertificateException {
			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] x509Certificates,
					String s) throws java.security.cert.CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[]{};
			}
		}};
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			//为OkHttpClient设置sslSocketFactory
            okhttpClient.sslSocketFactory(sslContext.getSocketFactory());

		} catch (Exception e) {
			e.printStackTrace();
		}*/
        mClient = okhttpClient.connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS).build();
        helpers = new HashMap<>();
    }
    
    public static DownLoadManager getInstance() {
    	if(manager==null){
    		manager = new DownLoadManager();
    	}
    	return manager;
    }
    
    public void download(final String dir, final String name, final String url, DowloadListener listener){
    	final DownLoadNewHelper helper = new DownLoadNewHelper(listener);
    	helpers.put(dir+name+url, helper);
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				helper.downLoadFile(dir, name, url,mClient);
			}
		}).start();
    }
    
    public void pause(String dir, String name, String url){
    	if(helpers.get(dir+name+url)!=null){
    		helpers.get(dir+name+url).setPause(true);
    	}
    }
    
    // 将网络下载地址进行编码
 	private static String doEncoderUrlStr(String str){
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
