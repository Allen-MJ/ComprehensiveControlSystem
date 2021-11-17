package cn.lyj.thepublic.entry;

import java.io.Serializable;

public class WjdcEntity implements Serializable {

    /**
     * pollEndtime : 2021-11-30 15:00:00
     * pollId : 4a304892ce3441bda5240d24ca1b6c9c
     * pollStatu : 1
     * pollTitle : 测试1
     * textInfo : {"textContent":"您好!环境与我们息息相关，那么你对环境保护有什么样的看法或态度呢?诚邀您用一点点时间，填写这份调查问卷。该问卷为匿名制，所有答案数据仅作分析使用，请您放心填写。谢谢您的支持!","textId":"4a304892ce3441bda5240d24ca1b6c9c"}
     */

    private String pollEndtime;
    private String pollId;
    private String pollStatu;
    private String pollTitle;
    private TextInfoBean textInfo;

    public String getPollEndtime() {
        return pollEndtime;
    }

    public void setPollEndtime(String pollEndtime) {
        this.pollEndtime = pollEndtime;
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public String getPollStatu() {
        return pollStatu;
    }

    public void setPollStatu(String pollStatu) {
        this.pollStatu = pollStatu;
    }

    public String getPollTitle() {
        return pollTitle;
    }

    public void setPollTitle(String pollTitle) {
        this.pollTitle = pollTitle;
    }

    public TextInfoBean getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(TextInfoBean textInfo) {
        this.textInfo = textInfo;
    }

    public static class TextInfoBean implements Serializable{
        /**
         * textContent : 您好!环境与我们息息相关，那么你对环境保护有什么样的看法或态度呢?诚邀您用一点点时间，填写这份调查问卷。该问卷为匿名制，所有答案数据仅作分析使用，请您放心填写。谢谢您的支持!
         * textId : 4a304892ce3441bda5240d24ca1b6c9c
         */

        private String textContent;
        private String textId;

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public String getTextId() {
            return textId;
        }

        public void setTextId(String textId) {
            this.textId = textId;
        }
    }
}
