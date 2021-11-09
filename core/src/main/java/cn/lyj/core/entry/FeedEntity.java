package cn.lyj.core.entry;

import java.io.Serializable;

public class FeedEntity implements Serializable {
    /*"createTime":   "2021-11-04 09:41:34",
             "feedback":   "描述工作任务进度反馈详情、",
            "feedbackPeople":   "admin",
            "id":  6,
            "taskId":   "d6a3d42ce4dd4779a123490303548ebf"*/
    private String createTime;
    private String feedback;
    private String feedbackPeople;
    private int id;
    private String taskId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedbackPeople() {
        return feedbackPeople;
    }

    public void setFeedbackPeople(String feedbackPeople) {
        this.feedbackPeople = feedbackPeople;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
