package allen.frame.net;

import java.util.HashMap;

public class InterfaceManager {
    private static InterfaceManager manager;
    private HashMap<String,Callback> map = new HashMap<>();
    public static InterfaceManager init(){
        if(manager==null){
            synchronized (InterfaceManager.class){
                if(manager==null){
                    manager = new InterfaceManager();
                }
            }
        }
        return manager;
    }

    /*public String invoke(Runnable runnable,Callback callback){
        if(callback!=null){
            if(!map.containsKey(callback.getKey())){
                map.put(callback.getKey(),callback);
            }
            return callback.getKey();
        }
        return "";
    }*/

    public <T> InterfaceManager invoke(Runnable runnable,Callback<T> callback){
        if(callback!=null){
            if(!map.containsKey(callback.getKey())){
                map.put(callback.getKey(),callback);
            }
        }
        return this;
    }

    public void removeInterfacw(Callback callback){
        if(callback!=null){
            if(map.containsKey(callback.getKey())){
                map.remove(callback.getKey());
            }
        }
    }
}
