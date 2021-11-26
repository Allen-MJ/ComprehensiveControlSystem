package cn.lyj.core.entry;

import java.io.Serializable;

public class GridMember implements Serializable {

    /**
     * gridName : 大足虹桥网格
     * orgName : 桃树社区
     * uid : 2090a119-7e2c-4bc3-b923-4f6dcc07d5e6
     * username : wg1
     */

    private String gridName;
    private String orgName;
    private String uid;
    private String username;

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
