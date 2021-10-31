package allen.frame.net;

import android.app.Activity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import allen.frame.AllenManager;
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import allen.frame.tools.StringUtils;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Http<T> {
    private static final long TIME_OUT = 120;//单位秒S
    private String token;
    private Gson gson;
    private Body body;
    private Activity act;
    private Object[] parameters;//参数
    private Callback<T> callback;//回调
    private String url;
    private Body mbody;
    private Http(Activity act){
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        gson = new Gson();
        body = new Body();
        this.act = act;
        mbody = new Body();
    }
    public static Http with(Activity act){
        return new Http(act);
    }

    public Http url(String url){
        if(StringUtils.empty(url)){
            throw new NullPointerException("url is empty!");
        }else if(url.startsWith("http")){
            this.url = url;
        }else{
            this.url = Constants.url +url;
        }
        return this;
    }

    public Http parameters(Object[] mParameters){
        parameters = mParameters;
        return this;
    }

    public Http enqueue(Callback<T> callback){
        this.callback = callback;
        return this;
    }

    public void post(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, mbody.postJson(parameters));
        Request request = new Request.Builder().url(url)
                .addHeader("keep-alive", "false")
                .addHeader("Authorization", token).post(body)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(act.isFinishing()){
                            Logger.http("data", "Activity is on isFinishing!");
                            return;
                        }
                        Logger.http("data", "onFailure");
                        callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.xequals("200")){

                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                callback.success(result);
                            }
                        });
                    }else if(result.xequals("401")){
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Logger.http("data", "token is erro!");
                                act.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.token();
                                    }
                                });
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.fail(result);
                            }
                        });
                    }
                } else if(code==401){
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Logger.http("data", "->Not isSuccessful");
                            callback.token();
                        }
                    });
                }else{
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String msg = "请求失败!";
                            Logger.http("data", data);
                            try {
                                JSONObject obj = new JSONObject(data);
                                msg = obj.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            callback.fail(new allen.frame.entry.Response(String.valueOf(code),msg,msg));
                        }
                    });
                }
            }
        });
    }

    public void get(){
        Logger.http("token", ">>" + token);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        String txt = mbody.get(parameters);
        Logger.http("url", "url>>" + url + (StringUtils.empty(txt) ? "" : "?" + txt));
        final Request request = new Request.Builder().url(url + (StringUtils.empty(txt) ? "" : "?" + txt))
                .addHeader("keep-alive", "false")
                .addHeader("Authorization", token)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(act.isFinishing()){
                            Logger.http("data", "Activity is on isFinishing!");
                            return;
                        }
                        Logger.http("data", "onFailure");
                        callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.http("data", "onResponse");
                if(act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }
                if(act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.equals("200")){

                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                callback.success(result);
                            }
                        });
                    }else if(result.equals("401")){
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Logger.http("data", "token is erro!");
                                act.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.token();
                                    }
                                });
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.fail(result);
                            }
                        });
                    }
                } else if(code==401){
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Logger.http("data", "->Not isSuccessful");
                            callback.token();
                        }
                    });
                }else{
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String msg = "请求失败!";
                            Logger.http("data", data);
                            try {
                                JSONObject obj = new JSONObject(data);
                                msg = obj.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            callback.fail(new allen.frame.entry.Response(String.valueOf(code),msg,msg));
                        }
                    });
                }
            }
        });
    }
}
