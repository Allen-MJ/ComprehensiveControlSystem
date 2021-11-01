package allen.frame.net;

import android.app.Activity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class OkHttpEngine implements HttpEngine {

    private static final long TIME_OUT = 120;//单位秒S
    private final Gson gson;
    private final Body mbody;
    private String token;

    public OkHttpEngine() {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        gson = new Gson();
        mbody = new Body();
    }

    @Override
    public <T> void post(final Activity act, String url, Map<String, Object> params, final Callback<T> callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, mbody.post(params));
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

    @Override
    public <T> void get(final Activity act, String url, Map<String, Object> params, final Callback<T> callback) {
        Logger.http("token", ">>" + token);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        String txt = mbody.get(params);
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
                    if(result.codeEquals("200")){

                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                callback.success(result);
                            }
                        });
                    }else if(result.codeEquals("401")){
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