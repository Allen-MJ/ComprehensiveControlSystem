package allen.frame.entry;

import java.io.Serializable;

public class DicType implements Serializable {


    /**
     * createTime : 2019-10-27 20:31:36
     * dict : {"id":1}
     * dictSort : 1
     * id : 1
     * label : 激活
     * value : true
     */

    private String createTime;
    private DictBean dict;
    private int dictSort;
    private int id;
    private String label;
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class DictBean {
        /**
         * id : 1
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
