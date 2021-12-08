package cn.lyj.core.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class Log implements Serializable {
    private String logId;
    private String inputPid;
    private String workItem;
    private String workItemLabel;
    private String progress;
    private String progressName;
    private String description;
    private String createTime;
    private String createBy;
    private String updateTime;
    private String updateBy;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getInputPid() {
        return inputPid;
    }

    public void setInputPid(String inputPid) {
        this.inputPid = inputPid;
    }

    public String getWorkItem() {
        return workItem;
    }

    public void setWorkItem(String workItem) {
        this.workItem = workItem;
    }

    public String getWorkItemLabel() {
        return workItemLabel;
    }

    public void setWorkItemLabel(String workItemLabel) {
        this.workItemLabel = workItemLabel;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getProgressName() {
        String mProgress = "";
        switch (progress){
            case "1":
                mProgress = "处理中";
                break;
            case "2":
                mProgress = "已完成";
                break;
        }
        return StringUtils.empty(mProgress)?progressName:mProgress;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }
}
