package allen.frame.tools;

import java.util.HashMap;
import java.util.Map;

public class UploadProgressDialog {

    private Map<String,Progress> map;

    public UploadProgressDialog() {
        map = new HashMap<>();
    }

    public void init(){

    }

    public void addEvent(Progress event){
        map.put(event.getKey(),event);
    }

    public static class Progress{
        private String key;
        private long total;
        private long cucurrent;

        public Progress(String key, long total, long cucurrent) {
            this.key = key;
            this.total = total;
            this.cucurrent = cucurrent;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public long getCucurrent() {
            return cucurrent;
        }

        public void setCucurrent(long cucurrent) {
            this.cucurrent = cucurrent;
        }

        public boolean isFinish(){
            return total == cucurrent;
        }

    }
}
