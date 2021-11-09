package cn.lyj.core.entry;

import java.io.Serializable;

public class LoginAuth implements Serializable {
    private String uuid;
    private String img;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
