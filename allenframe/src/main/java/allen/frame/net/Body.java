package allen.frame.net;

import allen.frame.tools.Logger;
import allen.frame.tools.StringUtils;

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
}
