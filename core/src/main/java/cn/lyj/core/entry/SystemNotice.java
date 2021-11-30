package cn.lyj.core.entry;

import java.io.Serializable;

public class SystemNotice implements Serializable {
    private String noticeSubtitle;
    private String noticeEndtime;
    private String createTime;
    private String isRead;
    private String noticeObj;
    private String noticeStarttime;
    private String noticeType;
    private String createBy;
    private String noticeId;
    private String textContent;
    private String noticeStatu;
    private String noticeTitle;

    public String getNoticeSubtitle() {
        return noticeSubtitle;
    }

    public void setNoticeSubtitle(String noticeSubtitle) {
        this.noticeSubtitle = noticeSubtitle;
    }

    public String getNoticeEndtime() {
        return noticeEndtime;
    }

    public void setNoticeEndtime(String noticeEndtime) {
        this.noticeEndtime = noticeEndtime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getNoticeObj() {
        return noticeObj;
    }

    public void setNoticeObj(String noticeObj) {
        this.noticeObj = noticeObj;
    }

    public String getNoticeStarttime() {
        return noticeStarttime;
    }

    public void setNoticeStarttime(String noticeStarttime) {
        this.noticeStarttime = noticeStarttime;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getNoticeStatu() {
        return noticeStatu;
    }

    public void setNoticeStatu(String noticeStatu) {
        this.noticeStatu = noticeStatu;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }
}
