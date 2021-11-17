package cn.lyj.thepublic.entry;

import java.io.Serializable;

public class VoteOption implements Serializable {
    private String value;
    private boolean checked;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "VoteOption{" +
                "value='" + value + '\'' +
                ", checked=" + checked +
                '}';
    }
}
