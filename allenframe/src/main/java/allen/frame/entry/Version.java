package allen.frame.entry;

import java.io.Serializable;

public class Version implements Serializable {
    private String appId;
    private String appVersion;
    private String appUrl;
    private boolean appCompel;
    private String appPath;
    private String appDesc;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public boolean isAppCompel() {
        return appCompel;
    }

    public void setAppCompel(boolean appCompel) {
        this.appCompel = appCompel;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }
}
