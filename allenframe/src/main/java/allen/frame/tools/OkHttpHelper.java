package allen.frame.tools;

import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHelper {

    private static OkHttpHelper helper;
    OkHttpClient client;
    private OkHttpHelper(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
    }
    public static OkHttpHelper getInstance(){
        if(helper==null){
            helper = new OkHttpHelper();
        }
        return helper;
    }

    public static final MediaType xmltype
            = MediaType.get("application/soap+xml; charset=utf-8");

    public String postSoap(String url, String method, String[] array, String header, String spaceName, String[] headers){
        RequestBody body = RequestBody.create(xmltype, soapBody(method,array,header,spaceName,headers));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return new SoapObject().xmlPullToJosn(response.body().string(),method);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"Code\":\"500\",\"Msg\":\"网络异常!\",\"Data\":\"\",\"Count\":0}";
        }
    }

    private String soapBody(String method,String[] array,String header,String spaceName,String[] headers){
        StringBuffer sb = new StringBuffer();
        StringBuffer headsb = new StringBuffer();
        if(array!=null){
            for(int i=0;i<array.length;i++){
                String name = array[i++];
                String values = array[i];
                sb.append("<"+name+">");
                sb.append(values);
                sb.append("</"+name+">\n");
            }
        }
        if(headers!=null){
            for(int i=0;i<headers.length;i++){
                String name = headers[i++];
                String values = headers[i];
                headsb.append("<"+name+">");
                headsb.append(values);
                headsb.append("</"+name+">\n");
            }
        }
        String headOfBody = "";
        if(StringUtils.notEmpty(header)){
            headOfBody = "  <soap12:Header>\n" +
                    "    <"+header+" xmlns=\""+spaceName+"\">\n" +headsb.toString() +
                    "    </"+header+">\n" +
                    "  </soap12:Header>\n" ;
        }
        String httpBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                headOfBody +
                "  <soap12:Body>\n" +
                "    <"+method+" xmlns=\""+spaceName+"\">\n" +sb.toString()+
                "    </"+method+">\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";
        Logger.http("body:",httpBody);
        return httpBody;
    }

}
