package allen.frame.entry;

import java.io.Serializable;

public class Video implements Serializable {
    private String title;
    private String info;
    private String url;
    private int type = VideoTypeNet;
    public static final int VideoTypeNet = 0;//网络视频
    public static final int VideoTypeLocal = 1;//本地视频
    public static final int VideoTypeLive = 2;//直播

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
