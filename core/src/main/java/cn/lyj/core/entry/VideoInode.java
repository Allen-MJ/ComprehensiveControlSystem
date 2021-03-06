package cn.lyj.core.entry;

import java.io.Serializable;

public class VideoInode implements Serializable {
    private double inodeLat;
    private double inodeLng;
    private String inodeName;
    private String inodeId;

    public String getInodeId() {
        return inodeId;
    }

    public void setInodeId(String inodeId) {
        this.inodeId = inodeId;
    }

    public double getInodeLat() {
        return inodeLat;
    }

    public void setInodeLat(double inodeLat) {
        this.inodeLat = inodeLat;
    }

    public double getInodeLng() {
        return inodeLng;
    }

    public void setInodeLng(double inodeLng) {
        this.inodeLng = inodeLng;
    }

    public String getInodeName() {
        return inodeName;
    }

    public void setInodeName(String inodeName) {
        this.inodeName = inodeName;
    }
}
