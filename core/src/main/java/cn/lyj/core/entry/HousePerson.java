package cn.lyj.core.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class HousePerson implements Serializable {

    /**
     * b1200 : 0d86b6228d204c5b9811517703bc4967
     * b1201 : 500225199605021254
     * b1202 : 测试1
     * b1203 :
     * b1204 : 1
     * b1205 : 1996-05-02
     * b1206 : 01
     * b1207 : 重庆市大足区
     * b1208 : 10
     * b1209 : 05
     * b1209Obj : {"code":"05","codeName":"民盟"}
     * b1210 : 2
     * b1210Obj : {"code":"2","codeName":"本科教育"}
     * b1212 : 2
     * b1213 : 撒大声地
     * b1214 :
     * b1215 : 13696857458
     * b1216 : 500000
     * b1216Obj : {"code":"500000","codeName":"重庆市"}
     * b1217 : 阿斯顿发顺丰
     * b1218 : 500111
     * b1218Obj : {"code":"500111","codeName":"重庆市大足区"}
     * b1219 : 阿斯顿发
     * b1220 : 01
     * b1221 : 354252
     * b1222 :
     * b1223 :
     * b1224 : 00
     * b1224Obj : {"code":"00","codeName":"本人"}
     * b1225 :
     * createBy : admin
     * createTime : 2021-11-08 15:34:03
     * deleted : 0
     * gid : 828280c57c64053d017c6408fc5c0000
     * gidObj : {"orgFullName":"一网格","orgId":"828280c57c64053d017c6408fc5c0000","orgName":"一网格","orgNo":"500111103001001"}
     * updateBy : admin
     * updateTime : 2021-11-08 15:34:03
     */

    private String b1200;
    private String b1201;
    private String b1202;
    private String b1203;
    private String b1204;//性别
    private String b1204Name;//性别
    private String b1205;
    private String b1206;
    private String b1207;
    private String b1208;
    private String b1209;
    private B1209ObjBean b1209Obj;
    private String b1210;
    private B1210ObjBean b1210Obj;
    private String b1211;
    private String b1212;
    private String b1213;
    private String b1214;
    private String b1215;
    private String b1216;
    private B1216ObjBean b1216Obj;
    private String b1217;
    private String b1218;
    private B1218ObjBean b1218Obj;
    private String b1219;
    private String b1220;
    private String b1220Name;//人户一致
    private String b1221;
    private String b1222;
    private String b1223;
    private String b1224;
    private B1224ObjBean b1224Obj;
    private String b1225;
    private String createBy;
    private String createTime;
    private int deleted;
    private String gid;
    private GidObjBean gidObj;
    private String updateBy;
    private String updateTime;

    public String getB1200() {
        return b1200;
    }

    public void setB1200(String b1200) {
        this.b1200 = b1200;
    }

    public String getB1201() {
        return b1201;
    }

    public void setB1201(String b1201) {
        this.b1201 = b1201;
    }

    public String getB1202() {
        return b1202;
    }

    public void setB1202(String b1202) {
        this.b1202 = b1202;
    }

    public String getB1203() {
        return b1203;
    }

    public void setB1203(String b1203) {
        this.b1203 = b1203;
    }

    public String getB1204() {
        return b1204;
    }

    public String getB1204Name() {
        String sex = "";
        switch (b1204){
            case "2":
                sex = "女";
                break;
            case "1":
                sex = "男";
                break;
        }
        return StringUtils.empty(sex)?b1204Name:sex;
    }

    public void setB1204Name(String b1204Name) {
        this.b1204Name = b1204Name;
    }

    public void setB1204(String b1204) {
        this.b1204 = b1204;
    }

    public String getB1205() {
        return b1205;
    }

    public void setB1205(String b1205) {
        this.b1205 = b1205;
    }

    public String getB1206() {
        return b1206;
    }

    public void setB1206(String b1206) {
        this.b1206 = b1206;
    }

    public String getB1207() {
        return b1207;
    }

    public void setB1207(String b1207) {
        this.b1207 = b1207;
    }

    public String getB1208() {
        return b1208;
    }

    public void setB1208(String b1208) {
        this.b1208 = b1208;
    }

    public String getB1209() {
        return b1209;
    }

    public void setB1209(String b1209) {
        this.b1209 = b1209;
    }

    public B1209ObjBean getB1209Obj() {
        return b1209Obj;
    }

    public void setB1209Obj(B1209ObjBean b1209Obj) {
        this.b1209Obj = b1209Obj;
    }

    public String getB1210() {
        return b1210;
    }

    public void setB1210(String b1210) {
        this.b1210 = b1210;
    }

    public B1210ObjBean getB1210Obj() {
        return b1210Obj;
    }

    public void setB1210Obj(B1210ObjBean b1210Obj) {
        this.b1210Obj = b1210Obj;
    }

    public String getB1211() {
        return b1211;
    }

    public void setB1211(String b1211) {
        this.b1211 = b1211;
    }

    public String getB1212() {
        return b1212;
    }

    public void setB1212(String b1212) {
        this.b1212 = b1212;
    }

    public String getB1213() {
        return b1213;
    }

    public void setB1213(String b1213) {
        this.b1213 = b1213;
    }

    public String getB1214() {
        return b1214;
    }

    public void setB1214(String b1214) {
        this.b1214 = b1214;
    }

    public String getB1215() {
        return b1215;
    }

    public void setB1215(String b1215) {
        this.b1215 = b1215;
    }

    public String getB1216() {
        return b1216;
    }

    public void setB1216(String b1216) {
        this.b1216 = b1216;
    }

    public B1216ObjBean getB1216Obj() {
        return b1216Obj;
    }

    public void setB1216Obj(B1216ObjBean b1216Obj) {
        this.b1216Obj = b1216Obj;
    }

    public String getB1217() {
        return b1217;
    }

    public void setB1217(String b1217) {
        this.b1217 = b1217;
    }

    public String getB1218() {
        return b1218;
    }

    public void setB1218(String b1218) {
        this.b1218 = b1218;
    }

    public B1218ObjBean getB1218Obj() {
        return b1218Obj;
    }

    public void setB1218Obj(B1218ObjBean b1218Obj) {
        this.b1218Obj = b1218Obj;
    }

    public String getB1219() {
        return b1219;
    }

    public void setB1219(String b1219) {
        this.b1219 = b1219;
    }

    public String getB1220() {
        return b1220;
    }

    public void setB1220(String b1220) {
        this.b1220 = b1220;
    }

    public String getB1220Name() {
        String name = "";
        switch (b1220){
            case "01":
                name = "一致";
                break;
            case "02":
                name = "不一致";
                break;
        }
        return StringUtils.empty(name)?b1220Name:name;
    }

    public void setB1220Name(String b1220Name) {
        this.b1220Name = b1220Name;
    }

    public String getB1221() {
        return b1221;
    }

    public void setB1221(String b1221) {
        this.b1221 = b1221;
    }

    public String getB1222() {
        return b1222;
    }

    public void setB1222(String b1222) {
        this.b1222 = b1222;
    }

    public String getB1223() {
        return b1223;
    }

    public void setB1223(String b1223) {
        this.b1223 = b1223;
    }

    public String getB1224() {
        return b1224;
    }

    public void setB1224(String b1224) {
        this.b1224 = b1224;
    }

    public B1224ObjBean getB1224Obj() {
        return b1224Obj;
    }

    public void setB1224Obj(B1224ObjBean b1224Obj) {
        this.b1224Obj = b1224Obj;
    }

    public String getB1225() {
        return b1225;
    }

    public void setB1225(String b1225) {
        this.b1225 = b1225;
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

    public static class B1209ObjBean implements Serializable{
        /**
         * code : 05
         * codeName : 民盟
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

    public static class B1210ObjBean implements Serializable{
        /**
         * code : 2
         * codeName : 本科教育
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

    public static class B1216ObjBean implements Serializable{
        /**
         * code : 500000
         * codeName : 重庆市
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

    public static class B1218ObjBean implements Serializable{
        /**
         * code : 500111
         * codeName : 重庆市大足区
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

    public static class B1224ObjBean implements Serializable{
        /**
         * code : 00
         * codeName : 本人
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
         * orgFullName : 一网格
         * orgId : 828280c57c64053d017c6408fc5c0000
         * orgName : 一网格
         * orgNo : 500111103001001
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
