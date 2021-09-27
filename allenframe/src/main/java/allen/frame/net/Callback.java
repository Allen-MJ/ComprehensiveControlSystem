package allen.frame.net;

/**
 * 数据回调接口
 * @param <T>
 */
public abstract class Callback<T> {

    private String key;
    Callback(){
        key = String.valueOf(System.currentTimeMillis());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    abstract void success(T data);
    abstract void fail(Class<?> data);
}