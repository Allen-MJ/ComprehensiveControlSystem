package cn.lyj.core.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class DrugEntity implements Serializable {

    /**
     * b2000 : 3f8fb4eab8094064806677e462e0bfeb
     * b2001 : 500223199503042254
     * b2002 : 啊啊啊
     * b2003 :
     * b2004 : 1
     * b2005 : 1995-03-04
     * b2006 : 03
     * b2007 : 啊实打实
     * b2008 : 2
     * b2009 : 13
     * b2009Obj : {"code":"13","codeName":"群众"}
     * b2010 : 3
     * b2010Obj : {"code":"3","codeName":"专科教育"}
     * b2011 : 50
     * b2012 : 4
     * b2013 : 是打发斯蒂芬
     * b2014 :
     * b2015 : 13596857485
     * b2016 : 310230
     * b2016Obj : {"code":"310230","codeName":"上海市崇明县"}
     * b2017 : 较好的发挥地方
     * b2018 : 310230
     * b2018Obj : {"code":"310230","codeName":"上海市崇明县"}
     * b2019 : 较好的发挥地方
     * b2020 : 2015-02-02
     * b2021 : 01
     * b2022 : 阿萨德
     * b2023 : 19145878596
     * b2024 : 05
     * b2025 : 阿萨德
     * b2026 : 19145878596
     * b2027 : 0
     * b2028 : SaaS发斯蒂芬
     * b2029 : 05
     * b2030 : 20
     * createBy : admin
     * createTime : 2021-10-12 11:03:39
     * deleted : 0
     * gidObj : {}
     * updateBy : admin
     * updateTime : 2021-10-12 11:03:39
     */

    private String b2000;
    private String b2001;
    private String b2002;
    private String b2003;
    private String b2004;
    private String b2004Name;//性别
    private String b2005;
    private String b2006;
    private String b2007;
    private String b2008;
    private String b2009;
    private B2009ObjBean b2009Obj;
    private String b2010;
    private B2010ObjBean b2010Obj;
    private String b2011;
    private String b2012;
    private String b2013;
    private String b2014;
    private String b2015;
    private String b2016;
    private B2016ObjBean b2016Obj;
    private String b2017;
    private String b2018;
    private B2018ObjBean b2018Obj;
    private String b2019;
    private String b2020;
    private String b2021;
    private String b2022;
    private String b2023;
    private String b2024;
    private String b2025;
    private String b2026;
    private String b2027;
    private String b2028;
    private String b2029;
    private String b2030;
    private String createBy;
    private String createTime;
    private int deleted;
    private String gid;
    private GidObjBean gidObj;
    private String updateBy;
    private String updateTime;

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    private String picturePath;

    public String getB2000() {
        return b2000;
    }

    public void setB2000(String b2000) {
        this.b2000 = b2000;
    }

    public String getB2001() {
        return b2001;
    }

    public void setB2001(String b2001) {
        this.b2001 = b2001;
    }

    public String getB2002() {
        return b2002;
    }

    public void setB2002(String b2002) {
        this.b2002 = b2002;
    }

    public String getB2003() {
        return b2003;
    }

    public void setB2003(String b2003) {
        this.b2003 = b2003;
    }

    public String getB2004() {
        return b2004;
    }

    public void setB2004(String b2004) {
        this.b2004 = b2004;
    }

    public String getB2004Name() {
        String name = "";
        switch (b2004){
            case "1":
                name = "男";
                break;
            case "2":
                name = "女";
                break;
        }
        return StringUtils.empty(name)?b2004Name:name;
    }

    public void setB2004Name(String b2004Name) {
        this.b2004Name = b2004Name;
    }

    public String getB2005() {
        return b2005;
    }

    public void setB2005(String b2005) {
        this.b2005 = b2005;
    }

    public String getB2006() {
        return b2006;
    }

    public void setB2006(String b2006) {
        this.b2006 = b2006;
    }

    public String getB2007() {
        return b2007;
    }

    public void setB2007(String b2007) {
        this.b2007 = b2007;
    }

    public String getB2008() {
        return b2008;
    }

    public void setB2008(String b2008) {
        this.b2008 = b2008;
    }

    public String getB2009() {
        return b2009;
    }

    public void setB2009(String b2009) {
        this.b2009 = b2009;
    }

    public B2009ObjBean getB2009Obj() {
        return b2009Obj;
    }

    public void setB2009Obj(B2009ObjBean b2009Obj) {
        this.b2009Obj = b2009Obj;
    }

    public String getB2010() {
        return b2010;
    }

    public void setB2010(String b2010) {
        this.b2010 = b2010;
    }

    public B2010ObjBean getB2010Obj() {
        return b2010Obj;
    }

    public void setB2010Obj(B2010ObjBean b2010Obj) {
        this.b2010Obj = b2010Obj;
    }

    public String getB2011() {
        return b2011;
    }

    public void setB2011(String b2011) {
        this.b2011 = b2011;
    }

    public String getB2012() {
        return b2012;
    }

    public void setB2012(String b2012) {
        this.b2012 = b2012;
    }

    public String getB2013() {
        return b2013;
    }

    public void setB2013(String b2013) {
        this.b2013 = b2013;
    }

    public String getB2014() {
        return b2014;
    }

    public void setB2014(String b2014) {
        this.b2014 = b2014;
    }

    public String getB2015() {
        return b2015;
    }

    public void setB2015(String b2015) {
        this.b2015 = b2015;
    }

    public String getB2016() {
        return b2016;
    }

    public void setB2016(String b2016) {
        this.b2016 = b2016;
    }

    public B2016ObjBean getB2016Obj() {
        return b2016Obj;
    }

    public void setB2016Obj(B2016ObjBean b2016Obj) {
        this.b2016Obj = b2016Obj;
    }

    public String getB2017() {
        return b2017;
    }

    public void setB2017(String b2017) {
        this.b2017 = b2017;
    }

    public String getB2018() {
        return b2018;
    }

    public void setB2018(String b2018) {
        this.b2018 = b2018;
    }

    public B2018ObjBean getB2018Obj() {
        return b2018Obj;
    }

    public void setB2018Obj(B2018ObjBean b2018Obj) {
        this.b2018Obj = b2018Obj;
    }

    public String getB2019() {
        return b2019;
    }

    public void setB2019(String b2019) {
        this.b2019 = b2019;
    }

    public String getB2020() {
        return b2020;
    }

    public void setB2020(String b2020) {
        this.b2020 = b2020;
    }

    public String getB2021() {
        return b2021;
    }

    public void setB2021(String b2021) {
        this.b2021 = b2021;
    }

    public String getB2022() {
        return b2022;
    }

    public void setB2022(String b2022) {
        this.b2022 = b2022;
    }

    public String getB2023() {
        return b2023;
    }

    public void setB2023(String b2023) {
        this.b2023 = b2023;
    }

    public String getB2024() {
        return b2024;
    }

    public void setB2024(String b2024) {
        this.b2024 = b2024;
    }

    public String getB2025() {
        return b2025;
    }

    public void setB2025(String b2025) {
        this.b2025 = b2025;
    }

    public String getB2026() {
        return b2026;
    }

    public void setB2026(String b2026) {
        this.b2026 = b2026;
    }

    public String getB2027() {
        return b2027;
    }

    public void setB2027(String b2027) {
        this.b2027 = b2027;
    }

    public String getB2028() {
        return b2028;
    }

    public void setB2028(String b2028) {
        this.b2028 = b2028;
    }

    public String getB2029() {
        return b2029;
    }

    public void setB2029(String b2029) {
        this.b2029 = b2029;
    }

    public String getB2030() {
        return b2030;
    }

    public void setB2030(String b2030) {
        this.b2030 = b2030;
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

    public static class B2009ObjBean implements Serializable{
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

    public static class B2010ObjBean implements Serializable{
        /**
         * code : 3
         * codeName : 专科教育
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

    public static class B2016ObjBean implements Serializable{
        /**
         * code : 310230
         * codeName : 上海市崇明县
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

    public static class B2018ObjBean implements Serializable{
        /**
         * code : 310230
         * codeName : 上海市崇明县
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
         * orgFullName : 桃花花网格
         * orgId : 2c9180907ce91391017cea3ac86a0006
         * orgName : 桃花花网格
         * orgNo : 500111123003001
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
