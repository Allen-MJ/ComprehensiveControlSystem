package cn.lyj.work;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.PermissionListener;
import allen.frame.widget.CircleImageView;
import allen.frame.widget.ContrlScrollViewPager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.core.LocationService;

public class HomeActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.user_photo)
    CircleImageView userPhoto;
    @BindView(R2.id.user_name)
    AppCompatTextView userName;
    @BindView(R2.id.user_grade)
    AppCompatTextView userGrade;
    @BindView(R2.id.user_dw)
    AppCompatTextView userDw;
    @BindView(R2.id.xl_bt)
    AppCompatImageView xlBt;
    @BindView(R2.id.pager)
    ContrlScrollViewPager pager;
    @BindView(R2.id.bottom)
    BottomNavigationView bottom;
    private FragmentAdapter adapter;
    private List<Fragment> list;
    private boolean isXl = false;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.work_home_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar, "", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this)
                .load(Constants.url + shared.getString(Constants.UserPhoto, ""))
                .error(R.drawable.default_photo).placeholder(R.drawable.default_photo)
                .into(userPhoto);
        isXl = shared.getBoolean(Constants.UserMap,false);
        xlBt.setImageResource(isXl?R.drawable.lamp_green:R.drawable.lamp_red);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        userName.setText(shared.getString(Constants.UserName, ""));
        userGrade.setText(shared.getString(Constants.UserGrage, ""));
        userDw.setText(shared.getString(Constants.UserUnitsName,""));
        list = new ArrayList<>();
        list.add(HomeFragment.init());
        list.add(ModelFragment.init(1));
        list.add(ModelFragment.init(2));
        adapter = new FragmentAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(adapter);
    }

    @Override
    protected void addEvent() {
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.item_home) {
                    pager.setCurrentItem(0);
                } else if (itemId == R.id.item_manager) {
                    pager.setCurrentItem(1);
                } else if (itemId == R.id.item_work) {
                    pager.setCurrentItem(2);
                }
                return true;
            }
        });
        bottom.setSelectedItemId(R.id.item_home);
    }

    @OnClick({R2.id.user_photo, R2.id.xl_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.xl_bt){
            requestRunPermisssion(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10, new PermissionListener() {
                @Override
                public void onGranted(int requestCode) {
                    Intent locationIntent = new Intent(HomeActivity.this, LocationService.class);
                    isXl = shared.getBoolean(Constants.UserMap,false);
                    shared.edit().putBoolean(Constants.UserMap,!isXl).apply();
                    if(isXl){
                        stopService(locationIntent);
                    }else{
                        //开启前台服务
                        startService(locationIntent);
                    }
                    xlBt.setImageResource(!isXl?R.drawable.lamp_green:R.drawable.lamp_red);
                }

                @Override
                public void onDenied(List<String> deniedPermission) {
                    MsgUtils.showMDMessage(context,"巡逻功能需要开通权限!");
                }
            });
        }
        view.setEnabled(true);
    }

}
