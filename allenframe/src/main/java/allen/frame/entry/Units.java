package allen.frame.entry;

import java.io.Serializable;

public class Units implements Serializable {
    private String orgId;
    private String orgName;
    private String orgNo;
    private String orgFullName;
    private int orgLevel;
    private String pid;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgFullName() {
        return orgFullName;
    }

    public void setOrgFullName(String orgFullName) {
        this.orgFullName = orgFullName;
    }

    public int getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(int orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
