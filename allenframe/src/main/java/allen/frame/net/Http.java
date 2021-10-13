package allen.frame.net;

import android.app.Activity;

import com.google.gson.Gson;

import allen.frame.AllenManager;
import allen.frame.tools.Constants;

public class Http<T> {
    private static final long TIME_OUT = 120;//单位秒S
    private String token;
    private Gson gson;
    private Body body;
    private Activity act;
    public String method;//方法名
    public Object[] parameters;//参数
    public Callback<T> callback;//回调
    private Http(){
        token = AllenManager.getInstance().getStoragePreference().getString(Constants.UserToken, "");
        gson = new Gson();
        body = new Body();
    }
    public Http with(Activity act){
        this.act = act;

        return this;
    }
    public static class Builder<T> {
        public String method;
        public Object[] parameters;
        public Callback<T> callback;
        public Builder(){
            method = method;
            parameters = parameters;
            callback = callback;
        }
        Builder(Http http){
            this.method = http.method;
            this.parameters = http.parameters;
            this.callback = http.callback;
        }
        public Builder setMothed(String mMethod){
            method = mMethod;
            return this;
        }
        public Builder setParameters(Object[] mParameters){
            parameters = mParameters;
            return this;
        }
    }
}
