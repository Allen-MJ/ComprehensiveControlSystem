package cn.lyj.core.entry;

import java.io.Serializable;
import java.util.List;

import allen.frame.entry.Units;
import allen.frame.entry.User;

public class KaoheEntry implements Serializable {
    private boolean aem;
    private String createBy;
    private String createTime;
    private String examineDate;
    private String examineId;
    private List<User> examineMember;//网格员
    private List<Relation> examineRelations;//考核关系
    private String examineTitle;
    private String examineType;
    private String orgId;
    private Units orgInfo;
    private String ruleId;
    private Rule rules;
    private String updateBy;
    private String updateTime;

    public boolean isAem() {
        return aem;
    }

    public void setAem(boolean aem) {
        this.aem = aem;
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

    public String getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(String examineDate) {
        this.examineDate = examineDate;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public List<User> getExamineMember() {
        return examineMember;
    }

    public void setExamineMember(List<User> examineMember) {
        this.examineMember = examineMember;
    }

    public List<Relation> getExamineRelations() {
        return examineRelations;
    }

    public void setExamineRelations(List<Relation> examineRelations) {
        this.examineRelations = examineRelations;
    }

    public String getExamineTitle() {
        return examineTitle;
    }

    public void setExamineTitle(String examineTitle) {
        this.examineTitle = examineTitle;
    }

    public String getExamineType() {
        return examineType;
    }

    public void setExamineType(String examineType) {
        this.examineType = examineType;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Units getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(Units orgInfo) {
        this.orgInfo = orgInfo;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Rule getRules() {
        return rules;
    }

    public void setRules(Rule rules) {
        this.rules = rules;
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

    public static class Relation implements Serializable{
        private String examineId;
        private boolean finish;
        private String finishTime;
        private String gridName;
        private String uid;

        public String getExamineId() {
            return examineId;
        }

        public void setExamineId(String examineId) {
            this.examineId = examineId;
        }

        public boolean isFinish() {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getGridName() {
            return gridName;
        }

        public void setGridName(String gridName) {
            this.gridName = gridName;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public static class Rule implements Serializable{
        private String createBy;
        private String createTime;
        private String examineContent;
        private int examineSource;
        private String orgId;
        private Units orgInfo;
        private String ruleId;
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

        public String getExamineContent() {
            return examineContent;
        }

        public void setExamineContent(String examineContent) {
            this.examineContent = examineContent;
        }

        public int getExamineSource() {
            return examineSource;
        }

        public void setExamineSource(int examineSource) {
            this.examineSource = examineSource;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public Units getOrgInfo() {
            return orgInfo;
        }

        public void setOrgInfo(Units orgInfo) {
            this.orgInfo = orgInfo;
        }

        public String getRuleId() {
            return ruleId;
        }

        public void setRuleId(String ruleId) {
            this.ruleId = ruleId;
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
}
