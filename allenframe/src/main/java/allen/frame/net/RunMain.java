package allen.frame.net;

import android.app.Activity;
import android.content.Context;

import allen.frame.tools.Logger;

public class RunMain {
    private Context context;
    public RunMain(Context context){
        this.context = context;
    }
    public void run2main(Runnable runnable){
        if(context instanceof Activity){
            if(((Activity) context).isFinishing()){
                Logger.http("data", "Activity is on isFinishing!");
            }else{
                ((Activity) context).runOnUiThread(runnable);
            }
        }else{
            new Thread(runnable).start();
        }
    }
}
