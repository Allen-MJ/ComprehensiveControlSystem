package cn.lyj.core.vediocontrol;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.widget.SearchView;
import butterknife.BindView;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.VideoInode;

public class NoVControlListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.grid_map)
    MapView gridMap;
    @BindView(R2.id.search)
    SearchView search;
    private BaiduMap mBaiduMap;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_vedio_control_show;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (gridMap != null) {
            gridMap.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (gridMap != null) {
            gridMap.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (gridMap != null) {
            gridMap.onDestroy();
        }
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar, "无权限视频监控点位", true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        mBaiduMap = gridMap.getMap();
        loadData("");
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search.setOnSerchListenner(new SearchView.onSerchListenner() {
            @Override
            public void onSerchEvent(String key) {
                loadData(key);
            }
        });
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String id = marker.getExtraInfo().getString("id");
                if(StringUtils.notEmpty(id)){
                    reqestVControl(id);
                }
                return false;
            }
        });
    }

    private void addItemVcontrol(LatLng point,String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.vcontrol);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().extraInfo(bundle)
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    private void reqestVControl(final String id){
        MsgUtils.showMDMessage(context, "是否申请该监控的权限?", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                reqest(id,"","","");
            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void reqest(String id,String authReason,String authStarttime,String authEndtime){
        showProgressDialog("");
        Https.with(this).url(CoreApi.requestNoVcontrol).addParam("inodeId",id).addParam("authReason",authReason)
                .addParam("authEndtime",authEndtime).addParam("authStarttime",authStarttime).get()
                .enqueue(new Callback<List<VideoInode>>() {

                    @Override
                    public void success(List<VideoInode> data) {
                        dismissProgressDialog();

                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    public double clat,clng;
    private void loadData(String name) {
        Https.with(this).url(CoreApi.queryNoVcontrol).get()
                .enqueue(new Callback<List<VideoInode>>() {

                    @Override
                    public void success(List<VideoInode> data) {
                        mBaiduMap.clear();
                        if(data!=null&&data.size()>0){
                            for(VideoInode inode:data){
                                clat = clat + inode.getInodeLat();
                                clng = clng + inode.getInodeLng();
                                LatLng latLng = new LatLng(inode.getInodeLat(),inode.getInodeLng());
                                addItemVcontrol(latLng,inode.getInodeId());
                            }
                            LatLng center = new LatLng(clat,clng);
                            MapStatusUpdate status2 = MapStatusUpdateFactory.newLatLng(center);
                            mBaiduMap.setMapStatus(status2);
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

}
