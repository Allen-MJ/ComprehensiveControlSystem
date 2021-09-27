package allen.frame.tools;

import android.util.Xml;

import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SoapObject {
    private String Code = "-1";
    private String Msg = "数据获取失败!";
    private Object Data;
    private int Count;
    public SoapObject(){}

    public SoapObject(String code, String msg, Object data, int count) {
        Code = code;
        Msg = msg;
        Data = data;
        Count = count;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String xmlPullToJosn(String xml, String method){
        String json = "";
        String genTAG = method+"Result";
        Logger.http("xml->",xml);
        ByteArrayInputStream tInputStringStream = null;
        try
        {
            if (StringUtils.notEmpty(xml)) {
                tInputStringStream = new ByteArrayInputStream(xml.getBytes());
                Logger.http("soap->","1");
            }else{
                return "{\"Code\":\"500\",\"Msg\":\"网络异常!\",\"Data\":\"\",\"Count\":0}";
            }
        }
        catch (Exception e) {
            Logger.http("soap->","2");
            return "{\"Code\":\"500\",\"Msg\":\"网络异常!\",\"Data\":\"\",\"Count\":0}";
        }
        if(!xml.contains("<"+genTAG+">")){
            Logger.http("soap->","3");
            return "{\"Code\":\"500\",\"Msg\":\"网络异常!\",\"Data\":\"\",\"Count\":0}";
        }
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(tInputStringStream,"UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
                        break;
                    case XmlPullParser.START_TAG:// 开始元素事件
                        String name = parser.getName();
                        if(name.equals(genTAG)){
                            Logger.http("soap->","4");
                            json = parser.nextText();
                            Logger.http("soap->",json);
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        break;
                }
                eventType = parser.next();
            }
            tInputStringStream.close();
            Logger.http("soap->","++");
            return  json;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Logger.http("soap->","7");
            return "{\"Code\":\"500\",\"Msg\":\"数据异常!\",\"Data\":\"\",\"Count\":0}";
        } catch (IOException e) {
            e.printStackTrace();
            Logger.http("soap->","8");
            return "{\"Code\":\"500\",\"Msg\":\"数据异常!\",\"Data\":\"\",\"Count\":0}";
        }
    }
}
