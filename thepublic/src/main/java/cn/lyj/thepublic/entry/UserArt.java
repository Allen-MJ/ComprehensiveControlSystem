package cn.lyj.thepublic.entry;

import java.io.Serializable;

/**
 * 用户点赞、评论、关注的文章
 */
public class UserArt implements Serializable {

    /**
     * 用户点赞
     * likeTime : 2021-10-13 15:49:12
     * publicUserId : E301D82C-6A40-4FAC-985E-39B4B5F55A1F
     * serviceId : 53aa71f33e7148e18d2a585dae4fc871
     * serviceInfo : {"createTime":"2021-10-11 17:31:43","serviceContent":"<p>将于2021-10-11在冉家坝地铁口举行<\/p><p><br><\/p><p><img src=\"http://localhost:30050\\file\\图片\\81bceb8b9a43743bb1873936bb49f3d4-20211011053113841.jpeg\" style=\"max-width:100%;\"><br><\/p><p><br><\/p><p>期待期待期待。。。<\/p>","serviceId":"53aa71f33e7148e18d2a585dae4fc871","serviceSubtitle":"345","serviceTitle":"第一届社会招聘会来了！","serviceType":"2","serviceUp":true}
     */

    private String likeTime;
    private String publicUserId;
    private String serviceId;
    private ServiceInfoBean serviceInfo;
    /**
     * 用户关注
     * befocusedServiceid : 53aa71f33e7148e18d2a585dae4fc871
     * befocusedTime : 2021-10-13 15:55:28
     */

    private String befocusedServiceid;
    private String befocusedTime;
    /**
     * 用户评论
     * commentContent : 测试评论
     * commentId : be9b8ba679f04ce8a9869852da60edd3
     * createBy : admin
     * createTime : 2021-10-13 16:20:56
     * deleted : 0
     * isOfficial : 0
     * superCommentId : 0
     * updateBy : admin
     * updateTime : 2021-10-13 16:20:56
     */

    private String commentContent;
    private String commentId;
    private String createBy;
    private String createTime;
    private int deleted;
    private String isOfficial;
    private String superCommentId;
    private String updateBy;
    private String updateTime;

    public String getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(String likeTime) {
        this.likeTime = likeTime;
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

    public ServiceInfoBean getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ServiceInfoBean serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public String getBefocusedServiceid() {
        return befocusedServiceid;
    }

    public void setBefocusedServiceid(String befocusedServiceid) {
        this.befocusedServiceid = befocusedServiceid;
    }

    public String getBefocusedTime() {
        return befocusedTime;
    }

    public void setBefocusedTime(String befocusedTime) {
        this.befocusedTime = befocusedTime;
    }

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

    public static class ServiceInfoBean implements Serializable{
        /**
         * createTime : 2021-10-11 17:31:43
         * serviceContent : <p>将于2021-10-11在冉家坝地铁口举行</p><p><br></p><p><img src="http://localhost:30050\file\图片\81bceb8b9a43743bb1873936bb49f3d4-20211011053113841.jpeg" style="max-width:100%;"><br></p><p><br></p><p>期待期待期待。。。</p>
         * serviceId : 53aa71f33e7148e18d2a585dae4fc871
         * serviceSubtitle : 345
         * serviceTitle : 第一届社会招聘会来了！
         * serviceType : 2
         * serviceUp : true
         */

        private String createTime;
        private String serviceContent;
        private String serviceId;
        private String serviceSubtitle;
        private String serviceTitle;
        private String serviceType;
        private boolean serviceUp;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getServiceContent() {
            return serviceContent;
        }

        public void setServiceContent(String serviceContent) {
            this.serviceContent = serviceContent;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceSubtitle() {
            return serviceSubtitle;
        }

        public void setServiceSubtitle(String serviceSubtitle) {
            this.serviceSubtitle = serviceSubtitle;
        }

        public String getServiceTitle() {
            return serviceTitle;
        }

        public void setServiceTitle(String serviceTitle) {
            this.serviceTitle = serviceTitle;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public boolean isServiceUp() {
            return serviceUp;
        }

        public void setServiceUp(boolean serviceUp) {
            this.serviceUp = serviceUp;
        }
    }
}
