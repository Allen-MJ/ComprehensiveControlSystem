package allen.frame.net;

import android.app.Activity;

import java.util.Map;

import allen.frame.entry.File;

public interface HttpEngine {
    public <T> void post(Activity act, String url, Map<String,Object> params,Callback<T> callBack);
    public <T> void delete(Activity act, String url, Map<String,Object> params,Callback<T> callBack);
    public <T> void delete(Activity act, String url, String params,Callback<T> callBack);
    public <T> void put(Activity act, String url, Map<String,Object> params,Callback<T> callBack);
    public <T> void put(Activity act, String url, String params,Callback<T> callBack);
    public <T> void post(Activity act, String url, String params,Callback<T> callBack);
    public <T> void get(Activity act, String url, Map<String,Object> params,Callback<T> callBack);

    /**
     * 上传文件
     * @param act
     * @param url
     * @param file
     * @param params
     * @param callBack
     * @param <T>
     */
    public <T> void upload(Activity act, String url, File file, Map<String,Object> params, Callback<T> callBack);
}
