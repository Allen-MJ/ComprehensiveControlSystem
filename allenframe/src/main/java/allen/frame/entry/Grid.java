package allen.frame.entry;

import java.io.Serializable;

public class Grid implements Serializable {
    /**
     * centerPointX : 105.72814189487353
     * centerPointY : 29.710666914352583
     * createBy : admin
     * createTime : 2021-11-23 16:15:38
     * deleted : 0
     * gid : ff8080817d4bd67e017d4bdd5cb00000
     * gridName : 大足虹桥网格
     * mapColor : #230404
     * mapData : 105.72724299654463:29.712610246543942,105.72728339646955:29.70956828219747,105.72853579414122:29.709268489366778,105.72875799372815:29.70928612426421,105.72888929348404:29.712601429390528
     * orgNo : 500111123002002
     * orgObj : {"orgFullName":"大足虹桥网格大足虹桥网格","orgId":"ff8080817d4bd67e017d4bdd5cb00000","orgName":"大足虹桥网格","orgNo":"500111123002002"}
     * pid : 2c9180907ce91391017cea0c4d570003
     * updateBy : admin
     * updateTime : 2021-11-23 16:15:42
     * zoom : 17
     */

    private String centerPointX;
    private String centerPointY;
    private String createBy;
    private String createTime;
    private int deleted;
    private String gid;
    private String gridName;
    private String mapColor;
    private String mapData;
    private String orgNo;
    private OrgObjBean orgObj;
    private String pid;
    private String updateBy;
    private String updateTime;
    private String zoom;
    private String orgName;

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

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getMapColor() {
        return mapColor;
    }

    public void setMapColor(String mapColor) {
        this.mapColor = mapColor;
    }

    public String getMapData() {
        return mapData;
    }

    public void setMapData(String mapData) {
        this.mapData = mapData;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public OrgObjBean getOrgObj() {
        return orgObj;
    }

    public void setOrgObj(OrgObjBean orgObj) {
        this.orgObj = orgObj;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public static class OrgObjBean implements Serializable {
        /**
         * orgFullName : 大足虹桥网格大足虹桥网格
         * orgId : ff8080817d4bd67e017d4bdd5cb00000
         * orgName : 大足虹桥网格
         * orgNo : 500111123002002
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
