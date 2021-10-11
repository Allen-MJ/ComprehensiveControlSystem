package allen.frame.net;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import allen.frame.AllenManager;
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataHttp {
    private static final long TIME_OUT = 120;//单位秒S
    private String token;
    private Gson gson;
    private DataBody mbody;

    public DataHttp() {
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        gson = new Gson();
        mbody = new DataBody();
    }

    public <T> void add(String mothed, Object[] arrays,Callback<T> callback){
        all(mothed,arrays,callback);
    }
    public <T> void delete(String mothed, Object[] arrays,Callback<T> callback){
        all(mothed,arrays,callback);
    }
    public <T> void query(String mothed, Object[] arrays,Callback<T> callback){
        all(mothed,arrays,callback);
    }
    public <T> void update(String mothed, Object[] arrays,Callback<T> callback){
        all(mothed,arrays,callback);
    }
    private <T> void all(String mothed, Object[] arrays,Callback<T> callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();// 创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");// 数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, mbody.jsonBody(arrays));
        Request request = new Request.Builder().url(Constants.url + mothed)
                .addHeader("keep-alive", "false")
                .addHeader("Authorization", "Bearer " + token).post(body)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.http("data", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.http("data", "onResponse");
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    Logger.http("data", ">>" + data);
                } else {
                    Logger.http("data", "Not isSuccessful");
                }
            }
        });
    }
}
