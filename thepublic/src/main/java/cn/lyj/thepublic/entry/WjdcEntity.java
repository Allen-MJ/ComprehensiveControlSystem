package cn.lyj.thepublic.entry;

import java.io.Serializable;

public class WjdcEntity implements Serializable {

    /**
     * pollEndtime : 2021-10-16 15:00:00
     * pollId : 4a304892ce3441bda5240d24ca1b6c9c
     * pollTitle : 测试1
     */

    private String pollEndtime;
    private String pollId;
    private String pollTitle;

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

    public String getPollTitle() {
        return pollTitle;
    }

    public void setPollTitle(String pollTitle) {
        this.pollTitle = pollTitle;
    }
}
