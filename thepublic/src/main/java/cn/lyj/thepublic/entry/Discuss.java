package cn.lyj.thepublic.entry;

import java.io.Serializable;

public class Discuss implements Serializable {

    /**
     * commentContent : 测试评论
     * commentId : 8d2a0ba532f04b8aadc8f4649718206f
     * createBy : 13032369093
     * createTime : 2021-11-07 15:53:08
     * deleted : 0
     * isOfficial : 0
     * publicUserId : d9ff81fa-ff5a-43f0-92ce-4c3e62409557
     * serviceId : 53aa71f33e7148e18d2a585dae4fc871
     * superCommentId : 0
     * updateBy : 13032369093
     * updateTime : 2021-11-07 15:53:08
     */

    private String commentContent;
    private String commentId;
    private String createBy;
    private String createTime;
    private int deleted;
    private String isOfficial;
    private String publicUserId;
    private String serviceId;
    private String superCommentId;
    private String updateBy;
    private String updateTime;

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    public String getPublicUserId() {
        return publicUserId;
    }

    public void setPublicUserId(String publicUserId) {
        this.publicUserId = publicUserId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSuperCommentId() {
        return superCommentId;
    }

    public void setSuperCommentId(String superCommentId) {
        this.superCommentId = superCommentId;
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
}
