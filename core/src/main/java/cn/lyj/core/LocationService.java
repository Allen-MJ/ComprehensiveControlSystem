package cn.lyj.core;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import allen.frame.ActivityHelper;
import allen.frame.AllenManager;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import cn.lyj.core.api.CoreApi;


public class LocationService extends Service {
    private static final String TAG="LocationService";
    //声明mlocationClient对象
    public LocationClient mlocationClient;
    //声明mLocationOption对象
    public LocationClientOption mLocationOption = null;
    private ActivityHelper actHelper = new ActivityHelper(this);
    private SharedPreferences shared;
    private String uid;
    private Context context = this;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        Logger.e(TAG, "onCreate：");
        if(Build.VERSION.SDK_INT >= 26) {
            startForeground(101, buildNotification());
        }

        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.e(TAG, "onStartCommand：");
        shared = actHelper.getSharedPreferences();
        uid = shared.getString(Constants.UserId, "");
        if(mlocationClient!=null){
            mlocationClient.stop();
            mlocationClient = null;
            mLocationOption = null;
        }
//        setAlarm();
        getPosition();
        return START_STICKY;
    }
    /*public void setAlarm() {
        *//**
         * 设置7点到21点定时提示
         *//*
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this,
                    AlarmReceiver.class);
            intent.setAction("action");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this, 22, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            Calendar calendar = Calendar.getInstance();
            long currentTime = calendar.getTimeInMillis();// 获取当前时间
            calendar.set(calendar.HOUR_OF_DAY, 22);
            calendar.set(calendar.MINUTE, 0);
            calendar.set(calendar.SECOND, 0);
            *//**
             * 当前时间大于设置的时间，将设置的时间增加一天
             *//*
            if (currentTime > calendar.getTimeInMillis()) {
                calendar.add(Calendar.DATE, 1);
            }

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), 1000 * 60 * 60 * 24,
                    pendingIntent);
    }*/
    public void getPosition() {
        //初始化定位
        mlocationClient = new LocationClient(this);
        //初始化定位参数
        mLocationOption = new LocationClientOption();
        //设置定位监听
        mlocationClient.registerLocationListener(mLocationListener);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setScanSpan(shared.getInt(Constants.UserMapRate,6)*1000);
        mLocationOption.setIsNeedAddress(false);
        mLocationOption.setCoorType("bd09ll");
        mLocationOption.setOpenGps(true);
        //设置定位参数
        mlocationClient.setLocOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.start();
    }

    private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
    private NotificationManager notificationManager = null;
    boolean isCreateChannel = false;

    @SuppressLint("NewApi")
    private Notification buildNotification() {

        Notification.Builder builder = null;
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager = (NotificationManager) getSystemService(Context
                        .NOTIFICATION_SERVICE);
            }
            String channelId = getPackageName();
            if (!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(getApplicationContext(), channelId);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        builder.setSmallIcon(R.drawable.logo_grid)
                .setContentTitle(AllenManager.getInstance().getAppname())
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.getNotification();
        }
        return notification;
    }
    private boolean isShow = false;
    // 声明定位回调监听器
    public BDAbstractLocationListener mLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                if(location.getFloor()!=null){
                    mlocationClient.startIndoorMode();
                }else{
                    mlocationClient.stopIndoorMode();
                }
                //定位成功回调信息，设置相关消息
                double lat = location.getLatitude();//获取纬度
                double lon = location.getLongitude();//获取经度
                Https.with(context).url(CoreApi.GridMap).addHeader("gridmemberId",uid)
                        .addParam("pointX",lon).addParam("pointY",lat).post()
                        .enqueue(new Callback<Object>() {

                            @Override
                            public void success(Object data) {
                                Logger.e("定位","add grid map point");
                            }

                            @Override
                            public void fail(final Response response) {
                                if(!isShow){
                                    isShow = true;
                                    Looper.prepare();
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            MsgUtils.showSystemMDMessage(context, "温馨提示", response.getMsg(), "知道了", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    isShow = false;
                                                }
                                            },null,null);
                                        }
                                    });
                                    Looper.loop();
                                }
                                Logger.e("定位","fail add grid map point");
                            }
                        });
            }else{
                if(!isShow){
                    isShow = true;
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            MsgUtils.showSystemMDMessage(context, "温馨提示", "请检查定位功能是否已启动以及网络情况!", "知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    isShow = false;
                                }
                            },null,null);
                        }
                    });
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mlocationClient!=null){
//            mlocationClient.disableBackgroundLocation(false);
            mlocationClient.stop();
            mlocationClient = null;
            mLocationOption = null;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        LocationService getService() {
            return LocationService.this;
        }
    }
}