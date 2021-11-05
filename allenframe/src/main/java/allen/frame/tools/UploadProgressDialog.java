package allen.frame.tools;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import allen.frame.R;

public class UploadProgressDialog {

    private Map<String,Progress> map;
    private ProgressDialog dialog;
    private Context context;

    public UploadProgressDialog() {
        map = new HashMap<>();
    }

    public void init(Context context){
        this.context = context;
        show();
    }

    private void show(){
        if(dialog==null){
            dialog = new ProgressDialog(context, R.style.Base_V21_Theme_AppCompat_Light_Dialog);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(false);
            dialog.show();
        }else{
            dialog.show();
        }
    }

    public void changeProgress(String key,long total,long cucurrent){
        map.put(key,new Progress(key,total,cucurrent));
        boolean isOk = true;
        StringBuffer sb = new StringBuffer();
        long mc=0,mt=0;
        for(Map.Entry<String,Progress> entry:map.entrySet()){
            Progress progress = entry.getValue();
            isOk = isOk && progress.isFinish();
            sb.append(progress+"\n");
            mt = mt +progress.getTotal();
            mc = mc +progress.getCucurrent();
        }
        dialog.setProgressNumberFormat(String.format("%s/%s", StringUtils.formatFileSize(mc),StringUtils.formatFileSize(mt)));
        dialog.setMessage(sb);
        if(isOk){
            if(listener!=null){
                listener.onComplet(dialog);
            }
        }
    }

    private OnCompletListener listener;
    public void setOnCompletListener(OnCompletListener listener){
        this.listener = listener;
    }

    public interface OnCompletListener{
        void onComplet(ProgressDialog dialog);
    }

    public static class Progress{
        private String key;
        private long total;
        private long cucurrent;

        public Progress() {
        }

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

        public String getMessege(){
            return getKey()+"  "+cucurrent/total*100+"%";
        }

        public boolean isFinish(){
            if(total!=0&&cucurrent!=0){
                return total == cucurrent;
            }else{
                return false;
            }
        }

    }
}
