package allen.frame.entry;

import java.io.Serializable;

public class File implements Serializable {
    private String name;
    private String path;
    private String url;
    private int type = 0;//0图片，2word，3音频，4视频
    private String suffix = "image/png";

    public File() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public java.io.File getFile(){
        return new java.io.File(path);
    }
}
