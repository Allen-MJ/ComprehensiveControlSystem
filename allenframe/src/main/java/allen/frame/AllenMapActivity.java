package allen.frame;

import android.os.Bundle;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import allen.frame.tools.Logger;
import androidx.annotation.Nullable;
import butterknife.BindView;

public class AllenMapActivity extends AllenIMBaseActivity {
    @BindView(R2.id.map)
    MapView map;
    private BaiduMap baiduMap;
    private UiSettings uiSettings;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.alen_map_layout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(map!=null){
            map.onResume();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(map!=null){
            map.onPause();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(map!=null){
            map.onDestroy();
        }
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        baiduMap = map.getMap();
        uiSettings = baiduMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        initLocationOption();
    }

    @Override
    protected void addEvent() {

    }

    /**
     * 初始化定位参数配置
     */
    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        LocationClient locationClient = new LocationClient(getApplicationContext());
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll");//设置坐标类型
        option.setOnceLocation(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置locationClientOption
        locationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        locationClient.start();
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || map == null){
                Logger.e("debug","fail!!");
                return;
            }
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            LatLng latLng = new LatLng(latitude,longitude);
            MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.setMapStatus(statusUpdate);
            Logger.e("debug","Latitude:"+latitude+"||Longitude:"+longitude);
        }
    }
}
