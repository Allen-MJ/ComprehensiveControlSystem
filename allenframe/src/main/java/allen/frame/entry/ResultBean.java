package allen.frame.entry;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {
    private T content;
    private int totalElements;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
