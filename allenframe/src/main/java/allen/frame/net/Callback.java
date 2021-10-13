package allen.frame.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import allen.frame.entry.Response;

/**
 * 数据回调接口
 * @param <T>
 */
public abstract class Callback<T> {

    protected Type genType;
    private String key;
    public Callback(){
        key = String.valueOf(System.currentTimeMillis());
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            this.genType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            this.genType = Object.class;
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    protected abstract void success(T data);

    protected void token() {}

    protected abstract void fail(Response response);
    public Type getGenericityType() {
        return genType;
    }
}