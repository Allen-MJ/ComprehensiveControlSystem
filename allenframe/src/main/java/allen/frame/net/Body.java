package allen.frame.net;

import com.google.gson.Gson;

import java.util.Map;

import allen.frame.tools.Logger;
import allen.frame.tools.StringUtils;
import allen.frame.widget.GoodView;

public class Body {

    public String postJson(Object[] arrays){
        if(arrays!=null){
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            if (arrays != null) {
                for (int i = 0; i < arrays.length; i++) {
                    sb.append((i == 0 ? "\"" : ",\"") + arrays[i++] + "\":" + StringUtils.getObject(arrays[i]));
                }
            }
            sb.append("}");
            Logger.http("post:body->",  sb.toString() );
            return sb.toString();
        }
        return "";
    }
    public String get(Object[] arrays) {
        StringBuilder sb = new StringBuilder();
        if (arrays != null) {
            for (int i = 0; i < arrays.length; i++) {
                sb.append((i == 0 ? "" : "&") + arrays[i++] + "="
                        + (arrays[i] == null ? "" : StringUtils.null2Empty(arrays[i].toString())));
            }
        }
        Logger.http("get:url->",  sb.toString());
        return sb.toString();
    }

    public String post(Map<String,Object> params){
        if(params!=null){
            Gson gson = new Gson();
            if (params.size()>0) {
                return gson.toJson(params);
            }
        }
        return "";
    }

    public String get(Map<String,Object> params){
        if(params!=null){
            if (params.size()>0) {
                StringBuilder sb = new StringBuilder();
                int i = 0;
                for(Map.Entry<String,Object> entry:params.entrySet()){
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    sb.append((i == 0 ? "" : "&") + key + "="
                            + (value == null ? "" : StringUtils.null2Empty(value.toString())));
                    i++;
                }
                return sb.toString();
            }
        }
        return "";
    }
}
