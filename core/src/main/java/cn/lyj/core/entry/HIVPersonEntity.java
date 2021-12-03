package cn.lyj.core.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class HIVPersonEntity implements Serializable {

    /**
     * b2100 : 2cdb4f2cb27d4316a6721bb1f2af1551
     * b2101 : 500223196805021254
     * b2102 : 测试
     * b2103 :
     * b2104 : 1
     * b2105 : 1968-05-02
     * b2106 : 03
     * b2107 : 啊实打实的
     * b2108 : 1
     * b2109 : 13
     * b2109Obj : {"code":"13","codeName":"群众"}
     * b2110 : 71
     * b2110Obj : {"code":"71","codeName":"初中"}
     * b2111 : 20
     * b2112 : 5
     * b2113 : 森岛帆高森岛帆高
     * b2114 :
     * b2115 : 15245457896
     * b2116 : 632623
     * b2116Obj : {"code":"632623","codeName":"青海省果洛藏族自治州甘德县"}
     * b2117 : 粉红色的华发商都华发商都
     * b2118 : 632623
     * b2118Obj : {"code":"632623","codeName":"青海省果洛藏族自治州甘德县"}
     * b2119 : 粉红色的华发商都华发商都
     * b2120 : 01
     * b2121 : 1
     * b2122 : 阿斯顿发斯蒂芬
     * b2123 : 020000
     * b2124 : 00
     * b2125 :
     * b2126 : 按时发散
     * b2127 : 17585857485
     * b2129 :
     * createBy : admin
     * createTime : 2021-10-12 11:50:14
     * deleted : 0
     * gidObj : {}
     * updateBy : admin
     * updateTime : 2021-10-12 11:50:14
     */

    private String b2100;
    private String b2101;
    private String b2102;
    private String b2103;
    private String b2104;
    private String b2104Name;//性别
    private String b2105;
    private String b2106;
    private String b2107;
    private String b2108;
    private String b2109;
    private B2109ObjBean b2109Obj;
    private String b2110;
    private B2110ObjBean b2110Obj;
    private String b2111;
    private String b2112;
    private String b2113;
    private String b2114;
    private String b2115;
    private String b2116;
    private B2116ObjBean b2116Obj;
    private String b2117;
    private String b2118;
    private B2118ObjBean b2118Obj;
    private String b2119;
    private String b2120;
    private String b2121;
    private String b2121Name;
    private String b2122;
    private String b2123;
    private String b2124;
    private String b2125;
    private String b2126;
    private String b2127;
    private String b2128;
    private String b2129;
    private String createBy;
    private String createTime;
    private int deleted;
    private String gid;
    private GidObjBean gidObj;
    private String updateBy;
    private String updateTime;

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    private String picture_path;

    public String getB2100() {
        return b2100;
    }

    public void setB2100(String b2100) {
        this.b2100 = b2100;
    }

    public String getB2101() {
        return b2101;
    }

    public void setB2101(String b2101) {
        this.b2101 = b2101;
    }

    public String getB2102() {
        return b2102;
    }

    public void setB2102(String b2102) {
        this.b2102 = b2102;
    }

    public String getB2103() {
        return b2103;
    }

    public void setB2103(String b2103) {
        this.b2103 = b2103;
    }

    public String getB2104() {
        return b2104;
    }

    public void setB2104(String b2104) {
        this.b2104 = b2104;
    }

    public String getB2104Name() {
        String name = "";
        switch (b2104){
            case "1":
                name = "男";
                break;
            case "2":
                name = "女";
                break;
        }
        return StringUtils.empty(name)?b2104Name:name;
    }

    public void setB2104Name(String b2104Name) {
        this.b2104Name = b2104Name;
    }

    public String getB2105() {
        return b2105;
    }

    public void setB2105(String b2105) {
        this.b2105 = b2105;
    }

    public String getB2106() {
        return b2106;
    }

    public void setB2106(String b2106) {
        this.b2106 = b2106;
    }

    public String getB2107() {
        return b2107;
    }

    public void setB2107(String b2107) {
        this.b2107 = b2107;
    }

    public String getB2108() {
        return b2108;
    }

    public void setB2108(String b2108) {
        this.b2108 = b2108;
    }

    public String getB2109() {
        return b2109;
    }

    public void setB2109(String b2109) {
        this.b2109 = b2109;
    }

    public B2109ObjBean getB2109Obj() {
        return b2109Obj;
    }

    public void setB2109Obj(B2109ObjBean b2109Obj) {
        this.b2109Obj = b2109Obj;
    }

    public String getB2110() {
        return b2110;
    }

    public void setB2110(String b2110) {
        this.b2110 = b2110;
    }

    public B2110ObjBean getB2110Obj() {
        return b2110Obj;
    }

    public void setB2110Obj(B2110ObjBean b2110Obj) {
        this.b2110Obj = b2110Obj;
    }

    public String getB2111() {
        return b2111;
    }

    public void setB2111(String b2111) {
        this.b2111 = b2111;
    }

    public String getB2112() {
        return b2112;
    }

    public void setB2112(String b2112) {
        this.b2112 = b2112;
    }

    public String getB2113() {
        return b2113;
    }

    public void setB2113(String b2113) {
        this.b2113 = b2113;
    }

    public String getB2114() {
        return b2114;
    }

    public void setB2114(String b2114) {
        this.b2114 = b2114;
    }

    public String getB2115() {
        return b2115;
    }

    public void setB2115(String b2115) {
        this.b2115 = b2115;
    }

    public String getB2116() {
        return b2116;
    }

    public void setB2116(String b2116) {
        this.b2116 = b2116;
    }

    public B2116ObjBean getB2116Obj() {
        return b2116Obj;
    }

    public void setB2116Obj(B2116ObjBean b2116Obj) {
        this.b2116Obj = b2116Obj;
    }

    public String getB2117() {
        return b2117;
    }

    public void setB2117(String b2117) {
        this.b2117 = b2117;
    }

    public String getB2118() {
        return b2118;
    }

    public void setB2118(String b2118) {
        this.b2118 = b2118;
    }

    public B2118ObjBean getB2118Obj() {
        return b2118Obj;
    }

    public void setB2118Obj(B2118ObjBean b2118Obj) {
        this.b2118Obj = b2118Obj;
    }

    public String getB2119() {
        return b2119;
    }

    public void setB2119(String b2119) {
        this.b2119 = b2119;
    }

    public String getB2120() {
        return b2120;
    }

    public void setB2120(String b2120) {
        this.b2120 = b2120;
    }

    public String getB2121() {
        return b2121;
    }

    public void setB2121(String b2121) {
        this.b2121 = b2121;
    }

    public String getB2121Name() {
        String name = "";
        switch (b2121){
            case "0":
                name = "否";
                break;
            case "1":
                name = "是";
                break;
        }
        return StringUtils.empty(name)?b2121Name:name;
    }

    public void setB2121Name(String b2121Name) {
        this.b2121Name = b2121Name;
    }

    public String getB2122() {
        return b2122;
    }

    public void setB2122(String b2122) {
        this.b2122 = b2122;
    }

    public String getB2123() {
        return b2123;
    }

    public void setB2123(String b2123) {
        this.b2123 = b2123;
    }

    public String getB2124() {
        return b2124;
    }

    public void setB2124(String b2124) {
        this.b2124 = b2124;
    }

    public String getB2125() {
        return b2125;
    }

    public void setB2125(String b2125) {
        this.b2125 = b2125;
    }

    public String getB2126() {
        return b2126;
    }

    public void setB2126(String b2126) {
        this.b2126 = b2126;
    }

    public String getB2127() {
        return b2127;
    }

    public void setB2127(String b2127) {
        this.b2127 = b2127;
    }

    public String getB2128() {
        return b2128;
    }

    public void setB2128(String b2128) {
        this.b2128 = b2128;
    }

    public String getB2129() {
        return b2129;
    }

    public void setB2129(String b2129) {
        this.b2129 = b2129;
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

    public static class B2109ObjBean implements Serializable{
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

    public static class B2110ObjBean implements Serializable{
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

    public static class B2116ObjBean implements Serializable{
        /**
         * code : 632623
         * codeName : 青海省果洛藏族自治州甘德县
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

    public static class B2118ObjBean implements Serializable{
        /**
         * code : 632623
         * codeName : 青海省果洛藏族自治州甘德县
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
         * orgFullName : 石香社区居委会公共场所
         * orgId : 402880997c9b45a0017c9b6e6a8b0000
         * orgName : 公共场所
         * orgNo : 500111103001004
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
