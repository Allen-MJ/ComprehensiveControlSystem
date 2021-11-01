package allen.frame.net;

import android.app.Activity;

import java.util.Map;

public interface HttpEngine {
    public <T> void post(Activity activity, String url, Map<String,Object> params,Callback<T> callBack);
    public <T> void get(Activity activity, String url, Map<String,Object> params,Callback<T> callBack);
}
