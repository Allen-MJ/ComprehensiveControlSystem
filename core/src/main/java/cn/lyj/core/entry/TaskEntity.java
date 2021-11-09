package cn.lyj.core.entry;

import java.io.Serializable;

public class TaskEntity implements Serializable {

    /**
     * createBy : wg1
     * createTime : 2021-11-09 16:06:15
     * deleted : 0
     * guid : 2090a119-7e2c-4bc3-b923-4f6dcc07d5e6
     * orgObj : {}
     * progress : 1
     * pubilshDate : 2021-11-01 00:00:00
     * specifyObjName : wg1
     * specifyType : 1
     * taskDesc : 请俊杰牵头做好今明两天颐泰苑业委会换届选举业主大会相关准备工作，并通知小区业主代表27日下午16:30社区大会议室召开工作会议，全体社区干部参会。
     * taskId : e511fb02ba074f4d956596366dc2b6d3
     * taskName : 颐泰苑小区业主委员会换届选举业主大会相关事宜
     * updateBy : wg1
     * updateTime : 2021-11-09 16:06:15
     * userObj : {"email":"123@123.com","enabled":true,"gender":"男","id":"2090a119-7e2c-4bc3-b923-4f6dcc07d5e6","isDelete":false,"phone":"13000001111","username":"wg1"}
     */

    private String createBy;
    private String createTime;
    private int deleted;
    private String guid;
    private OrgObjBean orgObj;
    private int progress;
    private String pubilshDate;
    private String specifyObjName;
    private int specifyType;
    private String taskDesc;
    private String taskId;
    private String taskName;
    private String updateBy;
    private String updateTime;
    private String receiver;
    private UserObjBean userObj;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public OrgObjBean getOrgObj() {
        return orgObj;
    }

    public void setOrgObj(OrgObjBean orgObj) {
        this.orgObj = orgObj;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getPubilshDate() {
        return pubilshDate;
    }

    public void setPubilshDate(String pubilshDate) {
        this.pubilshDate = pubilshDate;
    }

    public String getSpecifyObjName() {
        return specifyObjName;
    }

    public void setSpecifyObjName(String specifyObjName) {
        this.specifyObjName = specifyObjName;
    }

    public int getSpecifyType() {
        return specifyType;
    }

    public void setSpecifyType(int specifyType) {
        this.specifyType = specifyType;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public UserObjBean getUserObj() {
        return userObj;
    }

    public void setUserObj(UserObjBean userObj) {
        this.userObj = userObj;
    }

    public static class OrgObjBean implements Serializable{

    }

    public static class UserObjBean implements Serializable{
        /**
         * email : 123@123.com
         * enabled : true
         * gender : 男
         * id : 2090a119-7e2c-4bc3-b923-4f6dcc07d5e6
         * isDelete : false
         * phone : 13000001111
         * username : wg1
         */

        private String email;
        private boolean enabled;
        private String gender;
        private String id;
        private boolean isDelete;
        private String phone;
        private String username;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsDelete() {
            return isDelete;
        }

        public void setIsDelete(boolean isDelete) {
            this.isDelete = isDelete;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
