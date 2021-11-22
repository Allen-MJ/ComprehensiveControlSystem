package allen.frame.net;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

import allen.frame.entry.File;

public interface HttpEngine {
    public <T> void post(Context act, String url, Map<String,Object> params, Callback<T> callBack);
    public <T> void post(Context act, String url, Map<String,Object> params,Map<String,Object> headers,Callback<T> callBack);
    public <T> void delete(Context act, String url, Map<String,Object> params,Callback<T> callBack);
    public <T> void delete(Context act, String url, String params,Callback<T> callBack);
    public <T> void put(Context act, String url, Map<String,Object> params,Callback<T> callBack);
    public <T> void put(Context act, String url, String params,Callback<T> callBack);
    public <T> void post(Context act, String url, String params,Callback<T> callBack);
    public <T> void get(Context act, String url, Map<String,Object> params,Callback<T> callBack);

    /**
     * 上传文件
     * @param act
     * @param url
     * @param file
     * @param params
     * @param callBack
     * @param <T>
     */
    public <T> void upload(Context act, String url, File file, Map<String,Object> params, Callback<T> callBack);
    public <T> void download(Context act, String url, Callback<T> callBack);
}
