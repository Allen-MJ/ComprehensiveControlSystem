package allen.frame.entry;

import java.io.Serializable;

public class UploadFile implements Serializable {

    /**
     * createBy : 18580617183
     * createTime : 2021-11-03 14:10:54
     * id : 66
     * name : /Screenshot_20211103-091949
     * realName : Screenshot_20211103-091949-20211103021054362.png
     * relativePath : /file/图片/Screenshot_20211103-091949-20211103021054362.png
     * size : 75.20KB
     * suffix : png
     * type : 图片
     * updateTime : 2021-11-03 14:10:54
     */

    private String createBy;
    private String createTime;
    private int id;
    private String name;
    private String realName;
    private String relativePath;
    private String size;
    private String suffix;
    private String type;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
