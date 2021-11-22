package allen.frame.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import allen.frame.AllenManager;
import allen.frame.entry.File;
import allen.frame.tools.Constants;
import allen.frame.tools.FileUtils;
import allen.frame.tools.Logger;
import allen.frame.tools.ProgressListener;
import allen.frame.tools.StringUtils;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
    public <T> void post(final Context act, String url, Map<String, Object> params, final Callback<T> callback) {
        post(act, url, params, null, callback);
    }

    @Override
    public <T> void post(final Context act, String url, Map<String, Object> params, Map<String,Object> headers, final Callback<T> callback) {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, mbody.post(params));
        Request.Builder builder = new Request.Builder();
        builder.url(url).addHeader("keep-alive", "false")
                .addHeader("Authorization", token);
        if(headers!=null){
            for(Map.Entry<String,Object> entry:headers.entrySet()){
                String key = entry.getKey();
                String value = StringUtils.getObject(entry.getValue());
                builder.addHeader(key,value);
            }
        }
        builder.post(body);
        /*Request request = new Request.Builder().url(url)
                .addHeader("keep-alive", "false")
                .addHeader("Authorization", token).post(body)
                .build();*/
        Logger.e("body",mbody.post(params));
        client.newCall(builder.build()).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            Logger.http("data", "onFailure");
                            callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                /*if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }*/
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.xequals("200")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                    callback.success(result);
                                }
                            }
                        });
                    }else if(result.xequals("401")){
                        Logger.http("data", "token is erro!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.token();
                                }
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.fail(result);
                                }
                            }
                        });
                    }
                } else if(code==401){
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            Logger.http("data", "->Not isSuccessful");
                            if(callback!=null){
                                callback.token();
                            }
                        }
                    });
                }else{
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            if(callback!=null){
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
                        }
                    });
                }
            }
        });
    }

    @Override
    public <T> void delete(final Context act, String url, Map<String, Object> params, final Callback<T> callback) {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, mbody.post(params));
        Request request = new Request.Builder().url(url)
                .addHeader("keep-alive", "false")
                .addHeader("Authorization", token).delete(body)
                .build();
        Logger.e("body",mbody.post(params));
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            Logger.http("data", "onFailure");
                            callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                /*if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }*/
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.xequals("200")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                    callback.success(result);
                                }
                            }
                        });
                    }else if(result.xequals("401")){
                        Logger.http("data", "token is erro!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.token();
                                }
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.fail(result);
                                }
                            }
                        });
                    }
                } else if(code==401){
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            Logger.http("data", "->Not isSuccessful");
                            if(callback!=null){
                                callback.token();
                            }
                        }
                    });
                }else{
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            if(callback!=null){
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
                        }
                    });
                }
            }
        });
    }

    @Override
    public <T> void put(final Context act, String url, Map<String, Object> params, final Callback<T> callback) {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, mbody.post(params));
        Request request = new Request.Builder().url(url)
                .addHeader("keep-alive", "false")
                .addHeader("Authorization", token).put(body)
                .build();
        Logger.e("body",mbody.post(params));
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            Logger.http("data", "onFailure");
                            callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                /*if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }*/
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.xequals("200")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                    callback.success(result);
                                }
                            }
                        });
                    }else if(result.xequals("401")){
                        Logger.http("data", "token is erro!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.token();
                                }
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.fail(result);
                                }
                            }
                        });
                    }
                } else if(code==401){
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            Logger.http("data", "->Not isSuccessful");
                            if(callback!=null){
                                callback.token();
                            }
                        }
                    });
                }else{
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            if(callback!=null){
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
                        }
                    });
                }
            }
        });
    }

    @Override
    public <T> void post(final Context act, String url, String params, final Callback<T> callback) {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, params);
        Request request = new Request.Builder().url(url)
                .addHeader("keep-alive", "false")
                .addHeader("Authorization", token).post(body)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            Logger.http("data", "onFailure");
                            callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                /*if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }*/
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.xequals("200")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                    callback.success(result);
                                }
                            }
                        });
                    }else if(result.xequals("401")){
                        Logger.http("data", "token is erro!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.token();
                                }
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.fail(result);
                                }
                            }
                        });
                    }
                } else if(code==401){
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            Logger.http("data", "->Not isSuccessful");
                            if(callback!=null){
                                callback.token();
                            }
                        }
                    });
                }else{
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            if(callback!=null){
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
                        }
                    });
                }
            }
        });
    }

    @Override
    public <T> void put(final Context act, String url, String params, final Callback<T> callback) {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, params);
        Request request = new Request.Builder().url(url)
                .addHeader("keep-alive", "false")
                .addHeader("Authorization", token).put(body)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            Logger.http("data", "onFailure");
                            callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                /*if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }*/
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.xequals("200")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                    callback.success(result);
                                }
                            }
                        });
                    }else if(result.xequals("401")){
                        Logger.http("data", "token is erro!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.token();
                                }
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.fail(result);
                                }
                            }
                        });
                    }
                } else if(code==401){
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            Logger.http("data", "->Not isSuccessful");
                            if(callback!=null){
                                callback.token();
                            }
                        }
                    });
                }else{
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            if(callback!=null){
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
                        }
                    });
                }
            }
        });
    }

    @Override
    public <T> void delete(final Context act, String url, String params, final Callback<T> callback) {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, params);
        Request request = new Request.Builder().url(url)
                .addHeader("keep-alive", "false")
                .addHeader("Authorization", token).delete(body)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            Logger.http("data", "onFailure");
                            callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                /*if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }*/
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.xequals("200")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                    callback.success(result);
                                }
                            }
                        });
                    }else if(result.xequals("401")){
                        Logger.http("data", "token is erro!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.token();
                                }
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.fail(result);
                                }
                            }
                        });
                    }
                } else if(code==401){
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            if(callback!=null){
                                Logger.http("data", "->Not isSuccessful");
                                callback.token();
                            }
                        }
                    });
                }else{
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            if(callback!=null){
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
                        }
                    });
                }
            }
        });
    }

    @Override
    public <T> void get(final Context act, String url, Map<String, Object> params, final Callback<T> callback) {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
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
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            Logger.http("data", "onFailure");
                            callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.http("data", "onResponse");
                /*if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }
                if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }*/
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.codeEquals("200")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                    callback.success(result);
                                }
                            }
                        });
                    }else if(result.codeEquals("401")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.token();
                                }
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.fail(result);
                                }
                            }
                        });
                    }
                } else if(code==401){
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            Logger.http("data", "->Not isSuccessful");
                            if(callback!=null){
                                callback.token();
                            }
                        }
                    });
                }else{
                    new RunMain(act).run2main(new Runnable() {
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
                            if(callback!=null){
                                callback.fail(new allen.frame.entry.Response(String.valueOf(code),msg,msg));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public <T> void upload(final Context act, String url, File file, Map<String, Object> params, final Callback<T> callback) {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        OkHttpClient httpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody
                .Builder()
                .setType(MultipartBody.FORM);

        builder.addFormDataPart("platform", "android");
        builder.addFormDataPart("type", file.getSuffix());
        if(params!=null){
            for (Map.Entry<String,Object> entry:params.entrySet()){
                builder.addFormDataPart(entry.getKey(),entry.getValue().toString());
            }
        }
        builder.addFormDataPart("file", file.getName(),
                RequestBody.create(MediaType.parse(guessMimeType(file.getPath())), file.getFile()));
        MyMultipartBody myMultipartBody = new MyMultipartBody(builder.build(), new ProgressListener() {
            @Override
            public void onProgress(final long total, final long current) {
                //回调接口打印总进度和当前进度
                Logger.e("upload", total + " : " + current);
                /*if(act!=null&&act.isFinishing()){
                    return;
                }*/
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            callback.onProgress(total,current);
                        }
                    }
                });
            }
        });
        Request request = new Request.Builder().addHeader("keep-alive", "false")
                .addHeader("Authorization", token)
                .url(url)
                .post(myMultipartBody)
                .build();
        httpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        Logger.http("data", "onFailure");
                        if(callback!=null){
                            callback.fail(new allen.frame.entry.Response("501","请求失败!","请求失败!"));
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.http("data", "onResponse");
                /*if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }
                if(act!=null&&act.isFinishing()){
                    Logger.http("data", "Activity is on isFinishing!");
                    return;
                }*/
                final int code = response.code();
                Logger.http("code", ">>" + code);
                final String data = response.body().string();
                Logger.http("data", ">>" + data);
                if (response.isSuccessful()) {
                    final allen.frame.entry.Response result = gson.fromJson(data,allen.frame.entry.Response.class);
                    if(result.codeEquals("200")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.success((T) gson.fromJson(gson.toJson(result.getObj()), callback.getGenericityType()));
                                    callback.success(result);
                                }
                            }
                        });
                    }else if(result.codeEquals("401")){
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                Logger.http("data", "token is erro!");
                                if(callback!=null){
                                    callback.token();
                                }
                            }
                        });
                    }else{
                        Logger.http("data", "code is not 200!");
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if(callback!=null){
                                    callback.fail(result);
                                }
                            }
                        });
                    }
                } else if(code==401){
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            Logger.http("data", "->Not isSuccessful");
                            if(callback!=null){
                                callback.token();
                            }
                        }
                    });
                }else{
                    new RunMain(act).run2main(new Runnable() {
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
                            if(callback!=null){
                                callback.fail(new allen.frame.entry.Response(String.valueOf(code),msg,msg));
                            }
                        }
                    });
                }
            }
        });
    }

    private long length,total;
    private final static String TAG = "download";
    @Override
    public void download(final Context act, String url, final Callback<java.io.File> callBack) {
        final java.io.File file = FileUtils.getInstance().creatNewFile(Constants.APPFILE_NAME, FileUtils.getInstance().url2LocalName(url));
        length = file.length();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        final Request request = new Request.Builder().url(url)
                .addHeader("RANGE", "bytes=" + length + "-")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
                new RunMain(act).run2main(new Runnable() {
                    @Override
                    public void run() {
                        if(callBack!=null){
                            Logger.http("data", "onFailure");
                            callBack.fail(new allen.frame.entry.Response("501","下载失败!","下载失败!"));
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                InputStream inputStream = responseBody.byteStream();//得到输入流
                RandomAccessFile randomAccessFile = new RandomAccessFile(file.getAbsoluteFile(), "rw");//得到任意保存文件处理类实例
                if (total == 0){
                    total = responseBody.contentLength();//得到文件的总字节大小
                    randomAccessFile.setLength(total);//预设创建一个总字节的占位文件
                }
                if (length != 0){
                    randomAccessFile.seek(length);
                }
                /*if(total>0&&length>0&&total==length){
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            if(callBack!=null){
                                callBack.success(file);
                            }
                        }
                    });
                }*/
                byte[] bytes = new byte[2048];
                int len = 0;
                try {
                    while ((len = inputStream.read(bytes)) != -1) {
                        randomAccessFile.write(bytes,0,len);
                        length = length + len;
                        new RunMain(act).run2main(new Runnable() {
                            @Override
                            public void run() {
                                if (callBack != null) {
                                    callBack.onProgress(total,length);
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    Log.e(TAG, "Get下载异常");

                } finally {
                    length = randomAccessFile.getFilePointer();//记录当前保存文件的位置
                    randomAccessFile.close();
                    inputStream.close();
                    Log.e(TAG, total+"流关闭 下载的位置="+length);
                    Logger.e("更新","++++");
                    new RunMain(act).run2main(new Runnable() {
                        @Override
                        public void run() {
                            if(callBack!=null){
                                if(length==total){
                                    callBack.success(file);
                                }else{
                                    callBack.fail(new allen.frame.entry.Response("501","下载失败!","下载失败!"));
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private String guessMimeType(String filePath) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimType = fileNameMap.getContentTypeFor(filePath);
        if (TextUtils.isEmpty(mimType)) {
            return "application/octet-stream";
        }
        return mimType;
    }
}
