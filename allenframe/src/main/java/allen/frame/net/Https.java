package allen.frame.net;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import allen.frame.entry.File;
import allen.frame.tools.Constants;
import allen.frame.tools.StringUtils;

public class Https {
    private static HttpEngine mEngine = new OkHttpEngine();
    private Context context;
    private String mUrl;
    private File mFile;
    private String mPath;
    private Map<String,Object> mParams;
    private Map<String,Object> mHeaders;
    private String jsons;
    private int method = Type_Post;
    public static int Type_Post = 0;
    public static int Type_Get = 1;
    public static int Type_Put = 3;
    public static int Type_Delete = 4;
    public static int Type_Upload = 2;
    public static int Type_Download = 5;

    public Https(Context ctt) {
        this.context = ctt;
        mParams = new HashMap<>();
    }

    /**
     * 初始化引擎
     * @param engine
     */
    public static void init(HttpEngine engine){
        mEngine = engine;
    }

    public static Https with(Context ctt){
        return new Https(ctt);
    }

    public Https url(String url){
        if(StringUtils.empty(url)){
            throw new NullPointerException("url is empty!");
        }
        if(url.startsWith("http")){
            mUrl = url;
        }else{
            mUrl = Constants.url + url;
        }
        return this;
    }

    public Https post(){
        method = Type_Post;
        return this;
    }

    public Https get(){
        method = Type_Get;
        return this;
    }
    public Https put(){
        method = Type_Put;
        return this;
    }
    public Https delete(){
        method = Type_Delete;
        return this;
    }
    public Https upload(){
        method = Type_Upload;
        return this;
    }

    public Https file(File file){
        mFile = file;
        return this;
    }
    public Https path(String path){
        mPath = path;
        return this;
    }

    public Https addParam(String key, Object value){
        if(mParams==null){
            mParams = new HashMap<>();
        }
        mParams.put(key,value);
        return this;
    }

    public Https addHeader(String key, Object value){
        if(mHeaders==null){
            mHeaders = new HashMap<>();
        }
        mHeaders.put(key,value);
        return this;
    }

    public Https addParams(Map<String,Object> params){
        if(mParams==null){
            mParams = new HashMap<>();
        }
        mParams.putAll(params);
        return this;
    }

    public Https addJsons(String jsons){
        this.jsons = jsons;
        return this;
    }

    private <T> void post(Callback<T> callback){
        if(StringUtils.notEmpty(jsons)){
            mEngine.post(context,mUrl,jsons,callback);
        }else{
            mEngine.post(context,mUrl,mParams,mHeaders,callback);
        }
    }

    private <T> void get(Callback<T> callback){
        mEngine.get(context,mUrl,mParams,callback);
    }

    private <T> void put(Callback<T> callback){
        if(StringUtils.notEmpty(jsons)){
            mEngine.put(context,mUrl,jsons,callback);
        }else{
            mEngine.put(context,mUrl,mParams,callback);
        }
    }

    private <T> void delete(Callback<T> callback){
        if(StringUtils.notEmpty(jsons)){
            mEngine.delete(context,mUrl,jsons,callback);
        }else{
            mEngine.delete(context,mUrl,mParams,callback);
        }
    }

    private <T> void upload(Callback<T> callback){
        if(mFile!=null){
            mEngine.upload(context,mUrl,mFile,mParams,callback);
        }else{
            mEngine.upload(context,mUrl,mPath,mParams,callback);
        }
    }

    public void download(Callback<java.io.File> callback){
        method = Type_Download;
        mEngine.download(context,mUrl,callback);
    }

    public <T> void enqueue(Callback<T> callback){
        if(method==Type_Post){
            post(callback);
        }else if(method==Type_Get){
            get(callback);
        }else if(method==Type_Upload){
            if(StringUtils.notEmpty(mPath)){

            }else if(mFile==null){
                throw new NullPointerException("文件不能为空!");
            }
            upload(callback);
        }else if(method==Type_Delete){
            delete(callback);
        }else if(method==Type_Put){
            put(callback);
        }
    }
}
