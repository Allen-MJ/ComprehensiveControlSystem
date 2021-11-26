package cn.lyj.thepublic.entry;

import java.io.Serializable;

public class LifeService implements Serializable {
    /**
     * Id : 0
     * LName : string
     * LFilePath : string
     * AppLAHref : string
     * LAHref : string
     * Isdel : 0
     */

    private int Id;
    private String LName;
    private String LFilePath;
    private String AppLAHref;
    private String LAHref;
    private int Isdel;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getLFilePath() {
        return LFilePath;
    }

    public void setLFilePath(String LFilePath) {
        this.LFilePath = LFilePath;
    }

    public String getLAHref() {
        return LAHref;
    }

    public void setLAHref(String LAHref) {
        this.LAHref = LAHref;
    }

    public String getAppLAHref() {
        return AppLAHref;
    }

    public void setAppLAHref(String appLAHref) {
        AppLAHref = appLAHref;
    }

    public int getIsdel() {
        return Isdel;
    }

    public void setIsdel(int Isdel) {
        this.Isdel = Isdel;
    }
}
