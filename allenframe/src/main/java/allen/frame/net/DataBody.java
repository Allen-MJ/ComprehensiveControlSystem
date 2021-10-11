package allen.frame.net;

import allen.frame.tools.Logger;
import allen.frame.tools.StringUtils;

public class DataBody {

    public String jsonBody(Object[] arrays){
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
}
