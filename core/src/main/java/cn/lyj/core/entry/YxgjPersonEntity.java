package cn.lyj.core.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class YxgjPersonEntity implements Serializable {


    /**
     * birthday : 1989-05-12
     * createBy : admin
     * createTime : 2021-10-12 14:15:16
     * deleted : 0
     * edu : 1
     * eduObj : {"code":"1","codeName":"研究生教育"}
     * fwcs :
     * gidObj : {}
     * hjd : 500120
     * hjdObj : {"code":"500120","codeName":"重庆市璧山县"}
     * hjddetail : 梵蒂冈地方感受到
     * id : 1919b6323945442e9018638e461ab121
     * link : 13525857485
     * marriage : 1
     * name : 阿萨德
     * nation : 01
     * nativeplace : 上大帝岁数大
     * pid : 500224198905124450
     * sex : 1
     * uname :
     * updateBy : admin
     * updateTime : 2021-10-12 14:20:30
     * work : 非官方的
     * worktype : 4
     * xzd : 500120
     * xzdObj : {"code":"500120","codeName":"重庆市璧山县"}
     * xzddetail : 梵蒂冈地方感受到
     * zzmm : 13
     * zzmmObj : {"code":"13","codeName":"群众"}
     */

    private String birthday;
    private String createBy;
    private String createTime;
    private int deleted;
    private String edu;
    private EduObjBean eduObj;
    private String fwcs;
    private GidObjBean gidObj;
    private String hjd;
    private HjdObjBean hjdObj;
    private String hjddetail;
    private String id;
    private String link;
    private String marriage;
    private String name;
    private String nation;
    private String nativeplace;
    private String pid;
    private String sex;
    private String sexName;
    private String uname;
    private String updateBy;
    private String updateTime;
    private String work;
    private String worktype;
    private String xzd;
    private XzdObjBean xzdObj;
    private String xzddetail;
    private String zzmm;
    private ZzmmObjBean zzmmObj;
    private String gid;
    private String zongj;
    private String picture_path;

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    public String getZongj() {
        return zongj;
    }

    public void setZongj(String zongj) {
        this.zongj = zongj;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public EduObjBean getEduObj() {
        return eduObj;
    }

    public void setEduObj(EduObjBean eduObj) {
        this.eduObj = eduObj;
    }

    public String getFwcs() {
        return fwcs;
    }

    public void setFwcs(String fwcs) {
        this.fwcs = fwcs;
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

    public String getHjd() {
        return hjd;
    }

    public void setHjd(String hjd) {
        this.hjd = hjd;
    }

    public HjdObjBean getHjdObj() {
        return hjdObj;
    }

    public void setHjdObj(HjdObjBean hjdObj) {
        this.hjdObj = hjdObj;
    }

    public String getHjddetail() {
        return hjddetail;
    }

    public void setHjddetail(String hjddetail) {
        this.hjddetail = hjddetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNativeplace() {
        return nativeplace;
    }

    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexName() {
        String name = "";
        switch (sex){
            case "1":
                name = "男";
                break;
            case "2":
                name = "女";
                break;
        }
        return StringUtils.empty(name)?sexName:name;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getXzd() {
        return xzd;
    }

    public void setXzd(String xzd) {
        this.xzd = xzd;
    }

    public XzdObjBean getXzdObj() {
        return xzdObj;
    }

    public void setXzdObj(XzdObjBean xzdObj) {
        this.xzdObj = xzdObj;
    }

    public String getXzddetail() {
        return xzddetail;
    }

    public void setXzddetail(String xzddetail) {
        this.xzddetail = xzddetail;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public ZzmmObjBean getZzmmObj() {
        return zzmmObj;
    }

    public void setZzmmObj(ZzmmObjBean zzmmObj) {
        this.zzmmObj = zzmmObj;
    }

    public static class EduObjBean implements Serializable{
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

    public static class HjdObjBean implements Serializable{
        /**
         * code : 500120
         * codeName : 重庆市璧山县
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

    public static class XzdObjBean implements Serializable{
        /**
         * code : 500120
         * codeName : 重庆市璧山县
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

    public static class ZzmmObjBean implements Serializable{
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

}
