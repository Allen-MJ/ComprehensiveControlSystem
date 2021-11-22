package allen.frame;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.ArrayList;
import java.util.List;

import allen.frame.adapter.PoiItemAdapter;
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AllenMapChoiceActivity extends AllenBaseActivity implements OnGetGeoCoderResultListener, PoiItemAdapter.MyOnItemClickListener {
    @BindView(R2.id.map)
    MapView map;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.rv)
    RecyclerView rv;
    private BaiduMap baiduMap;
    private UiSettings uiSettings;

    // 默认逆地理编码半径范围
    private static final int sDefaultRGCRadius = 500;
    private PoiItemAdapter mPoiItemAdapter;

    private GeoCoder mGeoCoder = null;

    private boolean mStatusChangeByItemClick = false;
    private LatLng mCenter;
    private Handler mHandler;
    private PoiInfo info;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.alen_map_layout;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.alen_choice_save){
            if(info!=null){
                Intent intent = getIntent();
                intent.putExtra(Constants.ObjectFirst,info);
                setResult(RESULT_OK,intent);
                finish();
            }else{
                MsgUtils.showMDMessage(context,"请选择地址!");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) {
            map.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (map != null) {
            map.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (map != null) {
            map.onDestroy();
        }
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }

        if (null != mGeoCoder) {
            mGeoCoder.destroy();
        }
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar, "地址选择", true);
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
        initRecyclerView();
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(final ReverseGeoCodeResult reverseGeoCodeResult) {
        if (null == reverseGeoCodeResult) {
            return;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                updateUI(reverseGeoCodeResult);
            }
        });
    }

    @Override
    public void onItemClick(int position, PoiInfo poiInfo) {
        if (null == poiInfo || null == poiInfo.getLocation()) {
            return;
        }

        mStatusChangeByItemClick = true;
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(poiInfo.getLocation());
        baiduMap.setMapStatus(mapStatusUpdate);
        info = poiInfo;
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener implements BaiduMap.OnMapStatusChangeListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || map == null) {
                Logger.e("debug", "fail!!");
                return;
            }
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            final LatLng latLng = new LatLng(latitude, longitude);
            mCenter = latLng;
            MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.setMapStatus(statusUpdate);
            Logger.e("debug", "Latitude:" + latitude + "||Longitude:" + longitude);
            baiduMap.setOnMapStatusChangeListener(this);
            baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    createCenterMarker();
                    reverseRequest(latLng);
                }
            });
        }

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            LatLng newCenter = mapStatus.target;

            // 如果是点击poi item导致的地图状态更新，则不用做后面的逆地理请求，
            if (mStatusChangeByItemClick) {
                if (!isLatlngEqual(mCenter, newCenter)) {
                    mCenter = newCenter;
                }
                mStatusChangeByItemClick = false;
                return;
            }

            if (!isLatlngEqual(mCenter, newCenter)) {
                mCenter = newCenter;
                reverseRequest(mCenter);
            }
        }
    }

    public static boolean isLatlngEqual(LatLng latLng0, LatLng latLng1) {
        if (latLng0.latitude == latLng1.latitude
                && latLng0.longitude == latLng1.longitude) {
            return true;
        }

        return false;
    }

    /**
     * 创建地图中心点marker
     */
    private void createCenterMarker() {
        Projection projection = baiduMap.getProjection();
        if (null == projection) {
            return;
        }

        Point point = projection.toScreenLocation(mCenter);
        BitmapDescriptor bitmapDescriptor =
                BitmapDescriptorFactory.fromResource(R.drawable.icon_binding_point);
        if (null == bitmapDescriptor) {
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions()
                .position(mCenter)
                .icon(bitmapDescriptor)
                .flat(false)
                .fixedScreenPosition(point);
        baiduMap.addOverlay(markerOptions);
        bitmapDescriptor.recycle();
    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        if (null == rv) {
            return;
        }
        mHandler = new Handler(this.getMainLooper());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(layoutManager);
    }

    /**
     * 逆地理编码请求
     *
     * @param latLng
     */
    private void reverseRequest(LatLng latLng) {
        if (null == latLng) {
            return;
        }

        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption().location(latLng)
                .newVersion(1) // 建议请求新版数据
                .radius(sDefaultRGCRadius);

        if (null == mGeoCoder) {
            mGeoCoder = GeoCoder.newInstance();
        }

        mGeoCoder.setOnGetGeoCodeResultListener(this);
        mGeoCoder.reverseGeoCode(reverseGeoCodeOption);
    }

    /**
     * 更新UI
     *
     * @param reverseGeoCodeResult
     */
    private void updateUI(ReverseGeoCodeResult reverseGeoCodeResult) {
        List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();

        PoiInfo curAddressPoiInfo = new PoiInfo();
        curAddressPoiInfo.address = reverseGeoCodeResult.getAddress();
        curAddressPoiInfo.location = reverseGeoCodeResult.getLocation();
        info = curAddressPoiInfo;
        if (null == poiInfos) {
            poiInfos = new ArrayList<>(2);
        }

        poiInfos.add(0, curAddressPoiInfo);

        if (null == mPoiItemAdapter) {
            mPoiItemAdapter = new PoiItemAdapter(poiInfos);
            rv.setAdapter(mPoiItemAdapter);
            mPoiItemAdapter.setOnItemClickListener(this);
        } else {
            mPoiItemAdapter.updateData(poiInfos);
        }
    }

}
