package cn.lyj.thepublic.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VoteEntity implements Serializable {

    /**
     * itemList : [{"itemId":"d98b3922eced401685b5d3234a7c849c","itemName":"1.最近常玩的游戏？","itemNotnull":"0","itemSortid":1,"itemType":"1","pollId":"4a304892ce3441bda5240d24ca1b6c9c"},{"itemId":"a223ed05c8f94f56b7e913feb9537d3b","itemName":"2.你每月在游戏的花销","itemNotnull":"0","itemSortid":2,"itemType":"2","itemValue":"0-500元;501-2000元;2001-5000元;5001-10000元;10000以上","pollId":"4a304892ce3441bda5240d24ca1b6c9c","valueList":["0-500元","501-2000元","2001-5000元","5001-10000元","10000以上"]}]
     * pollEndtime : 2021-10-16 15:00:00
     * pollId : 4a304892ce3441bda5240d24ca1b6c9c
     * pollStatu : 1
     * pollTitle : 测试1
     */

    private String pollEndtime;
    private String pollId;
    private String pollStatu;
    private String pollTitle;
    private List<ItemListBean> itemList;

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

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public static class ItemListBean {
        /**
         * itemId : d98b3922eced401685b5d3234a7c849c
         * itemName : 1.最近常玩的游戏？
         * itemNotnull : 0
         * itemSortid : 1
         * itemType : 1
         * pollId : 4a304892ce3441bda5240d24ca1b6c9c
         * itemValue : 0-500元;501-2000元;2001-5000元;5001-10000元;10000以上
         * valueList : ["0-500元","501-2000元","2001-5000元","5001-10000元","10000以上"]
         */

        private String itemId;
        private String itemName;
        private String itemNotnull;
        private int itemSortid;
        private String itemType;
        private String pollId;
        private String itemValue;
        private List<String> valueList;
        private List<VoteOption> options;

        public List<VoteOption> getOptions() {
            return options;
        }

        public void setChoice(int index, boolean isCheck) {
            for (int i = 0; i < options.size(); i++) {
                options.get(i).setChecked(i == index);
            }
            options.get(index).setChecked(isCheck);
        }

        public List<VoteOption> getChoice() {
            List<VoteOption> chooseList = new ArrayList<>();
            if (options != null) {
                for (VoteOption op : options) {
                    if (op.isChecked()) {
                        chooseList.add(op);
                    }
                }
            }
            return chooseList;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemNotnull() {
            return itemNotnull;
        }

        public void setItemNotnull(String itemNotnull) {
            this.itemNotnull = itemNotnull;
        }

        public int getItemSortid() {
            return itemSortid;
        }

        public void setItemSortid(int itemSortid) {
            this.itemSortid = itemSortid;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getPollId() {
            return pollId;
        }

        public void setPollId(String pollId) {
            this.pollId = pollId;
        }

        public String getItemValue() {
            return itemValue;
        }

        public void setItemValue(String itemValue) {
            this.itemValue = itemValue;
        }

        public List<String> getValueList() {
            return valueList;
        }

        public void setValueList(List<String> valueList) {
            this.valueList = valueList;
            options=new ArrayList<>();
            if (valueList!=null){
                for (String s:valueList
                     ) {
                    VoteOption option=new VoteOption();
                    option.setValue(s);
                    option.setChecked(false);
                    options.add(option);
                }
            }
        }
    }
}
