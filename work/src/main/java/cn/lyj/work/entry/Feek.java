package cn.lyj.work.entry;

import java.io.Serializable;

public class Feek implements Serializable {
    private String dwname;
    private String fkfs;
    private String fksj;
    private String fkr;
    private String fkms;
    public Feek(){}

    public void setDwname(String dwname) {
        this.dwname = dwname;
    }

    public String getDwname() {
        return dwname;
    }

    public void setFkfs(String fkfs) {
        this.fkfs = fkfs;
    }

    public String getFkfs() {
        return fkfs;
    }

    public void setFkr(String fkr) {
        this.fkr = fkr;
    }

    public String getFkr() {
        return fkr;
    }

    public void setFksj(String fksj) {
        this.fksj = fksj;
    }

    public String getFksj() {
        return fksj;
    }

    public void setFkms(String fkms) {
        this.fkms = fkms;
    }

    public String getFkms() {
        return fkms;
    }
}
