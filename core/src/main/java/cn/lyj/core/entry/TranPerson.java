package cn.lyj.core.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class TranPerson implements Serializable {

    /**
     * b1300 : 01743aa79e464075bd681d6d74620a84
     * b1301 : 659812199902250023
     * b1302 : 吴伟
     * b1303 : 吴桐
     * b1304 : 2
     * b1305 : 1999-02-25
     * b1306 : 19
     * b1307 : 四川
     * b1308 : 10
     * b1309 : 03
     * b1309Obj : {"code":"03","codeName":"共青团员"}
     * b1310 : 81
     * b1310Obj : {"code":"81","codeName":"小学"}
     * b1311 : 00
     * b1312 : 8
     * b1313 : 特殊
     * b1314 : 场所
     * b1315 : 15289547896
     * b1316 : 469002
     * b1316Obj : {"code":"469002","codeName":"海南省琼海市"}
     * b1317 : 北方大街45号
     * b1318 : 340121
     * b1318Obj : {"code":"340121","codeName":"安徽省合肥市长丰县"}
     * b1319 : 北方大街45号
     * b1320 : 04
     * b1321 : 02
     * b1322 : 565656366
     * b1323 : 2019-11-13
     * b1324 : 2024-11-21
     * b1325 : 06
     * b1326 : 0
     * b1327 : 1
     * createBy : admin
     * createTime : 2021-11-05 14:39:17
     * deleted : 0
     * gid : 2c9180907ce91391017cea32cbc30005
     * gidObj : {"orgFullName":"1104桃树网格","orgId":"2c9180907ce91391017cea32cbc30005","orgName":"1104桃树网格","orgNo":"500111123002001"}
     * picturePath : /file\图片\3333-20211105023855619.jpg
     * updateBy : admin
     * updateTime : 2021-11-05 14:39:17
     */

    private String b1300;
    private String b1301;
    private String b1302;
    private String b1303;
    private String b1304;
    private String b1304Name;//性别
    private String b1305;
    private String b1306;
    private String b1307;
    private String b1308;
    private String b1309;
    private B1309ObjBean b1309Obj;
    private String b1310;
    private B1310ObjBean b1310Obj;
    private String b1311;
    private String b1312;
    private String b1313;
    private String b1314;
    private String b1315;
    private String b1316;
    private B1316ObjBean b1316Obj;
    private String b1317;
    private String b1318;
    private B1318ObjBean b1318Obj;
    private String b1319;
    private String b1320;
    private String b1321;
    private String b1322;
    private String b1323;
    private String b1324;
    private String b1325;
    private String b1326;
    private String b1326Name;
    private String b1327;
    private String b1327Name;
    private String createBy;
    private String createTime;
    private int deleted;
    private String gid;
    private GidObjBean gidObj;
    private String picturePath;
    private String updateBy;
    private String updateTime;

    public String getB1300() {
        return b1300;
    }

    public void setB1300(String b1300) {
        this.b1300 = b1300;
    }

    public String getB1301() {
        return b1301;
    }

    public void setB1301(String b1301) {
        this.b1301 = b1301;
    }

    public String getB1302() {
        return b1302;
    }

    public void setB1302(String b1302) {
        this.b1302 = b1302;
    }

    public String getB1303() {
        return b1303;
    }

    public void setB1303(String b1303) {
        this.b1303 = b1303;
    }

    public String getB1304() {
        return b1304;
    }

    public void setB1304(String b1304) {
        this.b1304 = b1304;
    }

    public String getB1304Name() {
        String sex = "";
        switch (b1304){
            case "1":
                sex = "男";
                break;
            case "2":
                sex = "女";
                break;
        }
        return StringUtils.empty(sex)?b1304Name:sex;
    }

    public void setB1304Name(String b1304Name) {
        this.b1304Name = b1304Name;
    }

    public String getB1305() {
        return b1305;
    }

    public void setB1305(String b1305) {
        this.b1305 = b1305;
    }

    public String getB1306() {
        return b1306;
    }

    public void setB1306(String b1306) {
        this.b1306 = b1306;
    }

    public String getB1307() {
        return b1307;
    }

    public void setB1307(String b1307) {
        this.b1307 = b1307;
    }

    public String getB1308() {
        return b1308;
    }

    public void setB1308(String b1308) {
        this.b1308 = b1308;
    }

    public String getB1309() {
        return b1309;
    }

    public void setB1309(String b1309) {
        this.b1309 = b1309;
    }

    public B1309ObjBean getB1309Obj() {
        return b1309Obj;
    }

    public void setB1309Obj(B1309ObjBean b1309Obj) {
        this.b1309Obj = b1309Obj;
    }

    public String getB1310() {
        return b1310;
    }

    public void setB1310(String b1310) {
        this.b1310 = b1310;
    }

    public B1310ObjBean getB1310Obj() {
        return b1310Obj;
    }

    public void setB1310Obj(B1310ObjBean b1310Obj) {
        this.b1310Obj = b1310Obj;
    }

    public String getB1311() {
        return b1311;
    }

    public void setB1311(String b1311) {
        this.b1311 = b1311;
    }

    public String getB1312() {
        return b1312;
    }

    public void setB1312(String b1312) {
        this.b1312 = b1312;
    }

    public String getB1313() {
        return b1313;
    }

    public void setB1313(String b1313) {
        this.b1313 = b1313;
    }

    public String getB1314() {
        return b1314;
    }

    public void setB1314(String b1314) {
        this.b1314 = b1314;
    }

    public String getB1315() {
        return b1315;
    }

    public void setB1315(String b1315) {
        this.b1315 = b1315;
    }

    public String getB1316() {
        return b1316;
    }

    public void setB1316(String b1316) {
        this.b1316 = b1316;
    }

    public B1316ObjBean getB1316Obj() {
        return b1316Obj;
    }

    public void setB1316Obj(B1316ObjBean b1316Obj) {
        this.b1316Obj = b1316Obj;
    }

    public String getB1317() {
        return b1317;
    }

    public void setB1317(String b1317) {
        this.b1317 = b1317;
    }

    public String getB1318() {
        return b1318;
    }

    public void setB1318(String b1318) {
        this.b1318 = b1318;
    }

    public B1318ObjBean getB1318Obj() {
        return b1318Obj;
    }

    public void setB1318Obj(B1318ObjBean b1318Obj) {
        this.b1318Obj = b1318Obj;
    }

    public String getB1319() {
        return b1319;
    }

    public void setB1319(String b1319) {
        this.b1319 = b1319;
    }

    public String getB1320() {
        return b1320;
    }

    public void setB1320(String b1320) {
        this.b1320 = b1320;
    }

    public String getB1321() {
        return b1321;
    }

    public void setB1321(String b1321) {
        this.b1321 = b1321;
    }

    public String getB1322() {
        return b1322;
    }

    public void setB1322(String b1322) {
        this.b1322 = b1322;
    }

    public String getB1323() {
        return b1323;
    }

    public void setB1323(String b1323) {
        this.b1323 = b1323;
    }

    public String getB1324() {
        return b1324;
    }

    public void setB1324(String b1324) {
        this.b1324 = b1324;
    }

    public String getB1325() {
        return b1325;
    }

    public void setB1325(String b1325) {
        this.b1325 = b1325;
    }

    public String getB1326() {
        return b1326;
    }

    public void setB1326(String b1326) {
        this.b1326 = b1326;
    }

    public String getB1326Name() {
        String name = "";
        switch (b1326){
            case "0":
                name = "否";
                break;
            case "1":
                name = "是";
                break;
        }
        return StringUtils.empty(name)?b1326Name:name;
    }

    public void setB1326Name(String b1326Name) {
        this.b1326Name = b1326Name;
    }

    public String getB1327Name() {
        String name = "";
        switch (b1327){
            case "0":
                name = "否";
                break;
            case "1":
                name = "是";
                break;
        }
        return StringUtils.empty(name)?b1327Name:name;
    }

    public void setB1327Name(String b1327Name) {
        this.b1327Name = b1327Name;
    }

    public String getB1327() {
        return b1327;
    }

    public void setB1327(String b1327) {
        this.b1327 = b1327;
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

    public static class B1309ObjBean implements Serializable{
        /**
         * code : 03
         * codeName : 共青团员
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

    public static class B1310ObjBean implements Serializable{
        /**
         * code : 81
         * codeName : 小学
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

    public static class B1316ObjBean implements Serializable{
        /**
         * code : 469002
         * codeName : 海南省琼海市
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

    public static class B1318ObjBean implements Serializable{
        /**
         * code : 340121
         * codeName : 安徽省合肥市长丰县
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
