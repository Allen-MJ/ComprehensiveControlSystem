package cn.lyj.core.entry;

import java.io.Serializable;

public class SqjzPersonEntity implements Serializable {

    /**
     * b1800 : 7d70f385783e491aaed6390f38038bf1
     * b1801 : 513029184753025305
     * b1802 : 123
     * b1804 : 2
     * b1805 : 1847-53-02
     * b1806 : 01
     * b1807 : 四
     * b1808 : 1
     * b1809 : 01
     * b1809Obj : {"code":"01","codeName":"中共党员"}
     * b1810 : 1
     * b1810Obj : {"code":"1","codeName":"研究生教育"}
     * b1811 :
     * b1812 : 1
     * b1815 : 15914158052
     * b1816 : 110000
     * b1816Obj : {"code":"110000","codeName":"北京市"}
     * b1817 : 2
     * b1818 : 110000
     * b1818Obj : {"code":"110000","codeName":"北京市"}
     * b1819 : 1
     * b1820 : 12
     * b1822 : 01
     * b1823 : 020000
     * b1824 : 123213123
     * b1828 : 2021-09-30
     * b1829 : 2021-09-28
     * b1830 : 01
     * b1831 :
     * b1832 : 1
     * b1833 : ["01","02"]
     * b1834 : 1
     * b1835 : ["01","02"]
     * b1837 : 1
     * b1841 : 1
     * b1848 : 05
     * createBy : admin
     * createTime : 2021-10-18 14:01:10
     * deleted : 0
     * gid : 402880997c90f363017c9117897d0000
     * gidObj : {"orgFullName":"石香社区居委会紫田网格","orgId":"402880997c90f363017c9117897d0000","orgName":"紫田网格","orgNo":"500111103001003"}
     * picturePath : /file\图片\微信图片_20210623112536-20211026114221922.jpg
     * updateBy : admin
     * updateTime : 2021-11-03 13:59:56
     */

    private String b1800;
    private String b1801;
    private String b1802;
    private String b1803;
    private String b1804;
    private String b1804Name;
    private String b1805;
    private String b1806;
    private String b1807;
    private String b1808;
    private String b1809;
    private B1809ObjBean b1809Obj;
    private String b1810;
    private B1810ObjBean b1810Obj;
    private String b1811;
    private String b1812;
    private String b1813;
    private String b1814;
    private String b1815;
    private String b1816;
    private B1816ObjBean b1816Obj;
    private String b1817;
    private String b1818;
    private B1818ObjBean b1818Obj;
    private String b1819;
    private String b1820;
    private String b1822;
    private String b1823;
    private String b1824;
    private String b1828;
    private String b1829;
    private String b1830;
    private String b1831;
    private String b1832;
    private String b1833;
    private String b1834;
    private String b1835;
    private String b1837;
    private String b1841;
    private String b1848;
    private String createBy;
    private String createTime;
    private int deleted;
    private String gid;
    private GidObjBean gidObj;
    private String picturePath;
    private String updateBy;
    private String updateTime;

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    private String picture_path;

    public String getB1800() {
        return b1800;
    }

    public void setB1800(String b1800) {
        this.b1800 = b1800;
    }

    public String getB1801() {
        return b1801;
    }

    public void setB1801(String b1801) {
        this.b1801 = b1801;
    }

    public String getB1802() {
        return b1802;
    }

    public void setB1802(String b1802) {
        this.b1802 = b1802;
    }

    public String getB1803() {
        return b1803;
    }

    public void setB1803(String b1803) {
        this.b1803 = b1803;
    }

    public String getB1804() {
        return b1804;
    }

    public String getB1804Name() {
        String name = "";
        switch (b1804){
            case "1":
                name = "男";
                break;
            case "2":
                name = "女";
                break;
        }
        return name;
    }

    public void setB1804(String b1804) {
        this.b1804 = b1804;
    }

    public String getB1805() {
        return b1805;
    }

    public void setB1805(String b1805) {
        this.b1805 = b1805;
    }

    public String getB1806() {
        return b1806;
    }

    public void setB1806(String b1806) {
        this.b1806 = b1806;
    }

    public String getB1807() {
        return b1807;
    }

    public void setB1807(String b1807) {
        this.b1807 = b1807;
    }

    public String getB1808() {
        return b1808;
    }

    public void setB1808(String b1808) {
        this.b1808 = b1808;
    }

    public String getB1809() {
        return b1809;
    }

    public void setB1809(String b1809) {
        this.b1809 = b1809;
    }

    public B1809ObjBean getB1809Obj() {
        return b1809Obj;
    }

    public void setB1809Obj(B1809ObjBean b1809Obj) {
        this.b1809Obj = b1809Obj;
    }

    public String getB1810() {
        return b1810;
    }

    public void setB1810(String b1810) {
        this.b1810 = b1810;
    }

    public B1810ObjBean getB1810Obj() {
        return b1810Obj;
    }

    public void setB1810Obj(B1810ObjBean b1810Obj) {
        this.b1810Obj = b1810Obj;
    }

    public String getB1811() {
        return b1811;
    }

    public void setB1811(String b1811) {
        this.b1811 = b1811;
    }

    public String getB1812() {
        return b1812;
    }

    public void setB1812(String b1812) {
        this.b1812 = b1812;
    }

    public String getB1813() {
        return b1813;
    }

    public void setB1813(String b1813) {
        this.b1813 = b1813;
    }

    public String getB1814() {
        return b1814;
    }

    public void setB1814(String b1814) {
        this.b1814 = b1814;
    }

    public String getB1815() {
        return b1815;
    }

    public void setB1815(String b1815) {
        this.b1815 = b1815;
    }

    public String getB1816() {
        return b1816;
    }

    public void setB1816(String b1816) {
        this.b1816 = b1816;
    }

    public B1816ObjBean getB1816Obj() {
        return b1816Obj;
    }

    public void setB1816Obj(B1816ObjBean b1816Obj) {
        this.b1816Obj = b1816Obj;
    }

    public String getB1817() {
        return b1817;
    }

    public void setB1817(String b1817) {
        this.b1817 = b1817;
    }

    public String getB1818() {
        return b1818;
    }

    public void setB1818(String b1818) {
        this.b1818 = b1818;
    }

    public B1818ObjBean getB1818Obj() {
        return b1818Obj;
    }

    public void setB1818Obj(B1818ObjBean b1818Obj) {
        this.b1818Obj = b1818Obj;
    }

    public String getB1819() {
        return b1819;
    }

    public void setB1819(String b1819) {
        this.b1819 = b1819;
    }

    public String getB1820() {
        return b1820;
    }

    public void setB1820(String b1820) {
        this.b1820 = b1820;
    }

    public String getB1822() {
        return b1822;
    }

    public void setB1822(String b1822) {
        this.b1822 = b1822;
    }

    public String getB1823() {
        return b1823;
    }

    public void setB1823(String b1823) {
        this.b1823 = b1823;
    }

    public String getB1824() {
        return b1824;
    }

    public void setB1824(String b1824) {
        this.b1824 = b1824;
    }

    public String getB1828() {
        return b1828;
    }

    public void setB1828(String b1828) {
        this.b1828 = b1828;
    }

    public String getB1829() {
        return b1829;
    }

    public void setB1829(String b1829) {
        this.b1829 = b1829;
    }

    public String getB1830() {
        return b1830;
    }

    public void setB1830(String b1830) {
        this.b1830 = b1830;
    }

    public String getB1831() {
        return b1831;
    }

    public void setB1831(String b1831) {
        this.b1831 = b1831;
    }

    public String getB1832() {
        return b1832;
    }

    public void setB1832(String b1832) {
        this.b1832 = b1832;
    }

    public String getB1833() {
        return b1833;
    }

    public void setB1833(String b1833) {
        this.b1833 = b1833;
    }

    public String getB1834() {
        return b1834;
    }

    public void setB1834(String b1834) {
        this.b1834 = b1834;
    }

    public String getB1835() {
        return b1835;
    }

    public void setB1835(String b1835) {
        this.b1835 = b1835;
    }

    public String getB1837() {
        return b1837;
    }

    public void setB1837(String b1837) {
        this.b1837 = b1837;
    }

    public String getB1841() {
        return b1841;
    }

    public void setB1841(String b1841) {
        this.b1841 = b1841;
    }

    public String getB1848() {
        return b1848;
    }

    public void setB1848(String b1848) {
        this.b1848 = b1848;
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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
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

    public static class B1809ObjBean implements Serializable{
        /**
         * code : 01
         * codeName : 中共党员
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

    public static class B1810ObjBean implements Serializable{
        /**
         * code : 1
         * codeName : 研究生教育
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

    public static class B1816ObjBean implements Serializable{
        /**
         * code : 110000
         * codeName : 北京市
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

    public static class B1818ObjBean implements Serializable{
        /**
         * code : 110000
         * codeName : 北京市
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
         * orgFullName : 石香社区居委会紫田网格
         * orgId : 402880997c90f363017c9117897d0000
         * orgName : 紫田网格
         * orgNo : 500111103001003
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
