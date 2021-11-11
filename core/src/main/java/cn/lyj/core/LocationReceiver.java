package cn.lyj.core;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

public class LocationReceiver extends BroadcastReceiver {
    private static final String TAG="LocationReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            Log.e(TAG, "onReceive: 亮屏了!");
            if (!isServiceRunning(context.getPackageName()+".LocationService",context)){
                //开启前台服务
                Log.e(TAG, "onReceive: 开启前台服务!");
                Intent locationIntent = new Intent(context, LocationService.class);
                context.startService(locationIntent);
            }

        }else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
            Log.e(TAG, "onReceive: 熄屏了!");

            if (!isServiceRunning(context.getPackageName()+".LocationService",context)){
                //开启前台服务
                Intent locationIntent = new Intent(context, LocationService.class);
                Log.e(TAG, "onReceive: 开启前台服务!");
                context.startService(locationIntent);
            }
        }

    }
    /**
     * 判断服务是否正在运行
     *
     * @param serviceName 服务类的全路径名称 例如： com.jaychan.demo.service.PushService
     * @param context 上下文对象
     * @return
     */
    public static boolean isServiceRunning(String serviceName, Context context) {
        //活动管理器
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(Integer.MAX_VALUE); //获取运行的服务,参数表示最多返回的数量

        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            String className = runningServiceInfo.service.getClassName();
            if (className.equals(serviceName)) {
                return true; //判断服务是否运行
            }
        }

        return false;
    }
}
