package cn.lyj.thepublic.entry;

import java.io.Serializable;

public class SquareType implements Serializable {

    /**
     * createBy : admin
     * createTime : 2021-10-11 11:54:26
     * dict : {"id":95}
     * dictSort : 10
     * id : 599
     * label : 婚育信息
     * updateTime : 2021-10-11 11:54:26
     * value : 1
     */

    private String createBy;
    private String createTime;
    private DictBean dict;
    private int dictSort;
    private int id;
    private String label;
    private String updateTime;
    private String value;

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

    public DictBean getDict() {
        return dict;
    }

    public void setDict(DictBean dict) {
        this.dict = dict;
    }

    public int getDictSort() {
        return dictSort;
    }

    public void setDictSort(int dictSort) {
        this.dictSort = dictSort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class DictBean {
        /**
         * id : 95
         */

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
