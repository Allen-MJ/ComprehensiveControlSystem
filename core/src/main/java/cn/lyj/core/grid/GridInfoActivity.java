package cn.lyj.core.grid;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Grid;
import allen.frame.tools.Constants;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import cn.lyj.core.R;
import cn.lyj.core.R2;

public class GridInfoActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.grid_date)
    AppCompatTextView gridDate;
    @BindView(R2.id.grid_map)
    MapView gridMap;
    @BindView(R2.id.grid_name)
    AppCompatTextView gridName;
    private Grid entry;
    private BaiduMap mBaiduMap;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_grid_info;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        gridMap.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        gridMap.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        gridMap.onDestroy();
    }

    @Override
    protected void initBar() {
        entry = (Grid) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, "网格信息", true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        mBaiduMap = gridMap.getMap();
        if(entry!=null){
            gridName.setText(entry.getGridName());
            gridDate.setText(entry.getCreateTime());
            drawMap();
        }
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

    private void drawMap(){
        String[] pos = entry.getMapData().split(",");
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(39.93923, 116.357428));
        points.add(new LatLng(39.91923, 116.327428));
        points.add(new LatLng(39.89923, 116.347428));
        points.add(new LatLng(39.89923, 116.367428));
        points.add(new LatLng(39.91923, 116.387428));

        //构造PolygonOptions
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(Color.parseColor(entry.getMapColor())) //填充颜色
                .stroke(new Stroke(5, Color.parseColor(entry.getMapColor()))); //边框宽度和颜色

        //在地图上显示多边形
        mBaiduMap.addOverlay(mPolygonOptions);
    }

}
