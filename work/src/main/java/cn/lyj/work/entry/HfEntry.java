package cn.lyj.work.entry;

import java.io.Serializable;

public class HfEntry implements Serializable {
    private String ReplyContent;
    private String MessageId;
    private String Name;
    private String MessageDate;
    private String MessageContent;

    public HfEntry() {
    }

    public String getReplyContent() {
        return ReplyContent;
    }

    public void setReplyContent(String replyContent) {
        ReplyContent = replyContent;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMessageDate() {
        return MessageDate;
    }

    public void setMessageDate(String messageDate) {
        MessageDate = messageDate;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    @Override
    public String toString() {
        return "HfEntry{" +
                "ReplyContent='" + ReplyContent + '\'' +
                ", MessageId='" + MessageId + '\'' +
                ", Name='" + Name + '\'' +
                ", MessageDate='" + MessageDate + '\'' +
                ", MessageContent='" + MessageContent + '\'' +
                '}';
    }
}
