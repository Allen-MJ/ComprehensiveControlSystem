package cn.lyj.thepublic.entry;

import java.io.Serializable;

public class Notice implements Serializable  {
        /**
         * createBy : admin
         * createTime : 2021-10-31 16:26:06
         * deleted : 0
         * noticeContent : <p>123213123123<img src="http://150.158.184.184:30050\file\图片\tp1-20211031060844209.jpeg" alt="" width="500" height="245" /></p>
         * noticeId : c39114e927e546bb89c94834be9adeff
         * noticeIssuer : E301D82C-6A40-4FAC-985E-39B4B5F55A1F
         * noticeSubtitle : 副标题1111111111111111111
         * noticeTitle : 测试通知1
         * noticeType : 大足要闻
         * noticeUp : true
         * updateBy : admin
         * updateTime : 2021-10-31 18:08:52
         */

        private String createBy;
        private String createTime;
        private int deleted;
        private String noticeContent;
        private String noticeId;
        private String noticeIssuer;
        private String noticeSubtitle;
        private String noticeTitle;
        private String noticeType;
        private boolean noticeUp;
        private String updateBy;
        private String updateTime;

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

        public String getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(String noticeContent) {
            this.noticeContent = noticeContent;
        }

        public String getNoticeId() {
            return noticeId;
        }

        public void setNoticeId(String noticeId) {
            this.noticeId = noticeId;
        }

        public String getNoticeIssuer() {
            return noticeIssuer;
        }

        public void setNoticeIssuer(String noticeIssuer) {
            this.noticeIssuer = noticeIssuer;
        }

        public String getNoticeSubtitle() {
            return noticeSubtitle;
        }

        public void setNoticeSubtitle(String noticeSubtitle) {
            this.noticeSubtitle = noticeSubtitle;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public String getNoticeType() {
            return noticeType;
        }

        public void setNoticeType(String noticeType) {
            this.noticeType = noticeType;
        }

        public boolean isNoticeUp() {
            return noticeUp;
        }

        public void setNoticeUp(boolean noticeUp) {
            this.noticeUp = noticeUp;
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
