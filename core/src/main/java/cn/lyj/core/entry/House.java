package cn.lyj.core.entry;

import java.io.Serializable;

public class House implements Serializable {

    /**
     * address : 测试地址
     * bid : 3dc3f7deae2c4b5a9b526e0d8fe3f67c
     * bidObj : {"cid":"22fea79414dc4168adca9cc833dc38a2","cidObj":{"address":"重庆市重庆市大足区","centerPointX":"105.74447316444201","centerPointY":"29.674841082862294","cid":"22fea79414dc4168adca9cc833dc38a2","createBy":"admin","createTime":"2021-10-20 09:44:40","deleted":0,"gid":"402880997c9b45a0017c9b6e6a8b0000","gridInfo":{"gid":"402880997c9b45a0017c9b6e6a8b0000","gridName":"公共场所","orgNo":"500111103001004"},"name":"萌鸡小队","type":"1","updateBy":"admin","updateTime":"2021-10-21 14:24:52"},"createBy":"admin","createTime":"2021-10-21 10:58:54","danyuan":1,"deleted":0,"gid":"402880997c9b45a0017c9b6e6a8b0000","gidObj":{"orgFullName":"石香社区居委会公共场所","orgId":"402880997c9b45a0017c9b6e6a8b0000","orgName":"公共场所","orgNo":"500111103001004"},"hushu":1,"id":"3dc3f7deae2c4b5a9b526e0d8fe3f67c","louceng":3,"name":"第二栋","remark":"测试","sort":2,"updateBy":"admin","updateTime":"2021-10-21 10:59:05"}
     * cid : 22fea79414dc4168adca9cc833dc38a2
     * cidObj : {"address":"重庆市重庆市大足区","centerPointX":"105.74447316444201","centerPointY":"29.674841082862294","cid":"22fea79414dc4168adca9cc833dc38a2","createBy":"admin","createTime":"2021-10-20 09:44:40","deleted":0,"gid":"402880997c9b45a0017c9b6e6a8b0000","gridInfo":{"gid":"402880997c9b45a0017c9b6e6a8b0000","gridName":"公共场所","orgNo":"500111103001004"},"name":"萌鸡小队","type":"1","updateBy":"admin","updateTime":"2021-10-21 14:24:52"}
     * createBy : admin
     * createTime : 2021-10-15 15:14:44
     * danyuan : 2
     * deleted : 0
     * gid : 402880997c9b45a0017c9b6e6a8b0000
     * gidObj : {"orgFullName":"石香社区居委会公共场所","orgId":"402880997c9b45a0017c9b6e6a8b0000","orgName":"公共场所","orgNo":"500111103001004"}
     * hao : 1
     * hid : 0
     * htype : 01
     * huhao : 2465645
     * landline : 48481125
     * louceng : 2
     * remark : 测试
     * state : 1
     * ucode : 测试测试单位
     * updateBy : admin
     * updateTime : 2021-10-28 11:45:34
     */

    private String address;
    private String bid;
    private BidObjBean bidObj;
    private String cid;
    private CidObjBeanX cidObj;
    private String createBy;
    private String createTime;
    private int danyuan;
    private int deleted;
    private String gid;
    private GidObjBeanX gidObj;
    private int hao;
    private String hid;
    private String htype;
    private String huhao;
    private String landline;
    private int louceng;
    private String remark;
    private String state;
    private String ucode;
    private String updateBy;
    private String updateTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public BidObjBean getBidObj() {
        return bidObj;
    }

    public void setBidObj(BidObjBean bidObj) {
        this.bidObj = bidObj;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public CidObjBeanX getCidObj() {
        return cidObj;
    }

    public void setCidObj(CidObjBeanX cidObj) {
        this.cidObj = cidObj;
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

    public int getDanyuan() {
        return danyuan;
    }

    public void setDanyuan(int danyuan) {
        this.danyuan = danyuan;
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

    public GidObjBeanX getGidObj() {
        return gidObj;
    }

    public void setGidObj(GidObjBeanX gidObj) {
        this.gidObj = gidObj;
    }

    public int getHao() {
        return hao;
    }

    public void setHao(int hao) {
        this.hao = hao;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getHtype() {
        return htype;
    }

    public void setHtype(String htype) {
        this.htype = htype;
    }

    public String getHuhao() {
        return huhao;
    }

    public void setHuhao(String huhao) {
        this.huhao = huhao;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public int getLouceng() {
        return louceng;
    }

    public void setLouceng(int louceng) {
        this.louceng = louceng;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
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

    public static class BidObjBean implements Serializable{
        /**
         * cid : 22fea79414dc4168adca9cc833dc38a2
         * cidObj : {"address":"重庆市重庆市大足区","centerPointX":"105.74447316444201","centerPointY":"29.674841082862294","cid":"22fea79414dc4168adca9cc833dc38a2","createBy":"admin","createTime":"2021-10-20 09:44:40","deleted":0,"gid":"402880997c9b45a0017c9b6e6a8b0000","gridInfo":{"gid":"402880997c9b45a0017c9b6e6a8b0000","gridName":"公共场所","orgNo":"500111103001004"},"name":"萌鸡小队","type":"1","updateBy":"admin","updateTime":"2021-10-21 14:24:52"}
         * createBy : admin
         * createTime : 2021-10-21 10:58:54
         * danyuan : 1
         * deleted : 0
         * gid : 402880997c9b45a0017c9b6e6a8b0000
         * gidObj : {"orgFullName":"石香社区居委会公共场所","orgId":"402880997c9b45a0017c9b6e6a8b0000","orgName":"公共场所","orgNo":"500111103001004"}
         * hushu : 1
         * id : 3dc3f7deae2c4b5a9b526e0d8fe3f67c
         * louceng : 3
         * name : 第二栋
         * remark : 测试
         * sort : 2
         * updateBy : admin
         * updateTime : 2021-10-21 10:59:05
         */

        private String cid;
        private CidObjBean cidObj;
        private String createBy;
        private String createTime;
        private int danyuan;
        private int deleted;
        private String gid;
        private GidObjBean gidObj;
        private int hushu;
        private String id;
        private int louceng;
        private String name;
        private String remark;
        private int sort;
        private String updateBy;
        private String updateTime;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public CidObjBean getCidObj() {
            return cidObj;
        }

        public void setCidObj(CidObjBean cidObj) {
            this.cidObj = cidObj;
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

        public int getDanyuan() {
            return danyuan;
        }

        public void setDanyuan(int danyuan) {
            this.danyuan = danyuan;
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

        public int getHushu() {
            return hushu;
        }

        public void setHushu(int hushu) {
            this.hushu = hushu;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLouceng() {
            return louceng;
        }

        public void setLouceng(int louceng) {
            this.louceng = louceng;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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

        public static class CidObjBean implements Serializable{
            /**
             * address : 重庆市重庆市大足区
             * centerPointX : 105.74447316444201
             * centerPointY : 29.674841082862294
             * cid : 22fea79414dc4168adca9cc833dc38a2
             * createBy : admin
             * createTime : 2021-10-20 09:44:40
             * deleted : 0
             * gid : 402880997c9b45a0017c9b6e6a8b0000
             * gridInfo : {"gid":"402880997c9b45a0017c9b6e6a8b0000","gridName":"公共场所","orgNo":"500111103001004"}
             * name : 萌鸡小队
             * type : 1
             * updateBy : admin
             * updateTime : 2021-10-21 14:24:52
             */

            private String address;
            private String centerPointX;
            private String centerPointY;
            private String cid;
            private String createBy;
            private String createTime;
            private int deleted;
            private String gid;
            private GridInfoBean gridInfo;
            private String name;
            private String type;
            private String updateBy;
            private String updateTime;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCenterPointX() {
                return centerPointX;
            }

            public void setCenterPointX(String centerPointX) {
                this.centerPointX = centerPointX;
            }

            public String getCenterPointY() {
                return centerPointY;
            }

            public void setCenterPointY(String centerPointY) {
                this.centerPointY = centerPointY;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
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

            public GridInfoBean getGridInfo() {
                return gridInfo;
            }

            public void setGridInfo(GridInfoBean gridInfo) {
                this.gridInfo = gridInfo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public static class GridInfoBean implements Serializable{
                /**
                 * gid : 402880997c9b45a0017c9b6e6a8b0000
                 * gridName : 公共场所
                 * orgNo : 500111103001004
                 */

                private String gid;
                private String gridName;
                private String orgNo;

                public String getGid() {
                    return gid;
                }

                public void setGid(String gid) {
                    this.gid = gid;
                }

                public String getGridName() {
                    return gridName;
                }

                public void setGridName(String gridName) {
                    this.gridName = gridName;
                }

                public String getOrgNo() {
                    return orgNo;
                }

                public void setOrgNo(String orgNo) {
                    this.orgNo = orgNo;
                }
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

    public static class CidObjBeanX implements Serializable{
        /**
         * address : 重庆市重庆市大足区
         * centerPointX : 105.74447316444201
         * centerPointY : 29.674841082862294
         * cid : 22fea79414dc4168adca9cc833dc38a2
         * createBy : admin
         * createTime : 2021-10-20 09:44:40
         * deleted : 0
         * gid : 402880997c9b45a0017c9b6e6a8b0000
         * gridInfo : {"gid":"402880997c9b45a0017c9b6e6a8b0000","gridName":"公共场所","orgNo":"500111103001004"}
         * name : 萌鸡小队
         * type : 1
         * updateBy : admin
         * updateTime : 2021-10-21 14:24:52
         */

        private String address;
        private String centerPointX;
        private String centerPointY;
        private String cid;
        private String createBy;
        private String createTime;
        private int deleted;
        private String gid;
        private GridInfoBeanX gridInfo;
        private String name;
        private String type;
        private String updateBy;
        private String updateTime;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCenterPointX() {
            return centerPointX;
        }

        public void setCenterPointX(String centerPointX) {
            this.centerPointX = centerPointX;
        }

        public String getCenterPointY() {
            return centerPointY;
        }

        public void setCenterPointY(String centerPointY) {
            this.centerPointY = centerPointY;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
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

        public GridInfoBeanX getGridInfo() {
            return gridInfo;
        }

        public void setGridInfo(GridInfoBeanX gridInfo) {
            this.gridInfo = gridInfo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public static class GridInfoBeanX implements Serializable{
            /**
             * gid : 402880997c9b45a0017c9b6e6a8b0000
             * gridName : 公共场所
             * orgNo : 500111103001004
             */

            private String gid;
            private String gridName;
            private String orgNo;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getGridName() {
                return gridName;
            }

            public void setGridName(String gridName) {
                this.gridName = gridName;
            }

            public String getOrgNo() {
                return orgNo;
            }

            public void setOrgNo(String orgNo) {
                this.orgNo = orgNo;
            }
        }
    }

    public static class GidObjBeanX implements Serializable{
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
