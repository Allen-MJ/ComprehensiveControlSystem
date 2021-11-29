package allen.frame.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class WordFile implements Serializable {
    private String name;//名称
    private String attachmentPath;//路径
    private int type = 0;//0线上文件，1本地文件

    public WordFile(){}
    public WordFile(String attachmentPath){
        type = 1;
        this.attachmentPath = attachmentPath;
        this.name = StringUtils.getFileNameByPath(attachmentPath);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
