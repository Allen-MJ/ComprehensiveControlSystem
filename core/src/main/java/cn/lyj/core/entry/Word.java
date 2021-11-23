package cn.lyj.core.entry;

import java.io.Serializable;
import java.util.List;

public class Word implements Serializable {

    /**
     * attachments : []
     * createBy : app
     * createTime : 2021-11-23 15:55:56
     * deleted : 0
     * digest : asdasdasd
     * emergencyDegree : 2
     * missiveId : 52c43e8fe8ad45cbba9613d851c5614e
     * missiveNo : asdasd
     * receiver : 测试员
     * signTime : 2021-11-23
     * signer : app
     * state : 未签收
     * title : asdasd
     * updateBy : app
     * updateTime : 2021-11-23 15:55:56
     */

    private String createBy;
    private String createTime;
    private int deleted;
    private String digest;
    private String emergencyDegree;
    private String missiveId;
    private String missiveNo;
    private String receiver;
    private String signTime;
    private String signer;
    private String state;
    private String title;
    private String updateBy;
    private String updateTime;
    private List<?> attachments;

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

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getEmergencyDegree() {
        return emergencyDegree;
    }

    public void setEmergencyDegree(String emergencyDegree) {
        this.emergencyDegree = emergencyDegree;
    }

    public String getMissiveId() {
        return missiveId;
    }

    public void setMissiveId(String missiveId) {
        this.missiveId = missiveId;
    }

    public String getMissiveNo() {
        return missiveNo;
    }

    public void setMissiveNo(String missiveNo) {
        this.missiveNo = missiveNo;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<?> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<?> attachments) {
        this.attachments = attachments;
    }
}
