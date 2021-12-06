package cn.lyj.core.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class XmsfPersonEntity implements Serializable {

    /**
     * b1700 : 03aebbda969344739628655b6e051f96
     * b1701 : 568912195602033698
     * b1702 : 李方发
     * b1704 : 1
     * b1705 : 1956-02-03
     * b1706 : 01
     * b1707 : 四川
     * b1708 : 30
     * b1709 : 13
     * b1709Obj : {"code":"13","codeName":"群众"}
     * b1710 : 71
     * b1710Obj : {"code":"71","codeName":"初中"}
     * b1711 : 00
     * b1712 : 3
     * b1713 : 护林员
     * b1714 : 场所
     * b1715 : 18923515689
     * b1716 : 130000
     * b1716Obj : {"code":"130000","codeName":"河北省"}
     * b1717 : 青奥支路45号
     * b1718 : 230100
     * b1718Obj : {"code":"230100","codeName":"黑龙江省哈尔滨市"}
     * b1719 : 兼备之路23号
     * b1720 : 0
     * b1721 : 01
     * b1722 : 5
     * b1723 : 武汉第一监狱
     * b1724 : 2017-11-13
     * b1725 : 01
     * b1726 : 2018-11-12
     * b1727 : 06
     * b1728 : 2021-11-01
     * b1729 : 07
     * b1730 : 已安置
     * b1731 : 已帮教
     * b1732 : 0
     * createBy : admin
     * createTime : 2021-11-08 15:19:57
     * deleted : 0
     * gid : 2c9180907ce91391017cea32cbc30005
     * gidObj : {"orgFullName":"1104桃树网格","orgId":"2c9180907ce91391017cea32cbc30005","orgName":"1104桃树网格","orgNo":"500111123002001"}
     * updateBy : admin
     * updateTime : 2021-11-08 15:48:12
     */

    private String b1700;
    private String b1701;
    private String b1702;
    private String b1703;
    private String b1704;
    private String b1704Name;
    private String b1705;
    private String b1706;
    private String b1707;
    private String b1708;
    private String b1709;
    private B1709ObjBean b1709Obj;
    private String b1710;
    private B1710ObjBean b1710Obj;
    private String b1711;
    private String b1712;
    private String b1713;
    private String b1714;
    private String b1715;
    private String b1716;
    private B1716ObjBean b1716Obj;
    private String b1717;
    private String b1718;
    private B1718ObjBean b1718Obj;
    private String b1719;
    private String b1720;
    private String b1720Name;
    private String b1721;
    private String b1722;
    private String b1723;
    private String b1724;
    private String b1725;
    private String b1726;
    private String b1727;
    private String b1728;
    private String b1729;
    private String b1730;
    private String b1731;
    private String b1732;
    private String b1732Name;
    private String b1733;
    private String createBy;
    private String createTime;
    private int deleted;
    private String gid;
    private GidObjBean gidObj;
    private String updateBy;
    private String updateTime;
    private String picturePath;

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getB1700() {
        return b1700;
    }

    public void setB1700(String b1700) {
        this.b1700 = b1700;
    }

    public String getB1701() {
        return b1701;
    }

    public void setB1701(String b1701) {
        this.b1701 = b1701;
    }

    public String getB1702() {
        return b1702;
    }

    public void setB1702(String b1702) {
        this.b1702 = b1702;
    }

    public String getB1703() {
        return b1703;
    }

    public void setB1703(String b1703) {
        this.b1703 = b1703;
    }

    public String getB1704() {
        return b1704;
    }

    public void setB1704(String b1704) {
        this.b1704 = b1704;
    }

    public String getB1704Name() {
        String name = "";
        switch (b1704){
            case "1":
                name = "男";
                break;
            case "2":
                name = "女";
                break;
        }
        return StringUtils.empty(name)?b1704Name:name;
    }

    public void setB1704Name(String b1704Name) {
        this.b1704Name = b1704Name;
    }

    public String getB1705() {
        return b1705;
    }

    public void setB1705(String b1705) {
        this.b1705 = b1705;
    }

    public String getB1706() {
        return b1706;
    }

    public void setB1706(String b1706) {
        this.b1706 = b1706;
    }

    public String getB1707() {
        return b1707;
    }

    public void setB1707(String b1707) {
        this.b1707 = b1707;
    }

    public String getB1708() {
        return b1708;
    }

    public void setB1708(String b1708) {
        this.b1708 = b1708;
    }

    public String getB1709() {
        return b1709;
    }

    public void setB1709(String b1709) {
        this.b1709 = b1709;
    }

    public B1709ObjBean getB1709Obj() {
        return b1709Obj;
    }

    public void setB1709Obj(B1709ObjBean b1709Obj) {
        this.b1709Obj = b1709Obj;
    }

    public String getB1710() {
        return b1710;
    }

    public void setB1710(String b1710) {
        this.b1710 = b1710;
    }

    public B1710ObjBean getB1710Obj() {
        return b1710Obj;
    }

    public void setB1710Obj(B1710ObjBean b1710Obj) {
        this.b1710Obj = b1710Obj;
    }

    public String getB1711() {
        return b1711;
    }

    public void setB1711(String b1711) {
        this.b1711 = b1711;
    }

    public String getB1712() {
        return b1712;
    }

    public void setB1712(String b1712) {
        this.b1712 = b1712;
    }

    public String getB1713() {
        return b1713;
    }

    public void setB1713(String b1713) {
        this.b1713 = b1713;
    }

    public String getB1714() {
        return b1714;
    }

    public void setB1714(String b1714) {
        this.b1714 = b1714;
    }

    public String getB1715() {
        return b1715;
    }

    public void setB1715(String b1715) {
        this.b1715 = b1715;
    }

    public String getB1716() {
        return b1716;
    }

    public void setB1716(String b1716) {
        this.b1716 = b1716;
    }

    public B1716ObjBean getB1716Obj() {
        return b1716Obj;
    }

    public void setB1716Obj(B1716ObjBean b1716Obj) {
        this.b1716Obj = b1716Obj;
    }

    public String getB1717() {
        return b1717;
    }

    public void setB1717(String b1717) {
        this.b1717 = b1717;
    }

    public String getB1718() {
        return b1718;
    }

    public void setB1718(String b1718) {
        this.b1718 = b1718;
    }

    public B1718ObjBean getB1718Obj() {
        return b1718Obj;
    }

    public void setB1718Obj(B1718ObjBean b1718Obj) {
        this.b1718Obj = b1718Obj;
    }

    public String getB1719() {
        return b1719;
    }

    public void setB1719(String b1719) {
        this.b1719 = b1719;
    }

    public String getB1720() {
        return b1720;
    }

    public void setB1720(String b1720) {
        this.b1720 = b1720;
    }

    public String getB1720Name() {
        String name = "";
        switch (b1720){
            case "0":
                name="否";
                break;
            case "1":
                name="是";
                break;
        }
        return name;
    }
    public String getB1732Name() {
        String name = "";
        switch (b1732){
            case "0":
                name="否";
                break;
            case "1":
                name="是";
                break;
        }
        return name;
    }

    public void setB1720Name(String b1720Name) {
        this.b1720Name = b1720Name;
    }

    public String getB1721() {
        return b1721;
    }

    public void setB1721(String b1721) {
        this.b1721 = b1721;
    }

    public String getB1722() {
        return b1722;
    }

    public void setB1722(String b1722) {
        this.b1722 = b1722;
    }

    public String getB1723() {
        return b1723;
    }

    public void setB1723(String b1723) {
        this.b1723 = b1723;
    }

    public String getB1724() {
        return b1724;
    }

    public void setB1724(String b1724) {
        this.b1724 = b1724;
    }

    public String getB1725() {
        return b1725;
    }

    public void setB1725(String b1725) {
        this.b1725 = b1725;
    }

    public String getB1726() {
        return b1726;
    }

    public void setB1726(String b1726) {
        this.b1726 = b1726;
    }

    public String getB1727() {
        return b1727;
    }

    public void setB1727(String b1727) {
        this.b1727 = b1727;
    }

    public String getB1728() {
        return b1728;
    }

    public void setB1728(String b1728) {
        this.b1728 = b1728;
    }

    public String getB1729() {
        return b1729;
    }

    public void setB1729(String b1729) {
        this.b1729 = b1729;
    }

    public String getB1730() {
        return b1730;
    }

    public void setB1730(String b1730) {
        this.b1730 = b1730;
    }

    public String getB1731() {
        return b1731;
    }

    public void setB1731(String b1731) {
        this.b1731 = b1731;
    }

    public String getB1732() {
        return b1732;
    }

    public void setB1732(String b1732) {
        this.b1732 = b1732;
    }

    public String getB1733() {
        return b1733;
    }

    public void setB1733(String b1733) {
        this.b1733 = b1733;
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

    public static class B1709ObjBean implements Serializable{
        /**
         * code : 13
         * codeName : 群众
         */

        private String code;
        private String codeName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }
    }

    public static class B1710ObjBean implements Serializable{
        /**
         * code : 71
         * codeName : 初中
         */

        private String code;
        private String codeName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }
    }

    public static class B1716ObjBean implements Serializable{
        /**
         * code : 130000
         * codeName : 河北省
         */

        private String code;
        private String codeName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }
    }

    public static class B1718ObjBean implements Serializable{
        /**
         * code : 230100
         * codeName : 黑龙江省哈尔滨市
         */

        private String code;
        private String codeName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }
    }

    public static class GidObjBean implements Serializable{
        /**
         * orgFullName : 1104桃树网格
         * orgId : 2c9180907ce91391017cea32cbc30005
         * orgName : 1104桃树网格
         * orgNo : 500111123002001
         */

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
