package cn.lyj.core.entry;

import java.io.Serializable;

public class CzPersonEntity implements Serializable {

    /**
     * bcz00 : 01ece27134e54444931870801e325acd
     * bcz01 : 500226199905021635
     * bcz02 : 测试2
     * bcz03 : 1999-05-02
     * bcz04 : 01
     * bcz06 : 阿士大夫撒
     * createBy : admin
     * createTime : 2021-11-08 15:34:19
     * deleted : 0
     * gid : 402880997ca33623017ca33eb6be0002
     * gidObj : {"orgFullName":"测试社区委员会御风","orgId":"402880997ca33623017ca33eb6be0002","orgName":"御风","orgNo":"500111106001002"}
     * updateBy : admin
     * updateTime : 2021-11-08 15:34:19
     */

    private String bcz00;
    private String bcz01;
    private String bcz02;
    private String bcz03;
    private String bcz04;
    private String bcz06;
    private String createBy;
    private String createTime;
    private int deleted;
    private String gid;
    private GidObjBean gidObj;
    private String updateBy;
    private String updateTime;

    public String getBcz00() {
        return bcz00;
    }

    public void setBcz00(String bcz00) {
        this.bcz00 = bcz00;
    }

    public String getBcz01() {
        return bcz01;
    }

    public void setBcz01(String bcz01) {
        this.bcz01 = bcz01;
    }

    public String getBcz02() {
        return bcz02;
    }

    public void setBcz02(String bcz02) {
        this.bcz02 = bcz02;
    }

    public String getBcz03() {
        return bcz03;
    }

    public void setBcz03(String bcz03) {
        this.bcz03 = bcz03;
    }

    public String getBcz04() {
        return bcz04;
    }

    public void setBcz04(String bcz04) {
        this.bcz04 = bcz04;
    }

    public String getBcz06() {
        return bcz06;
    }

    public void setBcz06(String bcz06) {
        this.bcz06 = bcz06;
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

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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
        /**
         * orgFullName : 测试社区委员会御风
         * orgId : 402880997ca33623017ca33eb6be0002
         * orgName : 御风
         * orgNo : 500111106001002
         */

        private String orgFullName;
        private String orgId;
        private String orgName;
        private String orgNo;
        private String genericName;

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

        public String getGenericName() {
            return genericName;
        }

        public void setGenericName(String genericName) {
            this.genericName = genericName;
        }
    }
}
