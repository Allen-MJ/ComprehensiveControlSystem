package cn.lyj.core.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class XjPersonEntity  implements Serializable {


    /**
     * birthday : 1998-05-21
     * createBy : admin
     * createTime : 2021-10-12 14:31:37
     * deleted : 0
     * edu : 2
     * eduObj : {"code":"2","codeName":"本科教育"}
     * fwcs : 撒旦法师的
     * gid : 402880997ca33623017ca33df67f0001
     * gidObj : {"orgFullName":"测试社区委员会测试网格","orgId":"402880997ca33623017ca33df67f0001","orgName":"测试网格","orgNo":"500111106001001"}
     * hjd : 500113
     * hjdObj : {"code":"500113","codeName":"重庆市巴南区"}
     * hjddetail : 大傻傻哒
     * id : 2b77cf9831d44d3dabb1050c9ce8ba83
     * link : 13485748589
     * marriage : 2
     * name : 刷刷刷
     * nation : 01
     * nativeplace : 重庆
     * picturePath : /file\图片\test2-20211026114242676.jpg
     * pid : 500220199805210225
     * sex : 2
     * uname :
     * updateBy : admin
     * updateTime : 2021-10-29 11:52:30
     * work : 四大法师
     * worktype : 4
     * xzd : 500108
     * xzdObj : {"code":"500108","codeName":"重庆市南岸区"}
     * xzddetail : 按发放的
     * zongj : 00
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
    private String gid;
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
    private String picturePath;
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
    private String zongj;
    private String zzmm;
    private ZzmmObjBean zzmmObj;

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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
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

    public String getZongj() {
        return zongj;
    }

    public void setZongj(String zongj) {
        this.zongj = zongj;
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

    public static class EduObjBean implements Serializable {
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

    public static class GidObjBean implements Serializable {
        /**
         * orgFullName : 测试社区委员会测试网格
         * orgId : 402880997ca33623017ca33df67f0001
         * orgName : 测试网格
         * orgNo : 500111106001001
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

    public static class HjdObjBean implements Serializable {
        /**
         * code : 500113
         * codeName : 重庆市巴南区
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

    public static class XzdObjBean implements Serializable {
        /**
         * code : 500108
         * codeName : 重庆市南岸区
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

    public static class ZzmmObjBean implements Serializable {
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
