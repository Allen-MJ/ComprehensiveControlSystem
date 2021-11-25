package cn.lyj.core.entry;

import java.io.Serializable;

public class RentHouse implements Serializable {

    /**
     * b1600 : 25312aed34cc4abb9985658abbe9966d
     * b1601 : 9528
     * b1602 : 撒地方感受到干
     * b1603 : 01
     * b1604 : 88
     * b1605 : 111
     * b1606 : 500222199203041251
     * b1607 : 试试
     * b1608 : 13525874859
     * b1609 : 大法师
     * b1610 : 02
     * b1611 : 00
     * b1612 : 5646546546
     * b1613 : 实打实
     * b1614 : 13685748596
     * b1690 : 222.102
     * b1691 : 180.231
     * createBy : admin
     * createTime : 2021-10-08 17:52:23
     * deleted : 0
     * gidObj : {}
     * updateBy : admin
     * updateTime : 2021-10-08 18:19:52
     */

    private String b1600;
    private String b1601;
    private String b1602;
    private String b1603;
    private String b1604;
    private String b1605;
    private String b1606;
    private String b1607;
    private String b1608;
    private String b1609;
    private String b1610;
    private String b1611;
    private String b1612;
    private String b1613;
    private String b1614;
    private double b1690;
    private double b1691;
    private String createBy;
    private String createTime;
    private int deleted;
    private GidObjBean gidObj;
    private String updateBy;
    private String updateTime;

    public String getB1600() {
        return b1600;
    }

    public void setB1600(String b1600) {
        this.b1600 = b1600;
    }

    public String getB1601() {
        return b1601;
    }

    public void setB1601(String b1601) {
        this.b1601 = b1601;
    }

    public String getB1602() {
        return b1602;
    }

    public void setB1602(String b1602) {
        this.b1602 = b1602;
    }

    public String getB1603() {
        return b1603;
    }

    public void setB1603(String b1603) {
        this.b1603 = b1603;
    }

    public String getB1604() {
        return b1604;
    }

    public void setB1604(String b1604) {
        this.b1604 = b1604;
    }

    public String getB1605() {
        return b1605;
    }

    public void setB1605(String b1605) {
        this.b1605 = b1605;
    }

    public String getB1606() {
        return b1606;
    }

    public void setB1606(String b1606) {
        this.b1606 = b1606;
    }

    public String getB1607() {
        return b1607;
    }

    public void setB1607(String b1607) {
        this.b1607 = b1607;
    }

    public String getB1608() {
        return b1608;
    }

    public void setB1608(String b1608) {
        this.b1608 = b1608;
    }

    public String getB1609() {
        return b1609;
    }

    public void setB1609(String b1609) {
        this.b1609 = b1609;
    }

    public String getB1610() {
        return b1610;
    }

    public void setB1610(String b1610) {
        this.b1610 = b1610;
    }

    public String getB1611() {
        return b1611;
    }

    public void setB1611(String b1611) {
        this.b1611 = b1611;
    }

    public String getB1612() {
        return b1612;
    }

    public void setB1612(String b1612) {
        this.b1612 = b1612;
    }

    public String getB1613() {
        return b1613;
    }

    public void setB1613(String b1613) {
        this.b1613 = b1613;
    }

    public String getB1614() {
        return b1614;
    }

    public void setB1614(String b1614) {
        this.b1614 = b1614;
    }

    public double getB1690() {
        return b1690;
    }

    public void setB1690(double b1690) {
        this.b1690 = b1690;
    }

    public double getB1691() {
        return b1691;
    }

    public void setB1691(double b1691) {
        this.b1691 = b1691;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public GidObjBean getGidObj() {
        return gidObj;
    }

    public void setGidObj(GidObjBean gidObj) {
        this.gidObj = gidObj;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public static class GidObjBean implements Serializable{
        private String orgFullName;
        private String orgId;
        private String orgName;
        private String orgNo;

        public String getOrgFullName() {
            return orgFullName;
        }

        public void setOrgFullName(String orgFullName) {
            this.orgFullName = orgFullName;
        }

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
    }
}
